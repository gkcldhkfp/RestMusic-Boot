package com.itwill.rest.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Album;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class AlbumRepositoryTest {
	
	@Autowired
	private AlbumRepository albumRepo;
	
	// @Test
	public void diTest() {
		log.info("diTest(albumRepo = {})", albumRepo);
		assertThat(albumRepo).isNotNull();
	}

	// @Test
	@Transactional
	public void findAllTest() {
		log.info("findAllTest()");

		List<Album> albums = albumRepo.findAll();

//		albums.forEach((a) -> {System.out.println(a);});
		albums.forEach((a) -> {System.out.println(a.getTitleSongs());});
	}

//	@Test
	@Transactional
	public void findByIdTest() {
		log.info("findByIdTest()");

		Album album = albumRepo.findById(3).get(); // 천천히 가 앨범		
		
		System.out.println(album);
		System.out.println(album.getTitleSongs());
	}
	
}
