package com.itwill.rest.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Like;
import com.itwill.rest.domain.LikeId;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class SongRepositoryTest {
	@Autowired
	private SongRepository songRepo;

	@Autowired
	private LikeRepository likeRepo;
	
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

//	@Test
//	@Transactional
	public void likeTest() {
		LikeId likeId = LikeId.builder().id(1).songId(21).build();
		Optional<Like> like = likeRepo.findById(likeId);
		log.info("like={}",like);
		if(like.isEmpty()) {
			Like like1 = Like.builder().likeId(likeId).build(); 
			likeRepo.save(like1);
		} else {
			likeRepo.delete(like.get());
		}
		
	}
}
