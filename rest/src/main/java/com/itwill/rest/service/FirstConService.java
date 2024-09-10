package com.itwill.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.AlbumFirstCon;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.ArtistFirstCon;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.GroupFirstCon;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.SongFirstCon;
import com.itwill.rest.dto.ChoSeongSearchDto;
import com.itwill.rest.repository.AlbumFirstConRepository;
import com.itwill.rest.repository.AlbumRepository;
import com.itwill.rest.repository.ArtistFirstConRepository;
import com.itwill.rest.repository.ArtistRepository;
import com.itwill.rest.repository.GroupFirstConRepository;
import com.itwill.rest.repository.GroupRepository;
import com.itwill.rest.repository.SongFirstConRepository;
import com.itwill.rest.repository.SongRepository;
import com.itwill.rest.util.KoreanUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirstConService {

	private final AlbumRepository albumRepo;
	private final AlbumFirstConRepository albumFirstConRepo;

	private final SongRepository songRepo;
	private final SongFirstConRepository songFirstConRepo;

	private final ArtistRepository artistRepo;
	private final ArtistFirstConRepository artistFirstConRepo;

	private final GroupRepository groupRepo;
	private final GroupFirstConRepository groupFirstConRepo;

	/**
	 * 앨범 데이터를 초성테이블에 변환 후 삽입하는 서비스 메서드
	 */
	public List<AlbumFirstCon> synchAlbumFirstCon() {
		List<Album> albums = albumRepo.findAll();
		// 현재 모든 앨범을 불러옴.
		List<AlbumFirstCon> albumFirstCons = new ArrayList<>();
		for (Album album : albums) {
			String albumNameWithoutSpaces = album.getAlbumName().replaceAll("\\s+", "");
			// 앨범 이름에서 공백을 제거
			AlbumFirstCon albumFirstConName = AlbumFirstCon.builder()
					.album(album)
					.albumFirstConName(KoreanUtils.getInitialSound(albumNameWithoutSpaces))
					.build();
			// 초성 앨범 객체를 생성
			albumFirstCons.add(albumFirstConName);
			// 리스트에 추가
		}
		List<AlbumFirstCon> savedAlbumFirstCon = albumFirstConRepo.saveAll(albumFirstCons);
		// 리스트를 데이터베이스에 삽입.
		return savedAlbumFirstCon;
		// 결과 확인 용 리스트를 리턴.
	}

	/**
	 * 음원 데이터를 초성테이블에 변환 후 삽입하는 서비스 메서드
	 */
	public List<SongFirstCon> synchSongFirstCon() {
		List<Song> songs = songRepo.findAll();
		// 현재 모든 앨범을 불러옴.
		List<SongFirstCon> songFirstCons = new ArrayList<>();
		for (Song song : songs) {
			String songTitleWithoutSpaces = song.getTitle().replaceAll("\\s+", "");
			// 노래 제목에서 공백을 제거
			SongFirstCon songFirstConName = SongFirstCon.builder()
					.song(song)
					.songFirstConName(KoreanUtils.getInitialSound(songTitleWithoutSpaces))
					.build();
			// 초성 앨범 객체를 생성
			songFirstCons.add(songFirstConName);
			// 리스트에 추가
		}
		List<SongFirstCon> savedSongFirstCon = songFirstConRepo.saveAll(songFirstCons);
		// 리스트를 데이터베이스에 삽입.
		return savedSongFirstCon;
		// 결과 확인 용 리스트를 리턴.
	}

	/**
	 * 아티스트 데이터를 초성테이블에 변환 후 삽입하는 서비스 메서드
	 */
	public List<ArtistFirstCon> synchArtistFirstCon() {
		List<Artist> artists = artistRepo.findAll();
		// 현재 모든 아티스트을 불러옴.
		List<ArtistFirstCon> artistFirstCons = new ArrayList<>();
		for (Artist artist : artists) {
			String artistNameWithoutSpaces = artist.getArtistName().replaceAll("\\s+", "");
			// 아티스트 이름에서 공백을 제거
			ArtistFirstCon artistFirstConName = ArtistFirstCon.builder()
					.artist(artist)
					.artistFirstConName(KoreanUtils.getInitialSound(artistNameWithoutSpaces))
					.build();
			// 초성 아티스트 객체를 생성
			artistFirstCons.add(artistFirstConName);
			// 리스트에 추가
		}
		List<ArtistFirstCon> savedArtistFirstCon = artistFirstConRepo.saveAll(artistFirstCons);
		// 리스트를 데이터베이스에 삽입.
		return savedArtistFirstCon;
		// 결과 확인 용 리스트를 리턴.
	}

	/**
	 * 그룹 데이터를 초성테이블에 변환 후 삽입하는 서비스 메서드
	 */
	public List<GroupFirstCon> synchGroupFirstCon() {
		List<Group> groups = groupRepo.findAll();
		// 현재 모든 그룹을 불러옴.
		List<GroupFirstCon> groupFirstCons = new ArrayList<>();
		for (Group group : groups) {
			String groupNameWithoutSpaces = group.getGroupName().replaceAll("\\s+", "");
			// 그룹 이름에서 공백을 제거
			GroupFirstCon groupFirstConName = GroupFirstCon.builder()
					.group(group)
					.groupFirstConName(KoreanUtils.getInitialSound(groupNameWithoutSpaces))
					.build();
			// 초성 그룹 객체를 생성
			groupFirstCons.add(groupFirstConName);
			// 리스트에 추가
		}
		List<GroupFirstCon> savedGroupFirstCon = groupFirstConRepo.saveAll(groupFirstCons);
		// 리스트를 데이터베이스에 삽입.
		return savedGroupFirstCon;
		// 결과 확인 용 리스트를 리턴.
	}

	/**
	 * 초성으로 구성된 키워드를 전달받으면 초성에 해당하는 id로 name을 저장하는 서비스
	 */
	public List<ChoSeongSearchDto> searchDtos(String keyword) {
		String keywordWithoutSpaces = keyword.replaceAll("\\s+", "");
		List<ChoSeongSearchDto> choSeongBySearchTerm = convertToContentDto(albumRepo.findChoSeongBySearchTerm(keywordWithoutSpaces));
		return choSeongBySearchTerm;
	}

		public List<ChoSeongSearchDto> convertToContentDto(List<Object[]> results) {
		List<ChoSeongSearchDto> choSeongSearchDtos = new ArrayList<>();
		for (Object[] result : results) {
			ChoSeongSearchDto dto = new ChoSeongSearchDto();
			dto.setType((String) result[0]);
			dto.setId((Integer) result[1]);
			dto.setName((String) result[2]);
			dto.setLikeCount(((Number) (result[4] == null ? 0 : result[4])).intValue()); // assuming like_count is a BigInteger
			choSeongSearchDtos.add(dto);
		}
		return choSeongSearchDtos;
	}
}
