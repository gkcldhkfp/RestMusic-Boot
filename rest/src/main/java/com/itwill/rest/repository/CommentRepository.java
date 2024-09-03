package com.itwill.rest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.Comment;
import com.itwill.rest.domain.Song;

public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentQuerydsl{

	Page<Comment> findBySong(Song song, Pageable pageable);
	
	
}
