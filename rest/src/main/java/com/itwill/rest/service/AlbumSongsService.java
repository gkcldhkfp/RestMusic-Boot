package com.itwill.rest.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.ArtistRole;
import com.itwill.rest.domain.GenreCode;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.SongGenre;
import com.itwill.rest.repository.AlbumRepository;
import com.itwill.rest.repository.ArtistRoleRepository;
import com.itwill.rest.repository.SongGenreRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumSongsService {
	private final AlbumRepository albumRepo;

	private final ArtistRoleRepository artistRoleRepo;

	private final SongGenreRepository songGenreRepo;

	/**
	 * 앨범 아이디로 앨범 객체를 리턴하는 메서드
	 */
	@Transactional(readOnly = true)
	public Album readById(Integer albumId) {
		log.info("read(albumId = {})", albumId);
		Album album = albumRepo.findById(albumId).orElseThrow();
		log.info("album = {}", album);
		return album;
	}

	/**
	 * 음원과 참여 아티스트를 매핑하는 메서드
	 */
	@Transactional(readOnly = true)
	public Map<Song, List<Artist>> getArtistsBySongsAndRoleId(List<Song> songs, Integer roleId) {
		// 맵 객체 생성
		Map<Song, List<Artist>> songArtistMap = new HashMap<>();
		songs.forEach(s -> {
			// 음원의 참여 아티스트를 역할 별로 가져오는 Jpa 쿼리
			List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(s, roleId);

			// ArtistRole 리스트를 Artist 리스트로 변환
			List<Artist> artists = artistRoles.stream().map(ArtistRole::getArtist).collect(Collectors.toList());

			// 맵에 원소 추가
			songArtistMap.put(s, artists);
		});

		// 맵을 리턴
		return songArtistMap;
	}

	/**
	 * 앨범 정보 출력을 위한 참여 가수
	 * 가장 많이 등장한 가수를 먼저 보여줌.
	 */

	@Transactional(readOnly = true)
	public List<Artist> getSortedArtists(List<Song> songs, Integer roleId) {
		Map<Artist, Integer> artistCountMap = new HashMap<>();

		for (Song song : songs) {
			List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, roleId);
			for (ArtistRole artistRole : artistRoles) {
				Artist artist = artistRole.getArtist();
				artistCountMap.put(artist, artistCountMap.getOrDefault(artist, 0) + 1);
			}
		}

		// 가장 많이 등장한 가수를 먼저 보여주고, 횟수가 동일하면 이름 순으로 정렬함.
		return artistCountMap.entrySet().stream()
				.sorted(Map.Entry.<Artist, Integer>comparingByValue(Comparator.reverseOrder())
						.thenComparing(Map.Entry.comparingByKey(Comparator.comparing(Artist::getArtistName))))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	/**
	 * 음원 객체로 그룹이 있다면 그룹 객체를, 없다면 null을 리턴하는 메서드
	 */
	@Transactional(readOnly = true)
	public Group selectGroupBySong(Song song) {
		List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, 10);
		if (artistRoles == null || artistRoles.size() == 0) {
			// 테스트할 때 아티스트가 없는 경우에도 음원을 재생하기 위한 조건문
			return null;
		}
		Group group = artistRoles.get(0).getGroup();
		return group;
	}

	/**
	 * 앨범 정보 출력을 위한 참여 가수
	 * 가장 많이 등장한 가수를 먼저 보여줌.
	 */

	@Transactional(readOnly = true)
	public List<GenreCode> getSortedGenres(List<Song> songs) {
		Map<GenreCode, Integer> genreCountMap = new HashMap<>();

		for (Song song : songs) {
			List<SongGenre> songGenres = song.getGenres();
			for (SongGenre songGenre : songGenres) {
				GenreCode genreCode = songGenre.getGenreCode();
				genreCountMap.put(genreCode, genreCountMap.getOrDefault(genreCode, 0) + 1);
			}
		}

		// 가장 많이 등장한 장르를 먼저 보여주고, 횟수가 동일하면 장르 이름 순으로 정렬함.
		return genreCountMap.entrySet().stream()
				.sorted(Map.Entry.<GenreCode, Integer>comparingByValue(Comparator.reverseOrder())
						.thenComparing(Map.Entry.comparingByKey(Comparator.comparing(GenreCode :: getGenreName))))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	/**
	 * 앨범 정보 출력을 위한 장르
	 * 가장 많이 등장한 장르를 먼저 보여줌.
	 */

	@Transactional(readOnly = true)
	public List<Group> getSortedGroups(List<Song> songs, Integer roleId) {
		Map<Group, Integer> groupCountMap = new HashMap<>();
		Set<String> processedGroupNames = new HashSet<>(); // 이미 처리된 그룹 이름을 추적하는 Set

		for (Song song : songs) {
			List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, roleId);
			for (ArtistRole artistRole : artistRoles) {
				Group group = artistRole.getGroup();
				if (group != null && !processedGroupNames.contains(group.getGroupName())) {
					// 동일한 이름의 그룹이 아직 처리되지 않은 경우에만 추가
					groupCountMap.put(group, groupCountMap.getOrDefault(group, 0) + 1);
					processedGroupNames.add(group.getGroupName());
				}
			}
		}

		// 가장 많이 등장한 가수를 먼저 보여주고, 횟수가 동일하면 이름 순으로 정렬함.
		return groupCountMap.entrySet().stream()
				.sorted(Map.Entry.<Group, Integer>comparingByValue(Comparator.reverseOrder())
						.thenComparing(Map.Entry.comparingByKey(Comparator.comparing(Group::getGroupName))))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	/**
	 * 음원 객체로 참여 아티스트 중 가수 아티스트 객체를 리턴하는 메서드
	 */
	@Transactional(readOnly = true)
	public List<Artist> selectSingersBySong(Song song) {
		List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, 10);
		List<Artist> artists = new ArrayList<>();
		artistRoles.forEach(ar -> {
			artists.add(ar.getArtist());
		});
		return artists;
	}

	/**
	 * 음원 객체로 참여 아티스트 중 작곡 아티스트 객체를 리턴하는 메서드
	 */
	@Transactional(readOnly = true)
	public List<Artist> selectComposerBySong(Song song) {
		List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, 20);
		List<Artist> artists = new ArrayList<>();
		artistRoles.forEach(ar -> {
			artists.add(ar.getArtist());
		});
		return artists;
	}

	/**
	 * 음원 객체로 참여 아티스트 중 작사 아티스트 객체를 리턴하는 메서드
	 */
	@Transactional(readOnly = true)
	public List<Artist> selectWriterBySong(Song song) {
		List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, 30);
		List<Artist> artists = new ArrayList<>();
		artistRoles.forEach(ar -> {
			artists.add(ar.getArtist());
		});
		return artists;
	}

	/**
	 * 음원 객체로 참여 아티스트 중 작사 아티스트 객체를 리턴하는 메서드
	 */
	@Transactional(readOnly = true)
	public List<Artist> selectArrangerBySong(Song song) {
		List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, 40);
		List<Artist> artists = new ArrayList<>();
		artistRoles.forEach(ar -> {
			artists.add(ar.getArtist());
		});
		return artists;
	}

	/**
	 * 앨범 객체로 참여 아티스트 중 가수 아티스트 객체를 리턴하는 메서드
	 */
	@Transactional(readOnly = true)
	public Set<Artist> selectSingersByAlbum(Album album) {
		List<ArtistRole> artistRoles = artistRoleRepo.findBySong_AlbumAndRoleCode_RoleId(album, 10);
		Set<Artist> artists = new HashSet<>();
		artistRoles.forEach(ar -> {
			artists.add(ar.getArtist());
		});
		return artists;
	}

}
