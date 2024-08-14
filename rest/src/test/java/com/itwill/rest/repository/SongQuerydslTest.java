package com.itwill.rest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.dto.SongDetailsDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SongQuerydslTest {

	@Autowired
	private SongRepository songRepo;
	
//	@Test
	@Transactional
	public void querydlstest() {
		
		SongDetailsDto dto = songRepo.searchDetailsById(1);
		
		log.info("{}",dto);
		log.info("{}",dto.getComposerIds());
		
	}
	
}
