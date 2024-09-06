package com.itwill.rest.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.rest.domain.LikeId;
import com.itwill.rest.dto.SongLikeDto;
import com.itwill.rest.service.SongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/song")
public class SongRestController {

	private final SongService songSvc;
	
	@PostMapping("/isLiked")
	public ResponseEntity<Boolean> postMethodName(@RequestBody LikeId likeId) {
		log.info("dto={}",likeId);
	
		boolean result = songSvc.isLiked(likeId);
		
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("/like")
	public ResponseEntity<Boolean> songLikes(@RequestBody LikeId likeId) {
		log.debug("dto={}",likeId);
		
		boolean result = songSvc.like(likeId);
		
		return ResponseEntity.ok(result);
	}
	
		// 좋아요 개수 가져오는 컨트롤러
	@PostMapping("/getLikeCount")
	public ResponseEntity<Integer> getLikeCount(@RequestBody SongLikeDto dto) {
		log.info("getLikeCount(songId={})", dto.getSongId());

		int likesCount = songSvc.countSongLikes(dto.getSongId()); // 좋아요 취소 후의 좋아요 수를 계산합니다.
		return ResponseEntity.ok(likesCount);
	}
}
