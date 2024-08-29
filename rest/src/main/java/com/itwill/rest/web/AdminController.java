package com.itwill.rest.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping("/manage")
	public void manage() {
		log.info("Get manage()");
	}
	
	@GetMapping("/song")
	public void song() {
		log.info("Get song()");
	}

	@GetMapping("/group")
	public void group() {
		log.info("Get group()");
	}

	@GetMapping("/artist")
	public void artist() {
		log.info("Get artist()");
	}

	@GetMapping("/album")
	public void album() {
		log.info("Get album()");
	}
	
}
