package com.itwill.rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Comment;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.User;
import com.itwill.rest.dto.CommentCreateDto;
import com.itwill.rest.dto.CommentDto;
import com.itwill.rest.dto.CommentUpdateDto;
import com.itwill.rest.repository.CommentRepository;
import com.itwill.rest.repository.SongRepository;
import com.itwill.rest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commRepo;
	
	private final SongRepository songRepo;
	
	private final UserRepository userRepo;
	
	public Comment create(CommentCreateDto dto) {
		
		Song song = songRepo.findById(dto.getSongId()).get();
		
		User user = userRepo.findById(dto.getWriter()).get();
		
		Comment entity = Comment.builder().song(song).user(user).cText(dto.getCText()).build();
		
		log.info("save 전={}",entity);
		
		commRepo.save(entity);
		
		return entity;
	}
	
	@Transactional(readOnly = true)
	public Page<CommentDto> readBySongId(int songId, int pageNo) {
		 	log.info("readCommentList(songId={}, pageNo={})", songId, pageNo);
	        
	        // 댓글들이 달려 있는 song 엔터티를 검색:
//	        Song song = songRepo.findById(songId).orElseThrow();
	        
	        // 페이징 처리와 정렬을 하기 위한 Pageable 객체 생성:
	        Pageable pageable = PageRequest.of(pageNo, 5, Sort.by("modifiedTime").descending());
	        
	        // DB에서 검색(select 쿼리를 실행)
	        Page<CommentDto> data = commRepo.searchAllCommentBySongId(songId, pageable);
	        log.info("data.number = {}, data.totalPages = {}",
	                data.getNumber(), data.getTotalPages());
	        
	        return data;
	}
	
	public void delete(int id) {
		log.info("delete(id={})", id);
		
		commRepo.deleteById(id);
	}
	
	
	@Transactional
	public void update(CommentUpdateDto dto) {
		 log.info("update(dto={})", dto);
	        
	     // 아이디(PK)로 엔터티를 검색:
	     Comment entity = commRepo.findById(dto.getId()).orElseThrow();
	        
	     // 검색된 엔터티의 필드를 업데이트:
	     entity.update(dto.getCText());
	        
	     // commentRepo.save(entity)를 명시적으로 호출할 필요 없음.
	}
	
	
}
