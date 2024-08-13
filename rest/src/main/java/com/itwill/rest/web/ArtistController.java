package com.itwill.rest.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.rest.domain.Artist;
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
	public void songs(@RequestParam(name = "artistId") Integer artistId, Model model) {
		log.info("songs(artistId={})", artistId);
		
		Artist artist = artistSvc.findById(artistId);
		
		model.addAttribute("artist", artist);
	}
	
	@GetMapping("/albums")
	public void albums(@RequestParam(name = "artistId") Integer artistId, Model model) {
		log.info("albums(artistId={})", artistId);
		
		Artist artist = artistSvc.findById(artistId);
		
		model.addAttribute("artist", artist);
	}

}
