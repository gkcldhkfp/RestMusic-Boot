package com.itwill.rest.web;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.itwill.rest.domain.User;
import com.itwill.rest.dto.SongChartDto;
import com.itwill.rest.service.AlbumSongsService;
import com.itwill.rest.service.SongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	
	// 의존성 주입.
	private final SongService songSvc;
	private final AlbumSongsService albumSongsSvc;
	
	@GetMapping("/home")
	public String home(Model model, Authentication authentication){
		log.info("home()");
		
		List<SongChartDto> topTenList = songSvc.getTop10Songs();
		model.addAttribute("topTenList", topTenList);
		
		List<SongChartDto> newestList = songSvc.getNewestSongs();
		model.addAttribute("newestList", newestList);
		
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