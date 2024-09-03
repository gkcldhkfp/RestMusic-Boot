package com.itwill.rest.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.rest.domain.User;
import com.itwill.rest.dto.SongLikeDto;
import com.itwill.rest.service.SongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SongLikeRestController {
	
	private final SongService songSvc;
	
	// 사용자가 특정 노래를 좋아요 했는지 여부를 확인하는 메서드
    @PostMapping("/isLiked")
    public ResponseEntity<Boolean> isUserLikedSong(@RequestBody SongLikeDto dto, Authentication authentication) {
        log.info("isUserLikedSong = {}", dto);
        
        // Authentication 객체에서 현재 로그인된 사용자의 ID를 가져옵니다.
        Integer loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
            log.info("loginUserId={}", loginUserId);
        }
        
        dto.setLoginUserId(loginUserId);  // 로그인된 사용자 ID를 DTO에 설정합니다.
        boolean result = songSvc.isUserLikedSong(dto);  // 사용자가 노래를 좋아요 했는지 여부를 확인합니다.
        return ResponseEntity.ok(result);
    }
	
    // 사용자가 특정 노래에 좋아요를 추가하는 메서드
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/song/addLike")
    public ResponseEntity<Integer> addSongLike(@RequestBody SongLikeDto dto, Authentication authentication) {
        log.info("addSongLike({})", dto);
        
        // Authentication 객체에서 현재 로그인된 사용자의 ID를 가져옵니다.
        Integer loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
            log.info("loginUserId={}", loginUserId);
        }
        
        dto.setLoginUserId(loginUserId);  // 로그인된 사용자 ID를 DTO에 설정합니다.
        songSvc.addSongLike(dto);  // 좋아요를 추가합니다.
        int likesCount = songSvc.countSongLikes(dto.getSongId());  // 해당 노래의 좋아요 수를 계산합니다.
        return ResponseEntity.ok(likesCount);
    }
	
    // 사용자가 특정 노래에 대한 좋아요를 취소하는 메서드
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/song/cancelLike/{songId}/{loginUserId}")
    public ResponseEntity<Integer> deleteSongLike(@PathVariable int songId, Authentication authentication) {
        log.info("deleteSongLike(songId={}, loginUserId={})", songId);

        // Authentication 객체에서 현재 로그인된 사용자의 ID를 가져옵니다.
        Integer loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
            log.info("loginUserId={}", loginUserId);
        }
        
        SongLikeDto dto = new SongLikeDto(songId, loginUserId);  // SongLikeDto 객체에 사용자 ID와 노래 ID를 설정합니다.
        songSvc.cancelSongLike(dto);  // 좋아요를 취소합니다.
        int likesCount = songSvc.countSongLikes(songId);  // 좋아요 취소 후의 좋아요 수를 계산합니다.
        return ResponseEntity.ok(likesCount);
    }
	
}
