package com.itwill.rest.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.rest.domain.User;
import com.itwill.rest.dto.user.UserLikeDto;
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
	
	@GetMapping("/getUserLike/{id}")
	@ResponseBody
	public ResponseEntity<List<UserLikeDto>> getUserLike(@PathVariable Integer id) {
		log.info("getUserLike(id={})", id);
		
		List<UserLikeDto> list = userSvc.selectLikesById(id);
		
		return ResponseEntity.ok(list);
	}

}
