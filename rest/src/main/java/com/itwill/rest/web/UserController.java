package com.itwill.rest.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.rest.domain.User;
import com.itwill.rest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userSvc;
	
	@GetMapping("/mypage")
	public void myPage(@RequestParam(name = "id") Integer id, Model model) {
		log.info("myPage(id={})", id);
		
		User user = userSvc.readById(id);
		
		model.addAttribute("user", user);
	}

}
