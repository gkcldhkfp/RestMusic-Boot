package com.itwill.rest.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.rest.domain.User;
import com.itwill.rest.dto.SongLikeDto;
import com.itwill.rest.service.SongService;
import com.itwill.rest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SongLikeRestController {
	
	private final SongService songSvc;
	private final UserService userSvc;
	
	@PostMapping("/isLiked")
    public ResponseEntity<Boolean> isUserLikedSong(@RequestBody SongLikeDto dto) {
        log.info("isUserLikedSong = {}", dto);
        boolean result = songSvc.isUserLikedSong(dto);
        return ResponseEntity.ok(result);
    }
	
	@PreAuthorize("hasRole('USER')")
    @PostMapping("/song/addLike")
    public ResponseEntity<Integer> addSongLike(@RequestBody SongLikeDto dto) {
        log.info("addSongLike({})", dto);
//        Integer userId = getCurrentUserId();
//        dto.setLoginUserId(userId); // Set user ID from the logged-in user
        songSvc.addSongLike(dto);
        int likesCount = songSvc.countSongLikes(dto.getSongId());
        return ResponseEntity.ok(likesCount);
    }
	
	@PreAuthorize("hasRole('USER')")
    @DeleteMapping("/song/cancelLike/{songId}/{loginUserId}")
    public ResponseEntity<Integer> deleteSongLike(@PathVariable int songId, @PathVariable int loginUserId) {
//		Integer loginUserId = getCurrentUserId();
        log.info("deleteSongLike(songId={}, loginUserId={})", songId, loginUserId);
        SongLikeDto dto = new SongLikeDto(songId, loginUserId);
        songSvc.cancelSongLike(dto);
        int likesCount = songSvc.countSongLikes(songId);
        return ResponseEntity.ok(likesCount);
    }
	
//	private Integer getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            User user = (User) userSvc.loadUserByUsername(userDetails.getUsername());
//            if (user != null) {
//                return user.getId();  // `User` 객체에서 ID를 추출
//            }
//        }
//        return null;
//    }

}
