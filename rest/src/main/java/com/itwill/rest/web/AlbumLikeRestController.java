package com.itwill.rest.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.rest.domain.User;
import com.itwill.rest.dto.AlbumLikeDto;
import com.itwill.rest.service.AlbumSongsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class AlbumLikeRestController {

	private final AlbumSongsService albumServ;

	// 사용자가 특정 노래를 좋아요 했는지 여부를 확인하는 메서드
	@PostMapping("/isAlbumLiked")
	public ResponseEntity<Boolean> isUserAlbumLikedSong(@RequestBody AlbumLikeDto dto, Authentication authentication) {
		log.info("isUserLikedSong = {}", dto);

		// Authentication 객체에서 현재 로그인된 사용자의 ID를 가져옵니다.
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
			log.info("loginUserId={}", loginUserId);
		}

		dto.setLoginUserId(loginUserId); // 로그인된 사용자 ID를 DTO에 설정합니다.
		boolean result = albumServ.isUserLikedAlbum(dto); // 사용자가 노래를 좋아요 했는지 여부를 확인합니다.
		return ResponseEntity.ok(result);
	}

	// 사용자가 특정 노래에 좋아요를 추가하는 메서드
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/album/addLike")
	public ResponseEntity<Integer> addAlbumLike(@RequestBody AlbumLikeDto dto, Authentication authentication) {
		log.info("addSongLike({})", dto);

		// Authentication 객체에서 현재 로그인된 사용자의 ID를 가져옵니다.
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
			log.info("loginUserId={}", loginUserId);
		}

		dto.setLoginUserId(loginUserId); // 로그인된 사용자 ID를 DTO에 설정합니다.
		albumServ.addAlbumLike(dto); // 좋아요를 추가합니다.
		int likesCount = albumServ.countAlbumLikes(dto.getAlbumId()); // 해당 노래의 좋아요 수를 계산합니다.
		return ResponseEntity.ok(likesCount);
	}

	// 사용자가 특정 노래에 대한 좋아요를 취소하는 메서드
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/album/cancelLike")
	public ResponseEntity<Integer> deleteAlbumLike(@RequestBody AlbumLikeDto dto, Authentication authentication) {
		log.info("deleteAlbumLike(albumId={}, loginUserId={})", dto.getAlbumId());

		// Authentication 객체에서 현재 로그인된 사용자의 ID를 가져옵니다.
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
			log.info("loginUserId={}", loginUserId);
			dto.setLoginUserId(loginUserId);
		}

		albumServ.cancelAlbumLike(dto); // 좋아요를 취소합니다.
		Integer likesCount = albumServ.countAlbumLikes(dto.getAlbumId()); // 좋아요 취소 후의 좋아요 수를 계산합니다.
		return ResponseEntity.ok(likesCount);
	}

	// 좋아요 개수 가져오는 컨트롤러
	@PostMapping("/album/getLikeCount")
	public ResponseEntity<Integer> getLikeCount(@RequestBody AlbumLikeDto dto) {
		log.info("getLikeCount(albumId={}, loginUserId={})", dto.getAlbumId());

		int likesCount = albumServ.countAlbumLikes(dto.getAlbumId()); // 좋아요 취소 후의 좋아요 수를 계산합니다.
		return ResponseEntity.ok(likesCount);
	}

}
