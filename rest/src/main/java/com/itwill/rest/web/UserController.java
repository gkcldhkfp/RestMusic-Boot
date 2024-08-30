package com.itwill.rest.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.User;
import com.itwill.rest.dto.UserLikeDto;
import com.itwill.rest.service.AlbumSongsService;
import com.itwill.rest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userSvc;

	private final AlbumSongsService albumServ;

	@PreAuthorize("hasRole('USER')") // -> 로그인한(USER Role을 가진) 유저만 접속할 수 있게 제한 
	@GetMapping("/mypage")
	public void myPage(@RequestParam(name = "id") Integer id, Model model) {
		log.info("myPage(id={})", id);

		User user = userSvc.readById(id);

		model.addAttribute("user", user);
	}

	@GetMapping("/getUserLike/{id}")
	@ResponseBody
	public ResponseEntity<List<UserLikeDto>> getUserLike(@PathVariable Integer id, Model model) {
		log.info("getUserLike={}", id);
		List<Song> songs = userSvc.getLikeSongByUserId(id);
		// UserLikeDto 리스트 생성
		List<UserLikeDto> list = new ArrayList<>();
		for (Song song : songs) {
			List<Song> innerSong = new ArrayList<Song>();
			innerSong.add(song);
			List<Artist> artists = null;
			List<Group> groups = null;
			// 앨범의 수록곡과 그룹&가수를 매핑
			Map<Song, List<Object>> songAndArtists = albumServ.getArtistsOrGroupsBySongsAndRoleId(innerSong, 10);
			log.info("albumSongs = {}", songAndArtists);
			model.addAttribute("albumSongs", songAndArtists);

			// 앨범 참여 가수: 정렬, 중복처리
			List<Artist> sortedArtists = albumServ.getSortedArtists(innerSong, 10);
			log.info("songs = {}", songs);
			log.info("sortedArtists = {}", sortedArtists);
			model.addAttribute("albumArtist", sortedArtists);

			// 앨범 참여 그룹 가져오기.
			List<Group> sortedGroups = albumServ.getSortedGroups(innerSong, 10);
			if (!sortedGroups.isEmpty()) {
				log.info("sortedGroups = {}", sortedGroups);
				model.addAttribute("albumGroup", sortedGroups);
				groups = sortedGroups;
				// 모든 그룹의 groupMembers를 단일 리스트로 모으기
				List<Artist> groupMembers = sortedGroups.stream()
						.flatMap(group -> group.getGroupMembers().stream().map(gm -> gm.getArtist()))
						.collect(Collectors.toList());

				// 그룹 멤버에 포함되지 않은 아티스트만 필터링
				List<Artist> filteredArtists = sortedArtists.stream()
						.filter(artist -> groupMembers.stream().noneMatch(member -> member.equals(artist)))
						.collect(Collectors.toList());
				log.info("filteredArtists = {}", filteredArtists);
				model.addAttribute("filteredArtists", filteredArtists);
				artists = filteredArtists;
			} else {
				model.addAttribute("albumGroup", null);
			}
			List<Integer> artistIds = artists.stream().map(a -> a.getId()).toList();
			List<String> artistNames = artists.stream().map(a -> a.getArtistName()).toList();
			List<Integer> groupIds = groups!= null ? groups.stream().map(g -> g.getId()).toList() : null;
			List<String> groupNames = groups != null ? groups.stream().map(g -> g.getGroupName()).toList() : null;

			UserLikeDto dto = UserLikeDto.builder().songId(song.getSongId()).title(song.getTitle())
					.albumId(song.getAlbum().getAlbumId()).albumName(song.getAlbum().getAlbumName())
					.albumImage(song.getAlbum().getAlbumImage()).artistId(artistIds).artistName(artistNames)
					.groupId(groupIds).groupName(groupNames).build();
			list.add(dto);

		}
		log.info("list = {}", list);
		return ResponseEntity.ok(list);
	}

}
