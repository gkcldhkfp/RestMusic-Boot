package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.Like;
import com.itwill.rest.domain.LikeId;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
	
	List<Like> findByLikeId_id(Integer id);
	
}