package com.itwill.rest.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.rest.dto.SongDetailsDto;
import com.itwill.rest.service.SongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/song")
public class SongController {

	private final SongService songSvc;
	
	@GetMapping("/details")
	public void details(@RequestParam(name = "songId") int songId, Model model) {
		log.info("details({})", songId);
		
		
		SongDetailsDto dto = songSvc.readDetails(songId);
		
		model.addAttribute("data", dto);
		
		log.info("cover={}",dto.getAlbumImage());
		log.info("cover={}",dto.getWriters());
		
//		log.info("ly={}", dto.getLyrics());
		
	}
	
}
