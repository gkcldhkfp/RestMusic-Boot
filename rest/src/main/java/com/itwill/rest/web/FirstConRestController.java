package com.itwill.rest.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.rest.dto.ChoSeongSearchDto;
import com.itwill.rest.service.FirstConService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class FirstConRestController {
	
	private final FirstConService firstConServ;
	
	// 초성 검색을 위한 API 엔드포인트
    @GetMapping("/suggestions")
    public List<ChoSeongSearchDto> getSuggestions(@RequestParam String keyword) {
    	log.info("getSuggestions(keyword={})", keyword);
    	
        return firstConServ.searchDtos(keyword);
    }
	
}
