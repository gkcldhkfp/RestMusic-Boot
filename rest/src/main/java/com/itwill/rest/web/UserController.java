package com.itwill.rest.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.rest.domain.User;
import com.itwill.rest.dto.UserLikeDto;
import com.itwill.rest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userSvc;

	@PreAuthorize("hasRole('USER')") // -> 로그인한(USER Role을 가진) 유저만 접속할 수 있게 제한
	@GetMapping("/mypage")
	public void myPage(@RequestParam(name = "id") Integer id, Model model, Authentication authentication) {
		log.info("myPage(id={})", id);

		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);
		User user = userSvc.readById(id);

		model.addAttribute("user", user);
	}

	@GetMapping("/getUserLike/{id}")
	@ResponseBody
	public ResponseEntity<List<UserLikeDto>> getUserLike(@PathVariable(name = "id") Integer id, Model model) {
		log.info("getUserLike={}", id);

		// UserService를 통해 해당 사용자 ID로 좋아요 리스트 조회
		List<UserLikeDto> list = userSvc.getLikeSongByUserId(id);

		log.info("list = {}", list);
		return ResponseEntity.ok(list);
	}

}
