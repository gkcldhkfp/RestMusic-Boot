package com.itwill.rest.repository;

import static org.assertj.core.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.itwill.rest.domain.GroupMember;
import com.itwill.rest.domain.GroupMemberId;
import com.itwill.rest.domain.RoleCode;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.SongGenre;
import com.itwill.rest.domain.SongGenreId;
import com.itwill.rest.domain.TitleSong;
import com.itwill.rest.domain.TitleSongId;
import com.itwill.rest.dto.AlbumSearchResultDto;
import com.itwill.rest.dto.ContentDto;

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
	private GroupMemberRepository GroupMemberRepo;

	@Autowired
	private ArtistRepository artistRepo;

	@Autowired
	private ArtistRoleRepository artistRoleRepo;

	@Autowired
	private TitleSongRepository titleSongRepo;

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

//	 @Test
	@Transactional
	public void findByIdTest() {

		log.info("findByIdTest()");

		Album album = albumRepo.findById(260).get(); // 천천히 가 앨범
		List<Song> titleSongs = album.getSongs();
		titleSongs.forEach(t -> {
			System.out.println(t);
		});

	}

	// @Test
	@Transactional
	public void saveGenreTest() {
		SongGenreId songGenreId = new SongGenreId();
		songGenreId.setGenreId(10);
		songGenreId.setSongId(10);
		System.out.println(songGenreId);
	}

	// @Test
	// @Transactional()
	public void saveTest() {

		Album album = Album.builder().albumImage("image").albumName("test").albumReleaseDate(LocalDate.now())
				.albumType("정규").build();
		album = albumRepo.save(album);
		log.info("album = {}", album);

		List<Song> songs = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			songs.add(Song.builder().album(album).title(i + "test음악제목").songPath(i + "test경로").lyrics(i + "test가사")
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

			songGenres.add(SongGenre.builder().id(songGenreId).song(s).genreCode(hip).build());
		});

		songGenres.forEach((g) -> {
			songGenreRepo.save(g);
		});
		// songGenreRepo.saveAll(songGenres);

		log.info("songGenres = {}", songGenres);

		Group group = Group.builder().groupName("응애").groupDescription("응애에여").build();

		groupRepo.save(group);

		log.info("group = {}", group);

		RoleCode 가수 = roleCodeRepo.findById(10).get();
		RoleCode 작곡 = roleCodeRepo.findById(20).get();
		RoleCode 작사 = roleCodeRepo.findById(30).get();
		RoleCode 편곡 = roleCodeRepo.findById(40).get();

		List<Artist> artists = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			artists.add(Artist.builder().artistName(i + " 번째 멤버").artistImage(i + " 이미지").artistDescription(i + " 설명")
					.build());
		}

		artistRepo.saveAll(artists);

		List<ArtistRole> artistRoles = new ArrayList<>();

		songs.forEach((s) -> {
			artists.forEach((a) -> {
				ArtistRoleId artistRoleId = new ArtistRoleId();
				artistRoleId.setArtistId(a.getId());
				artistRoleId.setRoleId(가수.getRoleId());
				artistRoleId.setSongId(s.getSongId());
				artistRoles
						.add(ArtistRole.builder().id(artistRoleId).song(s).group(group).artist(a).roleCode(가수).build());
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
		List<Song> songs = albumRepo.findById(1).get().getSongs();
		if (songs != null && !songs.isEmpty()) {

			// log.info("{}",songs.size());
			songs.forEach((s) -> System.out.println(s));
		}
	}


//	 @Test
//	 @Transactional
	public void saveBol4Album() {

		Album album = Album.builder().albumName("RED PLANET").albumImage("bol4_RED PLANET_albumcover.png")
				.albumReleaseDate(LocalDate.of(2016, 8, 29)).albumType("정규").build();
		album = albumRepo.save(album);
		log.info("album = {}", album);

		List<Song> songs = new ArrayList<>();

		// 볼사 음원 객체 생성
		Song song1 = Song.builder().album(album).title("우주를 줄게").songPath("BOL4 - Galaxy(우주를 줄게).mp3")
				.lyrics("bol4우주를 줄게.txt").build();

		songs.add(song1);

		Song song2 = Song.builder().album(album).title("싸운날").songPath("BOL4 - 싸운날.mp3").lyrics("bol4싸운날.txt").build();

		songs.add(song2);

		Song song3 = Song.builder().album(album).title("You(=I)").songPath("BOL4 - You(=I).mp3")
				.lyrics("bol4You(=I).txt").build();

		songs.add(song3);

		Song song4 = Song.builder().album(album).title("심술").songPath("BOL4 - Mean(심술).mp3").lyrics("bol4심술.txt")
				.build();

		songs.add(song4);

		Song song5 = Song.builder().album(album).title("나만 안되는 연애").songPath("BOL4 - 나만 안되는 연애.mp3")
				.lyrics("bol4나만 안되는 연애.txt").build();

		songs.add(song5);

		Song song6 = Song.builder().album(album).title("초콜릿").songPath("BOL4 - Chocolate Drive(초콜릿).mp3")
				.lyrics("bol4초콜릿.txt").build();

		songs.add(song6);

		Song song7 = Song.builder().album(album).title("프리지아").songPath("BOL4 - 프리지아.mp3").lyrics("bol4프리지아.txt")
				.build();

		songs.add(song7);

		Song song8 = Song.builder().album(album).title("X Song").songPath("BOL4 - X Song.mp3").lyrics("bol4X Song.txt")
				.build();

		songs.add(song8);

		Song song9 = Song.builder().album(album).title("반지").songPath("BOL4 - 반지.mp3").lyrics("bol4반지.txt").build();

		songs.add(song9);

		Song song10 = Song.builder().album(album).title("사랑에 빠졌을 때").songPath("BOL4 - 사랑에 빠졌을 때.mp3")
				.lyrics("bol4사랑에 빠졌을 때.txt").build();

		songs.add(song10);

		// 음원들을 DB에 저장
		songRepo.saveAll(songs);

		// 타이틀 곡 설정
		List<TitleSong> titleSongs = new ArrayList<>();
		TitleSongId titleSongId1 = new TitleSongId(song1.getSongId(), album.getAlbumId());
		TitleSong titleSong1 = TitleSong.builder().id(titleSongId1).album(album).song(song1).build();

		titleSongs.add(titleSong1);

		TitleSongId titleSongId2 = new TitleSongId(song5.getSongId(), album.getAlbumId());
		TitleSong titleSong2 = TitleSong.builder().id(titleSongId2).album(album).song(song5).build();

		titleSongs.add(titleSong2);

		titleSongRepo.saveAll(titleSongs);

		// 음원 장르 등록

		GenreCode 발라드 = genreCodeRepo.findById(10).get();
		GenreCode 팝 = genreCodeRepo.findById(20).get();
		GenreCode 댄스일렉 = genreCodeRepo.findById(30).get();
		GenreCode 알앤비 = genreCodeRepo.findById(40).get();
		GenreCode 힙합 = genreCodeRepo.findById(50).get();
		GenreCode 트로트 = genreCodeRepo.findById(60).get();
		GenreCode OST = genreCodeRepo.findById(70).get();
		GenreCode 인디 = genreCodeRepo.findById(80).get();
		GenreCode 포크블루스 = genreCodeRepo.findById(90).get();
		GenreCode 록메탈 = genreCodeRepo.findById(100).get();

		List<SongGenre> genres = new ArrayList<>();

		songs.forEach(s -> {
			SongGenre 인디등록 = SongGenre.builder().genreCode(인디).song(s)
					.id(new SongGenreId(s.getSongId(), 인디.getCodeId())).build();
			genres.add(인디등록);

			SongGenre 포크등록 = SongGenre.builder().genreCode(포크블루스).song(s)
					.id(new SongGenreId(s.getSongId(), 포크블루스.getCodeId())).build();
			genres.add(포크등록);
		});
		songGenreRepo.saveAll(genres);

		// 그룹 객체 생성
		Group bol4 = Group.builder().groupName("볼빨간 사춘기").groupDescription("볼빨간 사춘기 멤버 = 안지영, 우지윤(탈퇴)").build();

		groupRepo.save(bol4);

		// 아티스트 객체 생성
		Artist ahn = Artist.builder().artistName("안지영").artistImage("볼빨간사춘기아티스트사진.png").artistDescription("bol4_설명.txt")
				.build();

		// DB에 삽입
		artistRepo.save(ahn);

		Artist woo = Artist.builder().artistName("우지윤").artistImage("볼빨간사춘기아티스트사진.png").artistDescription("bol4_설명.txt")
				.build();

		artistRepo.save(woo);

		Artist van = Artist.builder().artistName("바닐라맨").build();

		artistRepo.save(van);

		Artist hwang = Artist.builder().artistName("황종하").build();

		artistRepo.save(hwang);

		// 그룹 멤버 채우기
		List<GroupMember> members = new ArrayList<>();
		GroupMember member1 = GroupMember.builder().group(bol4).artist(ahn)
				.id(new GroupMemberId(bol4.getId(), ahn.getId())).build();

		members.add(member1);

		GroupMember member2 = GroupMember.builder().group(bol4).artist(woo)
				.id(new GroupMemberId(bol4.getId(), woo.getId())).build();

		members.add(member2);

		GroupMemberRepo.saveAll(members);

		// 역할 등록

		RoleCode 가수 = roleCodeRepo.findById(10).get();
		RoleCode 작곡 = roleCodeRepo.findById(20).get();
		RoleCode 작사 = roleCodeRepo.findById(30).get();
		RoleCode 편곡 = roleCodeRepo.findById(40).get();
		List<ArtistRole> roles = new ArrayList<>();

		ArtistRole song1Role1 = ArtistRole.builder().song(song1).roleCode(가수).group(bol4).artist(ahn)
				.id(new ArtistRoleId(ahn.getId(), song1.getSongId(), 가수.getRoleId())).build();

		roles.add(song1Role1);

		ArtistRole song1Role2 = ArtistRole.builder().song(song1).roleCode(가수).group(bol4).artist(woo)
				.id(new ArtistRoleId(woo.getId(), song1.getSongId(), 가수.getRoleId())).build();

		roles.add(song1Role2);

		ArtistRole song1Role3 = ArtistRole.builder().song(song1).roleCode(작사).group(bol4).artist(ahn)
				.id(new ArtistRoleId(ahn.getId(), song1.getSongId(), 작사.getRoleId())).build();

		roles.add(song1Role3);

		ArtistRole song1Role4 = ArtistRole.builder().song(song1).roleCode(작사).group(bol4).artist(woo)
				.id(new ArtistRoleId(woo.getId(), song1.getSongId(), 작사.getRoleId())).build();

		roles.add(song1Role4);

		ArtistRole song1Role5 = ArtistRole.builder().song(song1).roleCode(작곡).group(bol4).artist(ahn)
				.id(new ArtistRoleId(ahn.getId(), song1.getSongId(), 작곡.getRoleId())).build();

		roles.add(song1Role5);

		ArtistRole song1Role6 = ArtistRole.builder().song(song1).roleCode(작곡).group(bol4).artist(ahn)
				.id(new ArtistRoleId(ahn.getId(), song1.getSongId(), 작곡.getRoleId())).build();

		roles.add(song1Role6);

		ArtistRole song1Role7 = ArtistRole.builder().song(song1).roleCode(작곡).artist(van)
				.id(new ArtistRoleId(van.getId(), song1.getSongId(), 작곡.getRoleId())).build();

		roles.add(song1Role7);

		ArtistRole song1Role8 = ArtistRole.builder().song(song1).roleCode(편곡).artist(van)
				.id(new ArtistRoleId(van.getId(), song1.getSongId(), 편곡.getRoleId())).build();

		roles.add(song1Role8);

		ArtistRole song2Role1 = ArtistRole.builder().song(song2).roleCode(가수).group(bol4).artist(ahn)
				.id(new ArtistRoleId(ahn.getId(), song2.getSongId(), 가수.getRoleId())).build();

		roles.add(song2Role1);

		ArtistRole song2Role2 = ArtistRole.builder().song(song2).roleCode(가수).group(bol4).artist(woo)
				.id(new ArtistRoleId(woo.getId(), song2.getSongId(), 가수.getRoleId())).build();

		roles.add(song2Role2);

		ArtistRole song2Role3 = ArtistRole.builder().song(song2).roleCode(작사).group(bol4).artist(ahn)
				.id(new ArtistRoleId(ahn.getId(), song2.getSongId(), 작사.getRoleId())).build();

		roles.add(song2Role3);

		ArtistRole song2Role4 = ArtistRole.builder().song(song2).roleCode(작곡).group(bol4).artist(ahn)
				.id(new ArtistRoleId(ahn.getId(), song2.getSongId(), 작곡.getRoleId())).build();

		roles.add(song2Role4);

		ArtistRole song2Role5 = ArtistRole.builder().song(song2).roleCode(편곡).artist(van)
				.id(new ArtistRoleId(van.getId(), song2.getSongId(), 편곡.getRoleId())).build();

		roles.add(song2Role5);

		ArtistRole song3Role1 = ArtistRole.builder().song(song3).roleCode(가수).group(bol4).artist(ahn)
				.id(new ArtistRoleId(ahn.getId(), song3.getSongId(), 가수.getRoleId())).build();

		roles.add(song3Role1);

		ArtistRole song3Role2 = ArtistRole.builder().song(song3).roleCode(가수).group(bol4).artist(woo)
				.id(new ArtistRoleId(woo.getId(), song3.getSongId(), 가수.getRoleId())).build();

		roles.add(song3Role2);

		ArtistRole song3Role3 = ArtistRole.builder().song(song3).roleCode(작사).group(bol4).artist(ahn)
				.id(new ArtistRoleId(ahn.getId(), song3.getSongId(), 작사.getRoleId())).build();

		roles.add(song3Role3);

		ArtistRole song3Role4 = ArtistRole.builder().song(song3).roleCode(작곡).group(bol4).artist(ahn)
				.id(new ArtistRoleId(ahn.getId(), song3.getSongId(), 작곡.getRoleId())).build();

		roles.add(song3Role4);

		ArtistRole song3Role5 = ArtistRole.builder().song(song3).roleCode(편곡).artist(van)
				.id(new ArtistRoleId(van.getId(), song3.getSongId(), 편곡.getRoleId())).build();

		roles.add(song3Role5);

		artistRoleRepo.saveAll(roles);

	}


	// @Test
	@Transactional(readOnly = true)
	public void ttest() {
		long startTime = System.currentTimeMillis();

		List<ContentDto> dtos = convertToContentDto(albumRepo.findAllContentByKeyword("밤양갱"));

		
		// 종료 시간 기록
		long endTime = System.currentTimeMillis();
		log.info("chosungAlbumTest 종료");
		
		// 걸린 시간 계산 및 출력
		long duration = endTime - startTime;
		dtos.forEach(x -> System.out.println(x));
		System.out.println(dtos.size());
		log.info("테스트 실행 시간: " + duration + "ms");
	}
	
	// @Test
	@Transactional(readOnly = true)
	public void fullTexttest() {
		long startTime = System.currentTimeMillis();

		List<ContentDto> dtos = convertToContentDto(albumRepo.findAllContentByKeywordFullText("밤양갱"));

		
		// 종료 시간 기록
		long endTime = System.currentTimeMillis();
		log.info("chosungAlbumTest 종료");
		dtos.forEach(x -> System.out.println(x));

		// 걸린 시간 계산 및 출력
		long duration = endTime - startTime;
		log.info("테스트 실행 시간: " + duration + "ms");
	}

	public List<ContentDto> convertToContentDto(List<Object[]> results) {
		List<ContentDto> contentDtos = new ArrayList<>();
		for (Object[] result : results) {
			ContentDto dto = new ContentDto();
			dto.setType((String) result[0]);
			dto.setId((Integer) result[1]);
			dto.setName((String) result[2]);
			dto.setReleaseDate(result[3] != null ? (Date) result[3] : null);
			dto.setLikeCount(((Long) result[4]).intValue()); // assuming like_count is a BigInteger
			contentDtos.add(dto);
		}
		return contentDtos;
	}
	



}