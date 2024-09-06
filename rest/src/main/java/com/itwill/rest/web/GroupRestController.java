package com.itwill.rest.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.rest.domain.GroupLikeId;
import com.itwill.rest.service.GroupService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupRestController {
	
	private final GroupService groupSvc;
	
	@PostMapping("/isLiked")
	public ResponseEntity<Boolean> checkSongLiked(@RequestBody GroupLikeId likeId) {
		log.info("dto={}",likeId);
		
		boolean result = groupSvc.isLiked(likeId);
		
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("/like")
	public ResponseEntity<Boolean> songLikes(@RequestBody GroupLikeId likeId) {
		log.debug("dto={}",likeId);
		
		boolean result = groupSvc.like(likeId);
		
		return ResponseEntity.ok(result);
	}

}
