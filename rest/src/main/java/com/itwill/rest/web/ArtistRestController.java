package com.itwill.rest.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.rest.domain.ArtistLikeId;
import com.itwill.rest.service.ArtistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artist")
public class ArtistRestController {
	
	private final ArtistService artistSvc;
	
	@PostMapping("/isLiked")
	public ResponseEntity<Boolean> checkSongLiked(@RequestBody ArtistLikeId likeId) {
		log.info("dto={}",likeId);
		
		boolean result = artistSvc.isLiked(likeId);
		
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("/like")
	public ResponseEntity<Boolean> songLikes(@RequestBody ArtistLikeId likeId) {
		log.debug("dto={}",likeId);
		
		boolean result = artistSvc.like(likeId);
		
		return ResponseEntity.ok(result);
	}

}
