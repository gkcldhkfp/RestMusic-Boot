package com.itwill.rest.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlayListController {
	
	@GetMapping("/playlists/playlist")
	public void playlist() {
		log.info("playlist()");
	}

}
