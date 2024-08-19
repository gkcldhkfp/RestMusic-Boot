package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.Like;
import com.itwill.rest.domain.LikeId;

public interface LikeRepository extends JpaRepository<Like, LikeId>{

}
