package com.itwill.rest.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

        Integer loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        log.info("loginUserId={}", loginUserId);

        model.addAttribute("topSongs", songSvc.readTopSongs());
        model.addAttribute("loginUserId", loginUserId);
    }
	
	// 장르별 차트
	@GetMapping("/genreChart")
    public String showSongs(Model model, 
                            @RequestParam(name = "genreName", required = false, defaultValue = "전체") String genreName,
                            Authentication authentication) {
        log.debug("showSongs(model = {}, genreName = {})", model, genreName);

        Integer loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        log.debug("loginUserId={}", loginUserId);

        List<SongChartDto> list = "전체".equals(genreName) 
            ? songSvc.readAllSongs()
            : songSvc.readSongsByGenre(genreName);
        
        model.addAttribute("genreSongs", list);
        model.addAttribute("genres", Arrays.asList("전체", "발라드", "팝", "댄스/일렉", "알앤비", "힙합", "트로트", "OST", "인디", "포크/블루스", "록/메탈"));
        model.addAttribute("loginUserId", loginUserId);

        return "/song/genreChart";
    }
	
	// 최신 음악
	@GetMapping("/newest")
    public void getNewestSongs(Model model, Authentication authentication) {
        log.info("getNewestSongs()");

        Integer loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        log.info("loginUserId={}", loginUserId);

        model.addAttribute("newSongs", songSvc.readNewestSongs());
        model.addAttribute("loginUserId", loginUserId);
    }
	
	// 최신 음악(페이징)
//	@GetMapping("/api/newest")
//	@ResponseBody
//    public  ResponseEntity<Map<String, Object>> getNewestSongs(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "30") int size,
//            Authentication authentication) {
//
//        log.info("getNewestSongs(page={}, size={})", page, size);
//
//        Integer loginUserId = null;
//        if (authentication != null && authentication.isAuthenticated()) {
//            User user = (User) authentication.getPrincipal();
//            loginUserId = user.getId();
//        }
//        log.info("loginUserId={}", loginUserId);
//
//        Page<SongChartDto> pageResult = songSvc.readNewestSongs(PageRequest.of(page, size));
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("newSongs", pageResult.getContent());
//        response.put("hasMore", pageResult.hasNext());
//
//        return ResponseEntity.ok(response);
//    }
	
}
