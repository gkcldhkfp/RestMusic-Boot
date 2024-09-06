package com.itwill.rest.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itwill.rest.dto.SongPlayerDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/playList")
public class SongPlayerListController {
	private final ObjectMapper objectMapper;

	@GetMapping("/cPList")
	public ResponseEntity<List<SongPlayerDto>> cPList(HttpSession session)
			throws JsonMappingException, JsonProcessingException {
		// 세션에서 Json 문자열을 가져와 자바 리스트로 변경 후 데이터를 전달하는 Rest 컨트롤러

		// 세션에서 JSON 문자열을 가져옴
		String cPListJson = (String) session.getAttribute("cPListJson");
		log.info("cPListJson = {}", cPListJson);
		List<SongPlayerDto> cPList = new ArrayList<>();
		if (cPListJson != null && !cPListJson.trim().isEmpty()) {
			try {
				// JSON 문자열을 List<SongPlayerDto>로 변환
				cPList = objectMapper.readValue(cPListJson,
						objectMapper.getTypeFactory().constructCollectionType(List.class, SongPlayerDto.class));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info("cPList = {}", cPList);
		return ResponseEntity.ok(cPList);
	}

}
