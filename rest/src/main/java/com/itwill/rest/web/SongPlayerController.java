package com.itwill.rest.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itwill.rest.domain.Song;
import com.itwill.rest.service.SongService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SongPlayerController {
	private final SongService songServ;

	private final ObjectMapper objectMapper;

	@GetMapping("/player/playerPage")
	public void playerPage(
			Model model,
			HttpSession session) throws JsonProcessingException {
		@SuppressWarnings("unchecked")
		List<Song> cPList = (List<Song>) session.getAttribute("cPList");
		// 세션에서 리스트를 받아옴.
		// jackson objectmapper 객체 생성
		String cPListJson = objectMapper.writeValueAsString(cPList);
		// List -> Json 문자열
		System.out.println(cPListJson);
		// Json 문자열 출력
		model.addAttribute("cPList", cPListJson);
		// jsp에 전달
	}

	@GetMapping("/song/addCurrentPlayList")
	@ResponseBody
	public ResponseEntity<Song> addCurrentPlayList(
			@RequestParam(name = "songId") Integer songId,
			HttpSession session) {

		// 세션에서 리스트를 가져옴
		@SuppressWarnings("unchecked")
		List<Song> cPList = (List<Song>) session.getAttribute("cPList");
		if (cPList == null) { // 리스트가 널이면 새 배열을 넣어줌.
			cPList = new ArrayList<>();
		}
		log.debug("songId = {}", songId);
		Song song = songServ.selectBySongId(songId);
		// 요청 파라미터로 받은 songId로 음악 객체를 생성함.
		log.debug("song", song);
		cPList.add(song);
		// 생성한 음악 객체를 현재 재생 목록에 추가함.

		log.debug("cPList = {}", cPList);
		session.setAttribute("cPList", cPList);
		// 재생목록을 세션에 업데이트.

		return ResponseEntity.ok(song);
	}

	@GetMapping("/song/listen")
	@ResponseBody
	public ResponseEntity<Song> listen(
			@RequestParam(value = "songId") Integer songId,
			HttpSession session) {

		session.setAttribute("cPList", null);
		// 바로듣기 버튼 클릭 시 세션에 저장된 리스트를 지움.
		List<Song> cPList = new ArrayList<>();
		// 리스트를 지웠으므로 새 리스트를 생성해줌.
		Song song = songServ.selectBySongId(songId);
		// 요청 파라미터로 받은 songId로 음악 객체를 생성
		log.debug("song = {}", song);
		cPList.add(song);
		// 새로 생성한 리스트에 음악 객체를 추가.
		session.setAttribute("cPList", cPList);
		// 세션에 리스트를 업데이트

		return ResponseEntity.ok(song);
	}

}
