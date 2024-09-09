package com.itwill.rest.service;

import java.sql.Date;
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
import com.itwill.rest.domain.AlbumLike;
import com.itwill.rest.domain.AlbumLikeId;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.ArtistRole;
import com.itwill.rest.domain.GenreCode;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.SongGenre;
import com.itwill.rest.dto.AlbumLikeDto;
import com.itwill.rest.dto.AlbumSearchResultDto;
import com.itwill.rest.repository.AlbumLikeRepository;
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

	private final AlbumLikeRepository albumLikeRepo;

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
	 * 수록곡을 출력할 때 그룹과 가수를 처리하기 위한 서비스 메서드 그룹의 멤버는 그룹으로 대체해서 출력. 그룹 이름은 중복처리하여 리스트에
	 * 삽입 그룹에 포함되어 있지 않은 아티스트는 중복처리하여 리스트에 삽입
	 */
	@Transactional(readOnly = true)
	public Map<Song, List<Map<String, Object>>> getArtistsOrGroupsBySongsAndRoleId(List<Song> songs, Integer roleId) {
		Map<Song, List<Map<String, Object>>> songArtistGroupMap = new HashMap<>();

		for (Song song : songs) {
			Set<String> processedGroupNames = new HashSet<>();
			Set<String> processedArtistNames = new HashSet<>();
			List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, roleId);
			List<Map<String, Object>> artistsOrGroups = new ArrayList<>();

			for (ArtistRole artistRole : artistRoles) {
				Group group = artistRole.getGroup();
				Artist artist = artistRole.getArtist();

				if (group != null && !processedGroupNames.contains(group.getGroupName())) {
					Map<String, Object> groupMap = new HashMap<>();
					groupMap.put("type", "group");
					groupMap.put("data", group);
					artistsOrGroups.add(groupMap);
					processedGroupNames.add(group.getGroupName());
				} else if (group == null && !processedArtistNames.contains(artist.getArtistName())) {
					Map<String, Object> artistMap = new HashMap<>();
					artistMap.put("type", "artist");
					artistMap.put("data", artist);
					artistsOrGroups.add(artistMap);
					processedArtistNames.add(artist.getArtistName());
				}
			}
			songArtistGroupMap.put(song, artistsOrGroups);
		}

		return songArtistGroupMap;
	}

	/**
	 * 앨범 정보 출력을 위한 참여 가수 가장 많이 등장한 가수를 먼저 보여줌.
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
				.map(Map.Entry::getKey).collect(Collectors.toList());
	}

	/**
	 * 음원 객체로 그룹이 있다면 그룹 리스트를, 없다면 null을 리턴하는 메서드
	 */
	@Transactional(readOnly = true)
	public List<Group> selectGroupBySong(Song song) {
		List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, 10);
		List<Group> groups = new ArrayList<>();
		if (artistRoles == null || artistRoles.size() == 0) {
			// 테스트할 때 아티스트가 없는 경우에도 음원을 재생하기 위한 조건문
			return null;
		}
		for (ArtistRole artistRole : artistRoles) {
			Group group = artistRole.getGroup();
			if (group != null) {
				groups.add(group);
			}
		}
		return groups;
	}

	/**
	 * 앨범 정보 출력을 위한 참여 가수 가장 많이 등장한 가수를 먼저 보여줌.
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
						.thenComparing(Map.Entry.comparingByKey(Comparator.comparing(GenreCode::getGenreName))))
				.map(Map.Entry::getKey).collect(Collectors.toList());
	}

	/**
	 * 앨범 정보 출력을 위한 장르 가장 많이 등장한 장르를 먼저 보여줌.
	 */

	@Transactional(readOnly = true)
	public List<Group> getSortedGroups(List<Song> songs, Integer roleId) {
		Map<Group, Integer> groupCountMap = new HashMap<>();
		Set<String> processedGroupNames = new HashSet<>(); // 이미 처리된 그룹 이름을 추적하는 Set

		for (Song song : songs) {
			List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, roleId);
			for (ArtistRole artistRole : artistRoles) {
				Group group = artistRole.getGroup();
				if (group != null) {
					if (!processedGroupNames.contains(group.getGroupName())) {

						// 동일한 이름의 그룹이 아직 처리되지 않은 경우에만 추가
						groupCountMap.put(group, groupCountMap.getOrDefault(group, 0) + 1);
						processedGroupNames.add(group.getGroupName());
					}
				}
			}
		}

		// 가장 많이 등장한 그룹을 먼저 보여주고, 횟수가 동일하면 이름 순으로 정렬함.
		return groupCountMap.entrySet().stream()
				.sorted(Map.Entry.<Group, Integer>comparingByValue(Comparator.reverseOrder())
						.thenComparing(Map.Entry.comparingByKey(Comparator.comparing(Group::getGroupName))))
				.map(Map.Entry::getKey).collect(Collectors.toList());
	}

	/**
	 * 음원 객체로 참여 그룹의 리스트를 리턴하는 메서드
	 */
	@Transactional(readOnly = true)
	public List<Group> getGroupsBySong(Song song, Integer roleId) {
		Map<Group, Integer> groupCountMap = new HashMap<>();
		Set<String> processedGroupNames = new HashSet<>(); // 이미 처리된 그룹 이름을 추적하는 Set

		List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, roleId);
		for (ArtistRole artistRole : artistRoles) {
			Group group = artistRole.getGroup();
			if (group != null) {
				if (!processedGroupNames.contains(group.getGroupName())) {

					// 동일한 이름의 그룹이 아직 처리되지 않은 경우에만 추가
					groupCountMap.put(group, groupCountMap.getOrDefault(group, 0) + 1);
					processedGroupNames.add(group.getGroupName());
				}
			}
		}

		// 가장 많이 등장한 그룹을 먼저 보여주고, 횟수가 동일하면 이름 순으로 정렬함.
		return groupCountMap.entrySet().stream()
				.sorted(Map.Entry.<Group, Integer>comparingByValue(Comparator.reverseOrder())
						.thenComparing(Map.Entry.comparingByKey(Comparator.comparing(Group::getGroupName))))
				.map(Map.Entry::getKey).collect(Collectors.toList());
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
	
	
	public List<AlbumSearchResultDto> searchAllAlbums(String keyword){
		
		List<AlbumSearchResultDto> dtos = new ArrayList<AlbumSearchResultDto>();
		
		List<Object[]> results = albumRepo.searchAllAlbums(keyword);
    	
    	for (Object[] result : results) {
    		AlbumSearchResultDto dto = new AlbumSearchResultDto();
            dto.setAlbumId(((Number) result[0]).intValue());
            dto.setAlbumName((String) result[1]);
            dto.setAlbumImage(((String) result[2]));
            dto.setAlbumType((String) result[3]);
            if (result[4] != null) {
                Date sqlDate = (Date) result[4];
                dto.setAlbumReleaseDate(sqlDate.toLocalDate());
            } else {
                dto.setAlbumReleaseDate(null);
            }
            dto.setArtistName((String) result[5]);
            dto.setArtistId(result[6] != null ? ((Number) result[6]).intValue() : null);
            dto.setArtistType((String) result[7]);
						dto.setLikeCount(((Integer) (result[8] == null ? 0 : result[8])));
            dtos.add(dto);
        }
		
		
		return dtos;
	}
	
	public List<AlbumSearchResultDto> searchAlbums(String keyword, String sortType, int offset){
		
		List<AlbumSearchResultDto> dtos = new ArrayList<AlbumSearchResultDto>();
			List<Object[]> results = new ArrayList<>();
		if(sortType.equals("accuracy")) {
			results = albumRepo.searchAlbumsAccuracy(keyword, offset);
		} else if(sortType.equals("recency")) {
			results = albumRepo.searchAlbumsRecency(keyword, offset);
		} else if(sortType.equals("alphabet")) {
			results = albumRepo.searchAlbumsAlphabet(keyword, offset);
		}
		
		for (Object[] result : results) {
			AlbumSearchResultDto dto = new AlbumSearchResultDto();
			dto.setAlbumId(((Number) result[0]).intValue());
			dto.setAlbumName((String) result[1]);
			dto.setAlbumImage(((String) result[2]));
			dto.setAlbumType((String) result[3]);
			if (result[4] != null) {
				Date sqlDate = (Date) result[4];
				dto.setAlbumReleaseDate(sqlDate.toLocalDate());
			} else {
				dto.setAlbumReleaseDate(null);
			}
			dto.setArtistName((String) result[5]);
			dto.setArtistId(result[6] != null ? ((Number) result[6]).intValue() : null);
			dto.setArtistType((String) result[7]);
			dto.setLikeCount(((Integer) (result[8] == null ? 0 : result[8])));
			dtos.add(dto);
		}
		
		
		return dtos;
	}
	
	
	
	

	// 특정 사용자가 특정 노래를 좋아요 했는지 여부 확인
	public boolean isUserLikedAlbum(AlbumLikeDto dto) {
		log.info("isUserLikedSong(dto = {})", dto);
		AlbumLikeId albumLikeId = new AlbumLikeId(dto.getAlbumId(), dto.getLoginUserId());
		return albumLikeRepo.existsByAlbumLikeId(albumLikeId);
	}

	// 특정 곡의 좋아요 개수 확인
	public int countAlbumLikes(Integer albumId) {
		log.info("countAlbumLikes(albumId = {})", albumId);
		return (int) albumLikeRepo.countByAlbumLikeIdAlbumId(albumId);
	}

	// 좋아요 추가
	public void addAlbumLike(AlbumLikeDto dto) {
		log.info("addAlbumLike(dto = {})", dto);
		// Song 객체를 불러옴
		Album album = albumRepo.findById(dto.getAlbumId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid album ID"));

		// Like 객체를 생성하며 Song 객체를 설정
		AlbumLikeId albumLikeId = new AlbumLikeId(dto.getAlbumId(), dto.getLoginUserId());
		AlbumLike albumLike = AlbumLike.builder()
				.albumLikeId(albumLikeId)
				.album(album) // 여기에서 Song 설정
				.build();

		albumLikeRepo.save(albumLike);
	}

	// 좋아요 취소
	public void cancelAlbumLike(AlbumLikeDto dto) {
		log.info("cancelAlbumLike(dto = {})", dto);
		AlbumLikeId albumLikeId = new AlbumLikeId(dto.getAlbumId(), dto.getLoginUserId());
		albumLikeRepo.deleteById(albumLikeId);
	}

}
