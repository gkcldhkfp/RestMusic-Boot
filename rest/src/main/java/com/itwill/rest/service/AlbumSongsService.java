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
import com.itwill.rest.domain.Song;
import com.itwill.rest.repository.AlbumRepository;
import com.itwill.rest.repository.ArtistRoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumSongsService {
	private final AlbumRepository albumRepo;

	private final ArtistRoleRepository artistRoleRepo;

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
