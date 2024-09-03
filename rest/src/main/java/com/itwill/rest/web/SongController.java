package com.itwill.rest.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.Song;
import com.itwill.rest.dto.SongDetailsDto;
import com.itwill.rest.domain.User;
import com.itwill.rest.dto.SongChartDto;
import com.itwill.rest.service.AlbumSongsService;
import com.itwill.rest.service.SongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/song")
public class SongController {
	
	private final SongService songSvc;
	private final AlbumSongsService albumServ;
	
	@GetMapping("/details")
	public void details(@RequestParam(name = "songId") int songId, Model model) {
		log.info("details({})", songId);
		
		
		SongDetailsDto dto = songSvc.readDetails(songId);
		
		model.addAttribute("data", dto);
		
		log.info("cover={}",dto.getAlbumImage());
		log.info("cover={}",dto.getWriters());
		
		
		
//		log.info("ly={}", dto.getLyrics());
		
		// Song 객체 가져옴
		Song song = songSvc.selectBySongId(songId);
		// 리스트로 만듬
		List<Song> songs = List.of(song);
		// 앨범 참여 가수: 정렬, 중복처리
		List<Artist> sortedArtists = albumServ.getSortedArtists(songs, 10);
		log.info("sortedArtists = {}", sortedArtists);
		model.addAttribute("albumArtist", sortedArtists);

		// 앨범 참여 그룹 가져오기.
		List<Group> sortedGroups = albumServ.getSortedGroups(songs, 10);
		if (!sortedGroups.isEmpty()) {
			log.info("sortedGroups = {}", sortedGroups);
			model.addAttribute("albumGroup", sortedGroups);

			// 모든 그룹의 groupMembers를 단일 리스트로 모으기
			List<Artist> groupMembers = sortedGroups.stream()
					.flatMap(group -> group.getGroupMembers().stream().map(gm -> gm.getArtist()))
					.collect(Collectors.toList());

			// 그룹 멤버에 포함되지 않은 아티스트만 필터링
			List<Artist> filteredArtists = sortedArtists.stream()
					.filter(artist -> groupMembers.stream().noneMatch(member -> member.equals(artist)))
					.collect(Collectors.toList());
			log.info("filteredArtists = {}", filteredArtists);
			model.addAttribute("filteredArtists", filteredArtists);
		} else {
			model.addAttribute("albumGroup", null);

		}
	}
	
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

        model.addAttribute("topSongs", songSvc.getTopSongs());
        model.addAttribute("loginUserId", loginUserId);
    }
	
	// 장르별 차트
	@GetMapping("/genreChart")
	public void showSongs(Model model, Authentication authentication) {
	    log.info("showSongs");
	    
	    Integer loginUserId = null;
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

	    Integer loginUserId = null;
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
	
	// 최신 음악
	@GetMapping("/newest")
    public void newestSongs(Model model, Authentication authentication) {
		log.info("newestSongs");
        Integer loginUserId = null;
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

        Integer loginUserId = null;
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
