package com.itwill.rest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itwill.rest.dto.CommentDto;

public interface CommentQuerydsl {
	
	// 댓글 가져오기
	Page<CommentDto> searchAllCommentBySongId(int songId, Pageable pageable); 

}
