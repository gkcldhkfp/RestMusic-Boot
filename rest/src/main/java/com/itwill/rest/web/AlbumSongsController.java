package com.itwill.rest.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.Song;
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
	public void detail(@RequestParam(name = "albumId") Integer albumId, Model model) {
		log.info("detail(albumId = {})", albumId);

		Album album = albumServ.readById(albumId);
		log.info("album = {}", album);
		model.addAttribute("album", album);

		List<Song> songs = album.getSongs();
		log.info("songs = {}", songs);
		// 수록곡 개수를 뷰에 전달
		model.addAttribute("songsCount", songs.size());

		// 앨범의 수록곡과 가수를 매핑
		Map<Song, List<Artist>> songAndArtists = albumServ.getArtistsBySongsAndRoleId(songs, 10);
		log.info("albumSongs = {}", songAndArtists);
		model.addAttribute("albumSongs", songAndArtists);
		

		// 앨범 참여 가수: 정렬, 중복처리
		List<Artist> sortedArtists = albumServ.getSortedArtists(songs, 10);
		model.addAttribute("albumArtist", sortedArtists);


	}

}
