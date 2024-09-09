package com.itwill.rest.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.GenreCode;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.TitleSong;
import com.itwill.rest.domain.User;
import com.itwill.rest.service.AlbumSongsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumSongsController {

	private final AlbumSongsService albumServ;
	
	
	@GetMapping("detail")
	public void detail(@RequestParam(name = "albumId") Integer albumId, Model model, Authentication authentication) {
		log.info("detail(albumId = {})", albumId);

		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);

		Album album = albumServ.readById(albumId);
		log.info("album = {}", album);
		model.addAttribute("album", album);

		List<Song> songs = album.getSongs();
		log.info("songs = {}", songs);
		// 수록곡 개수를 뷰에 전달
		model.addAttribute("songsCount", songs.size());

		// 앨범의 수록곡과 그룹&가수를 매핑
		Map<Song, List<Map<String, Object>>> songAndArtists = albumServ.getArtistsOrGroupsBySongsAndRoleId(songs, 10);
		log.info("albumSongs = {}", songAndArtists);
		model.addAttribute("albumSongs", songAndArtists);

		// 앨범 참여 가수: 정렬, 중복처리
		List<Artist> sortedArtists = albumServ.getSortedArtists(songs, 10);
		log.info("sortedArtists = {}", sortedArtists);
		model.addAttribute("albumArtist", sortedArtists);

		// 앨범 참여 그룹 가져오기.
		List<Group> sortedGroups = albumServ.getSortedGroups(songs, 10);
		if (!sortedGroups.isEmpty()) {
			log.info("sortedGroups = {}", sortedGroups);
			model.addAttribute("albumGroup", sortedGroups);

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
		} else {
			model.addAttribute("albumGroup", null);

		}

		// 앨범 수록곡의 모든 장르를 표시, 중복처리, 중복횟수 많으면 먼저 출력력
		List<GenreCode> genreCodes = albumServ.getSortedGenres(songs);
		log.info("genres = {}", genreCodes);
		model.addAttribute("genres", genreCodes);

		// 앨범의 타이틀곡을 나열
		List<TitleSong> titleSongs = album.getTitleSongs();
		log.info("titleSongs = {}", titleSongs);
		model.addAttribute("titleSongs", titleSongs);

	}

}
