package com.itwill.rest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.itwill.rest.domain.Comment;
import com.itwill.rest.domain.Song;
import com.itwill.rest.dto.CommentDto;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class CommentRepoTest {

	@Autowired
	private CommentRepository comRepo;
	
	@Autowired
	private SongRepository songRepo;
	
//	@Test
	public void test() {
		
//		Song song = songRepo.findById(21).get();
		
		Pageable pageable = PageRequest.of(0, 5, Sort.by("modifiedTime").descending());
		
		Page<CommentDto> list = comRepo.searchAllCommentBySongId(21, pageable);
		
		  log.info("list.number = {}, list.totalPages = {}",
				  list.getNumber(), list.getTotalPages());
		  
	    list.forEach(System.out::println);
	    
	}
	
	
}
