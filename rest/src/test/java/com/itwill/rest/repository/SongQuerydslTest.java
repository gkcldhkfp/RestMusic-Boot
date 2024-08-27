package com.itwill.rest.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Song;
import com.itwill.rest.dto.SearchResultDto;
import com.itwill.rest.dto.SongDetailsDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SongQuerydslTest {

	@Autowired
	private SongRepository songRepo;
	
	@Autowired
	private AlbumRepository albumRepo;
	
	@Test
//	@Transactional
	public void querydlstest() {
		
//		SongDetailsDto dto = songRepo.searchDetailsById(1);
		Pageable pageable = PageRequest.of(0, 5);
		SearchResultDto dto = songRepo.searchAll("+우지*", "accuracy", pageable);
		log.info("{}", dto);
		log.info("{}", dto.getAlbum());
		log.info("{}", dto.getArtist());
		log.info("{}", dto.getSong());
		
		
		
//		List<Song> dummy = new ArrayList<Song>();
//		
//		for(int i = 0; i < 1000; i++) {
//			Album dum = Album.builder().albumName("dummy"+i).albumType("dummy").build();
//			albumRepo.save(dum);
//			
//			for(int x = 0; x < 10; x++) {
//				dummy.add(Song.builder().album(dum).title(i+"앨범 song"+x).songPath(".").lyrics("...").build());
//			}
//		}
//		songRepo.saveAll(dummy);
	}
	
	
	
	
	
}
