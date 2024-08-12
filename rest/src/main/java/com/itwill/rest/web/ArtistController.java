package com.itwill.rest.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/artist")
public class ArtistController {
	
	@GetMapping("/songs")
	public void songs() {
		log.info("songs()");
	}
	
	@GetMapping("/albums")
	public void albums() {
		log.info("albums()");
	}

}
