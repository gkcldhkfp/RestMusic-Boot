package com.itwill.rest.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.rest.domain.User;
import com.itwill.rest.dto.SongChartDto;
import com.itwill.rest.service.SongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/song")
public class SongController {
	private final SongService songSvc;
	
	// top30
	@GetMapping("/popularChart")
    public void getPopularSongs(Model model, Authentication authentication) {
        log.info("getPopularSongs()");

        Long loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        log.info("loginUserId={}", loginUserId);

        model.addAttribute("topSongs", songSvc.getTopSongs());
        model.addAttribute("loginUserId", loginUserId);
    }
	
	// 장르별 차트
	@GetMapping("/genreChart")
	public void showSongs(Model model, Authentication authentication) {
	    log.info("showSongs");
	    
	    Long loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        model.addAttribute("loginUserId", loginUserId);
        
	}
	
	// 장르별 차트(페이징 처리)
	@GetMapping("/api/genreChart")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getGenreSongsByPage(
	        @RequestParam(name = "genreName", required = false, defaultValue = "전체") String genreName,
	        @RequestParam(name = "page", required = false, defaultValue = "0") int page,
	        @RequestParam(name = "size", required = false, defaultValue = "30") int size,
	        Authentication authentication) {
	    log.info("getGenreSongsByPage(genreName = {}, page = {}, size = {})", genreName, page, size);

	    Long loginUserId = null;
	    if (authentication != null && authentication.isAuthenticated()) {
	        User user = (User) authentication.getPrincipal();
	        loginUserId = user.getId();
	    }
	    log.info("loginUserId={}", loginUserId);

	    // 노래 리스트 가져오기: 전체 또는 장르별
	    Page<SongChartDto> pageResult = "전체".equals(genreName)
	            ? songSvc.getAllSongs(page, size)
	            : songSvc.getSongsByGenre(genreName, page, size);

	    // 장르 목록 생성
	    List<String> genres = Arrays.asList("전체", "발라드", "팝", "댄스/일렉", "알앤비", "힙합", "트로트", "OST", "인디", "포크/블루스", "록/메탈");

	    // 응답 데이터 구성
	    Map<String, Object> response = new HashMap<>();
	    response.put("songs", pageResult.getContent());
	    response.put("currentPage", page);
	    response.put("totalPages", pageResult.getTotalPages());
	    response.put("totalSongsCount", pageResult.getTotalElements());
	    response.put("selectedGenre", genreName);
	    response.put("loginUserId", loginUserId);
	    response.put("genres", genres);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/newest")
    public void newestSongs(Model model, Authentication authentication) {
		log.info("newestSongs");
        Long loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        model.addAttribute("loginUserId", loginUserId);
    }
    
    // 최신 음악(페이징 처리)
    @GetMapping("/api/newest")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getNewestSongsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            Authentication authentication) {
        log.info("getNewestSongsByPage(page={}, size={})", page, size);

        Long loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        log.info("loginUserId={}", loginUserId);

        Page<SongChartDto> pageResult = songSvc.getNewestSongs(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("songs", pageResult.getContent());
        response.put("loginUserId", loginUserId);
        response.put("totalPages", pageResult.getTotalPages());
        response.put("totalElements", pageResult.getTotalElements());

        return ResponseEntity.ok(response);
    }
	
}
