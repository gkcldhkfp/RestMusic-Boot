package com.itwill.rest.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.ArtistRole;
import com.itwill.rest.domain.Song;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ArtistRoleRepositoryTest {
	@Autowired
	private ArtistRoleRepository artistRoleRepo;

	@Autowired
	private SongRepository songRepo;

	@Autowired
	private AlbumRepository albumRepo;

	// @Test
	@Transactional
	public void findBySongAndRoleCodeTest() {
		Song song = songRepo.findById(101).orElseThrow();
		log.info("song = {}", song);
		List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, 10);
		artistRoles.forEach(a -> {
			System.out.println(a);
		});
		List<Artist> artists = new ArrayList<>();
		artistRoles.forEach(ar -> {
			artists.add(ar.getArtist());
		});
		artists.forEach(a -> {
			System.out.println(a);
		});
	}


}
