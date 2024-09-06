package com.itwill.rest.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.itwill.rest.domain.User;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@GetMapping("/home")
	public String home(Model model, Authentication authentication) {
		log.info("home()");
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);
		return "home/home";
	}

	@GetMapping("/")
	public String index(Model model, Authentication authentication) {
		log.info("index()");

		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);
		return "index";
	}

}