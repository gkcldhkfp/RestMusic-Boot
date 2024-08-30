package com.itwill.rest.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.itwill.rest.domain.Comment;
import com.itwill.rest.domain.QComment;
import com.itwill.rest.dto.CommentDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommentQuerydslImpl extends QuerydslRepositorySupport implements CommentQuerydsl {
	
//	@Autowired
//    private JPAQueryFactory queryFactory;
	
	public CommentQuerydslImpl() {
		super(Comment.class);
	}

	@Override
	public Page<CommentDto> searchAllCommentBySongId(int songId, Pageable pageable) {
		QComment comment = QComment.comment;
		
		 JPQLQuery<CommentDto> query = from(comment)
		            .select(Projections.constructor(CommentDto.class,
		            		comment.user.id,
		                    comment.cId,
		                    comment.user.nickname,
		                    comment.cText,
		                    comment.modifiedTime,
		                    comment.createdTime
		            ))
		            .where(comment.song.songId.eq(songId));

		    // 페이지 설정
		    getQuerydsl().applyPagination(pageable, query);

		    // 리스트와 전체 개수 조회
		    List<CommentDto> list = query.fetch();
		    log.info("list.size = {}", list.size());

		    // 전체 레코드 개수 fetch
		    long count = query.fetchCount();
		    log.info("fetch count = {}", count);

		    // Page<T> 객체를 생성
		    Page<CommentDto> page = new PageImpl<>(list, pageable, count);

		    return page;
	}
	
	

}
