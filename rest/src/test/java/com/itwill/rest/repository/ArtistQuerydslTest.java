package com.itwill.rest.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.rest.dto.ArtistAlbumDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ArtistQuerydslTest {
	
	@Autowired
	private ArtistQuerydslImpl artistQuerydsl;
	
	@Test
	public void testSelectAlbumsByArtistId() {
		List<ArtistAlbumDto> artistAlbum = artistQuerydsl.selectAlbumsByArtistId(21);
		
		assertNotNull(artistAlbum);
	}

}
