package com.itwill.rest.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Song;
import com.itwill.rest.repository.AlbumRepository;
import com.itwill.rest.repository.SongRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class AlbumSongsServiceTest {
	@Autowired
	private AlbumRepository albumRepo;

	@Autowired
	private SongRepository songRepo;

	@Autowired
	private AlbumSongsService albumServ;

	// @Test
	@Transactional
	public void readTest() {
		Album album = albumRepo.findById(10).orElseThrow();
		List<Song> songs = album.getSongs();
		Map<Song, List<Map<String, Object>>> artistsBySongsAndRoleId = albumServ.getArtistsOrGroupsBySongsAndRoleId(songs, 10);
		System.out.println(artistsBySongsAndRoleId);

		
		
		// Set<Artist> artistAlbum = albumServ.selectSingersByAlbum(album);
		// artistAlbum.forEach(a -> {
		// 	System.out.println(a);
		// });
		
		// Song song = songRepo.findById(101).orElseThrow();
		// List<Artist> singers = albumServ.selectSingersBySong(song);
		// singers.forEach(a -> {
		// 	System.out.println(a);
		// });

		// List<Artist> composers = albumServ.selectComposerBySong(song);
		// composers.forEach(a -> {
		// 	System.out.println(a);
		// });

		// List<Artist> writers = albumServ.selectWriterBySong(song);
		// writers.forEach(a -> {
		// 	System.out.println(a);
		// });

		// List<Artist> arrangers = albumServ.selectArrangerBySong(song);
		// arrangers.forEach(a -> {
		// 	System.out.println(a);
		// });

	}

}
