package com.itwill.rest.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class HomeController {

	@GetMapping("/home")
	public String home(){
		log.info("home()");
		return "home/home";
	}

	@GetMapping("/")
	public String index() {
		log.info("index()");
		return "index";
	}
	
}