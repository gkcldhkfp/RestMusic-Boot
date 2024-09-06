package com.itwill.rest.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Like;
import com.itwill.rest.domain.LikeId;
import com.itwill.rest.dto.SongSearchResultDto;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class SongRepositoryTest {
	@Autowired
	private SongRepository songRepo;

	@Autowired
	private LikeRepository likeRepo;
	@Autowired
	private GroupRepository grRepo;
	@Autowired
	private ArtistRepository arRepo;
	
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
	
//	@Test
	@Transactional
	public void searchTest() {
//		 List<Object[]> results = songRepo.findSongsByKeywordOrderByRecency("dum", 10, 0);
		 
		List<Object[]> results = songRepo.searchAllSongs("앨범");
		
	     List<SongSearchResultDto> dtos = new ArrayList<>();


		 for (Object[] result : results) {
	            SongSearchResultDto dto = new SongSearchResultDto();
	            dto.setSongId(((Number) result[0]).intValue());
	            dto.setTitle((String) result[1]);
	            dto.setAlbumId(((Number) result[2]).intValue());
	            dto.setAlbumName((String) result[3]);
	            dto.setAlbumImage((String) result[4]);
	            dto.setArtistName((String) result[5]);
	            dto.setGroupName((String) result[6]);
	            dto.setArtistId((String) result[7]);
	            dto.setGroupId((String) result[8]);
	            dto.setLikeCount(((Number) result[9]).intValue());
	            dtos.add(dto);
	        }
	       
		
		 dtos.forEach(System.out :: println);
	}
	
}