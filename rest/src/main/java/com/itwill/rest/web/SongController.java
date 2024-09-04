package com.itwill.rest.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.Song;
import com.itwill.rest.dto.SongDetailsDto;
import com.itwill.rest.service.AlbumSongsService;
import com.itwill.rest.service.SongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/song")
public class SongController {

	private final SongService songSvc;
	private final AlbumSongsService albumServ;
	
	@GetMapping("/detail")
	public void details(@RequestParam(name = "songId") int songId, Model model) {
		log.info("details({})", songId);
		
		
		SongDetailsDto dto = songSvc.readDetails(songId);
		
		model.addAttribute("data", dto);
		
		log.info("cover={}",dto.getAlbumImage());
		log.info("cover={}",dto.getWriters());
		
		
		
//		log.info("ly={}", dto.getLyrics());
		
		// Song 객체 가져옴
		Song song = songSvc.selectBySongId(songId);
		// 리스트로 만듬
		List<Song> songs = List.of(song);
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
	}
	
}
