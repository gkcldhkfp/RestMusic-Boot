package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.Like;
import com.itwill.rest.domain.LikeId;

public interface SongLikeRepository extends JpaRepository<Like, LikeId> {
	// 특정 곡의 좋아요 개수 확인
	int countByLikeIdSongId(Integer songId);
	
	// 특정 사용자가 특정 노래를 좋아요 했는지 여부 확인
    boolean existsByLikeId(LikeId likeId);
}
