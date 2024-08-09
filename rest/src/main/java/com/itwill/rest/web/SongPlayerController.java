package com.itwill.rest.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
// @RequiredArgsConstructor
public class SongPlayerController {
	// private final AlbumSongsService albumSongsService;

	

	@GetMapping("/player/playerPage")
	public void playerPage(
			Model model,
			HttpSession session) throws JsonProcessingException {
		// List<AlbumSongs> cPList = (List<AlbumSongs>) session.getAttribute("cPList");
		// 세션에서 리스트를 받아옴.
		// ObjectMapper objectMapper = new ObjectMapper();
		// jackson objectmapper 객체 생성
		// String cPListJson = objectMapper.writeValueAsString(cPList);
		// List -> Json 문자열
		// System.out.println(cPListJson);
		// Json 문자열 출력
		// model.addAttribute("cPList", cPListJson);
		// jsp에 전달
	}

	

}
