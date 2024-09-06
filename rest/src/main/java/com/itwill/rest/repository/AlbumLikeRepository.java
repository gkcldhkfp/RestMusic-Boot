package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.AlbumLike;
import com.itwill.rest.domain.AlbumLikeId;

public interface AlbumLikeRepository extends JpaRepository<AlbumLike, AlbumLikeId> {
		// 특정 곡의 좋아요 개수 확인
	int countByAlbumLikeIdAlbumId(Integer albumId);
	
	// 특정 사용자가 특정 노래를 좋아요 했는지 여부 확인
    boolean existsByAlbumLikeId(AlbumLikeId albumLikeId);
}
