package com.itwill.rest.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.ArtistRole;
import com.itwill.rest.domain.ArtistRoleId;
import com.itwill.rest.domain.GenreCode;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.RoleCode;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.SongGenre;
import com.itwill.rest.domain.SongGenreId;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class AlbumRepositoryTest {

	@Autowired
	private AlbumRepository albumRepo;

	@Autowired
	private SongRepository songRepo;

	@Autowired
	private GenreCodeRepository genreCodeRepo;

	@Autowired
	private RoleCodeRepository roleCodeRepo;

	@Autowired
	private SongGenreRepository songGenreRepo;

	@Autowired
	private GroupRepository groupRepo;

	@Autowired
	private ArtistRepository artistRepo;

	@Autowired
	private ArtistRoleRepository artistRoleRepo;

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

		albums.forEach((a) -> {
			System.out.println(a);
		});
		albums.forEach((a) -> {
			System.out.println(a.getTitleSongs());
		});

	}

	// @Test
	@Transactional
	public void findByIdTest() {

		log.info("findByIdTest()");

		Album album = albumRepo.findById(3).get(); // 천천히 가 앨범

	}

	// @Test
	@Transactional
	public void saveGenreTest() {
		SongGenreId songGenreId = new SongGenreId();
		songGenreId.setGenreId(10);
		songGenreId.setSongId(10);
		System.out.println(songGenreId);
	}

//	 @Test
//	@Transactional()
	public void saveTest() {

		Album album = Album
				.builder()
				.albumImage("image")
				.albumName("test")
				.albumReleaseDate(LocalDate.now())
				.albumType("정규")
				.build();
		album = albumRepo.save(album);
		log.info("album = {}", album);


		List<Song> songs = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			songs.add(
					Song.builder()
							.album(album)
							.title(i + "test음악제목")
							.songPath(i + "test경로")
							.lyrics(i + "test가사")
							.build());
		}
		songRepo.saveAll(songs);
		songs.forEach((s) -> {
			System.out.println(s);
		});

		GenreCode hip = genreCodeRepo.findByGenreName("힙합");
		log.info("genrecode = {}", hip);

		List<SongGenre> songGenres = new ArrayList<>();
		
		// 모든 곡에 힙합 장르 추가
		songs.forEach((s) -> {
			SongGenreId songGenreId = new SongGenreId();
			songGenreId.setSongId(s.getSongId());
			songGenreId.setGenreId(hip.getGenreId());

			songGenres.add(
				SongGenre.builder()
				.id(songGenreId)
				.song(s)
				.genreCode(hip)
				.build()
			);
		});

		songGenres.forEach((g) -> {
			songGenreRepo.save(g);
		});
		// songGenreRepo.saveAll(songGenres);

		log.info("songGenres = {}", songGenres);

		Group group = Group
				.builder()
				.groupName("응애")
				.groupDescription("응애에여")
				.build();

		groupRepo.save(group);

		log.info("group = {}", group);

		RoleCode 가수 = roleCodeRepo.findById(10).get();
		RoleCode 작곡 = roleCodeRepo.findById(20).get();
		RoleCode 작사 = roleCodeRepo.findById(30).get();
		RoleCode 편곡 = roleCodeRepo.findById(40).get();


		List<Artist> artists = new ArrayList<>();
		for(int i = 1; i <= 10; i++){
			artists.add(
				Artist.builder()
					.artistName(i+" 번째 멤버")
					.artistImage(i+" 이미지")
					.artistDescription(i+" 설명")
				.build()
			);
		}

		artistRepo.saveAll(artists);

		List<ArtistRole> artistRoles = new ArrayList<>();


		songs.forEach((s) -> {
			artists.forEach((a) -> {
				ArtistRoleId artistRoleId = new ArtistRoleId();
				artistRoleId.setArtistId(a.getId());
				artistRoleId.setGroupId(group.getId());
				artistRoleId.setRoleId(가수.getRoleId());
				artistRoleId.setSongId(s.getSongId());
				artistRoles.add(
					ArtistRole.builder()
						.id(artistRoleId)
						.song(s)
						.group(group)
						.artist(a)
						.roleCode(가수)
						.build()
				);
			});
		});
		artistRoleRepo.saveAll(artistRoles);
	}

	// @Test
	public void deleteTest() {
		log.info("deleteTest");
		Album album = albumRepo.findById(17).get();
		albumRepo.delete(album);
	}
	
//	@Test
	@Transactional
	public void albumSongsTest() {
		Set<Song> songs = albumRepo.findById(1).get().getSongs();
		 if (songs != null && !songs.isEmpty()) {
		        
			 // log.info("{}",songs.size());
			 songs.forEach((s) -> System.out.println(s));
		    }
	}

}
