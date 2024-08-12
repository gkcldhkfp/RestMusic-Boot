package com.itwill.rest.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.SongGenre;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class SongRepositoryTest {
	@Autowired
	private SongRepository songRepo;

	@Test
	public void diTest() {
		assertThat(songRepo).isNotNull();
	}

	@Test
	@Transactional
	public void findByIdTest() {
		Song song = songRepo.findById(15).orElseThrow();
		log.info("song = {}", song);
		List<SongGenre> genres = song.getGenres();
		genres.forEach((g) -> {
			System.out.println(g.getGenre().getGenreName());
		});
	}
}
