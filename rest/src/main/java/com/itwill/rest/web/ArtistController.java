package com.itwill.rest.web;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.User;
import com.itwill.rest.dto.ArtistAlbumDto;
import com.itwill.rest.dto.ArtistSongDto;
import com.itwill.rest.service.ArtistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/artist")
public class ArtistController {
	
	private final ArtistService artistSvc;
	
	@GetMapping("/songs")
	public void songs(@RequestParam(name = "artistId") Integer artistId, Model model, Authentication authentication) {
		log.info("songs(artistId={})", artistId);
		
		Artist artist = artistSvc.findById(artistId);
		
		List<ArtistSongDto> list = artistSvc.readSongs(artistId);
		
        Integer loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        log.info("loginUserId={}", loginUserId);
		
		model.addAttribute("artist", artist);
		model.addAttribute("songs", list);
		model.addAttribute("loginUserId", loginUserId);
	}
	
	@GetMapping("/albums")
	public void albums(@RequestParam(name = "artistId") Integer artistId, Model model, Authentication authentication) {
		log.info("albums(artistId={})", artistId);
		
		Artist artist = artistSvc.findById(artistId);
		
		List<ArtistAlbumDto> list = artistSvc.readAlbums(artistId);
		
        Integer loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        log.info("loginUserId={}", loginUserId);
		
		model.addAttribute("artist", artist);
		model.addAttribute("albums", list);
		model.addAttribute("loginUserId", loginUserId);
	}

}
