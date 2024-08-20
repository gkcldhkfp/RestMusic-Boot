package com.itwill.rest.repository;

import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class SongRepositoryTest {
	@Autowired
	private SongRepository songRepo;

	// @Test
	@Transactional
	public void diTest() {
		assertThat(songRepo).isNotNull();
	}

	// @Test
	@Transactional
	public void findByIdTest() {
		/* Song song = songRepo.findById(15).orElseThrow();
		log.info("song = {}", song);
		Set<SongGenre> genres = song.getGenres();
		genres.forEach((g) -> {
			System.out.println(g.getGenreCode().getGenreName());
		}); */
	}

	// @Test
	@Transactional
	public void saveTest() {
		
	}
}
