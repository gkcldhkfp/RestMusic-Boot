package com.itwill.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.ArtistLike;
import com.itwill.rest.domain.ArtistLikeId;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.GroupMember;
import com.itwill.rest.dto.ArtistAlbumDto;
import com.itwill.rest.dto.ArtistSongDto;
import com.itwill.rest.dto.GroupAndArtistDto;
import com.itwill.rest.repository.AlbumRepository;
import com.itwill.rest.repository.ArtistLikeRepository;
import com.itwill.rest.repository.ArtistRepository;
import com.itwill.rest.repository.GroupMemberRepository;
import com.itwill.rest.repository.GroupRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArtistService {

	private final ArtistRepository artistRepo;
	private final GroupRepository groupRepo;
	private final GroupMemberRepository groupMemberRepo;
	private final AlbumRepository albumRepo;
	private final ArtistLikeRepository artistLikeRepo;

	@Transactional(readOnly = true)
	public Artist findById(Integer id) {
		log.info("findById={}", id);

		Artist artist = artistRepo.findById(id).orElseThrow();

		return artist;
	}

	@Transactional(readOnly = true)
	public List<ArtistSongDto> readSongs(Integer artistId) {
		log.info("readSongs(artistId={})", artistId);

		// 1. artistId로 해당 ARTIST가 참여한 앨범 ID를 조회
		List<Integer> albumIds = albumRepo.findAlbumIdsByArtistId(artistId);

		// 2. 앨범 ID 리스트를 이용하여 앨범 정보를 조회
		List<Album> albums = albumRepo.findAllById(albumIds);

		// 3. 앨범에 포함된 곡 정보를 추출하여 그룹이 참여한 곡만 필터링
		return albums.stream().flatMap(album -> album.getSongs().stream())
				.filter(song -> song.getArtistRole().stream()
						.anyMatch(artistRole -> artistId.equals(artistRole.getArtist().getId()))) // artistId를 equal 시켜서
																									// 앨범에서 해당 artist가
																									// 참여한 곡들만 get해옴.
				.map(song -> {
					// 곡에 포함된 아티스트와 그룹을 추출
					List<Artist> songArtists = song.getArtistRole().stream()
							.filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10)
							.map(artistRole -> artistRole.getArtist()).distinct().collect(Collectors.toList());

					List<Group> songGroups = song.getArtistRole().stream()
							.filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10)
							.filter(artistRole -> artistRole.getGroup() != null)
							.map(artistRole -> artistRole.getGroup()).filter(groupObj -> groupObj != null).distinct()
							.collect(Collectors.toList());

					// 그룹에 속한 아티스트 ID 목록
					List<Integer> groupArtistIds = songGroups.stream()
							.flatMap(group -> groupMemberRepo.findByGroupId(group.getId()).stream())
							.map(groupMember -> groupMember.getArtist().getId()).collect(Collectors.toList());

					// 아티스트 ID와 이름 리스트 생성 (그룹에 속하지 않은 아티스트만)
					List<Integer> artistIds = songArtists.stream()
							.filter(artist -> !groupArtistIds.contains(artist.getId())).map(Artist::getId)
							.collect(Collectors.toList());

					List<String> artistNames = songArtists.stream()
							.filter(artist -> !groupArtistIds.contains(artist.getId())).map(Artist::getArtistName)
							.collect(Collectors.toList());

					// 그룹 ID와 이름 리스트 생성
					List<Integer> groupIds = songGroups.stream().map(Group::getId).collect(Collectors.toList());

					List<String> groupNames = songGroups.stream().map(Group::getGroupName).collect(Collectors.toList());

					return ArtistSongDto.builder().songId(song.getSongId()).albumId(song.getAlbum().getAlbumId())
							.albumName(song.getAlbum().getAlbumName()).albumImage(song.getAlbum().getAlbumImage())
							.title(song.getTitle()).artistIds(artistIds).artistNames(artistNames).groupIds(groupIds)
							.groupNames(groupNames).build();
				}).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ArtistAlbumDto> readAlbums(Integer artistId) {
		log.info("readAlbums={artistId={}}", artistId);

		// 1. artistId로 해당 GROUP이 참여한 앨범 ID를 조회
		List<Integer> albumIds = albumRepo.findAlbumIdsByArtistId(artistId);

		// 2. 앨범 ID 리스트를 이용하여 앨범 정보를 조회
		List<Album> albums = albumRepo.findAllById(albumIds);

		// 3. 앨범에 포함된 아티스트와 그룹 정보를 추출하여 DTO로 변환
		List<ArtistAlbumDto> artistAlbumDtos = albums.stream().map(album -> {
			// 앨범에 포함된 아티스트와 그룹을 추출
			List<Artist> albumArtists = album.getSongs().stream().flatMap(song -> song.getArtistRole().stream())
					.filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10)
					.map(artistRole -> artistRole.getArtist()).distinct().collect(Collectors.toList());

			List<Group> albumGroups = album.getSongs().stream().flatMap(song -> song.getArtistRole().stream())
					.filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10)
					.filter(artistRole -> artistRole.getGroup() != null).map(artistRole -> artistRole.getGroup())
					.filter(groupObj -> groupObj != null).distinct().collect(Collectors.toList());

			// 그룹에 속한 아티스트 ID 목록
			List<Integer> groupArtistIds = albumGroups.stream()
					.flatMap(group -> groupMemberRepo.findByGroupId(group.getId()).stream())
					.map(groupMember -> groupMember.getArtist().getId()).collect(Collectors.toList());

			// 아티스트 ID와 이름 리스트 생성 (그룹에 속하지 않은 아티스트만)
			List<Integer> artistIds = albumArtists.stream().filter(artist -> !groupArtistIds.contains(artist.getId()))
					.map(Artist::getId).collect(Collectors.toList());

			List<String> artistNames = albumArtists.stream().filter(artist -> !groupArtistIds.contains(artist.getId()))
					.map(Artist::getArtistName).collect(Collectors.toList());

			// 그룹 ID와 이름 리스트 생성
			List<Integer> groupIds = albumGroups.stream().map(Group::getId).collect(Collectors.toList());

			List<String> groupNames = albumGroups.stream().map(Group::getGroupName).collect(Collectors.toList());

			return ArtistAlbumDto.builder().albumId(album.getAlbumId()).albumName(album.getAlbumName())
					.albumImage(album.getAlbumImage()).albumType(album.getAlbumType())
					.albumReleaseDate(album.getAlbumReleaseDate()).artistIds(artistIds).artistNames(artistNames)
					.groupIds(groupIds).groupNames(groupNames).build();
		}).collect(Collectors.toList());

		return artistAlbumDtos;
	}

	public boolean isLiked(ArtistLikeId artistLikeId) {

		return artistLikeRepo.existsById(artistLikeId);

	}

	public boolean like(ArtistLikeId artistLikeId) {
		log.info("like(likeId={})", artistLikeId);

		boolean exists = artistLikeRepo.existsById(artistLikeId);

		log.info("like={}", exists);

		if (exists) {
			artistLikeRepo.deleteById(artistLikeId);
			return false;
		} else {
			ArtistLike like = ArtistLike.builder().artistLikeId(artistLikeId).build();
			artistLikeRepo.save(like);
			return true;

		}
	}

	public List<GroupAndArtistDto> searchAllArtist(String keyword) {

		List<GroupAndArtistDto> dtos = new ArrayList<>();

		List<Object[]> results = artistRepo.searchAllArtist(keyword);

		for (Object[] result : results) {
			GroupAndArtistDto dto = new GroupAndArtistDto();
			dto.setType("artist");
			dto.setId(((Number) result[0]).intValue());
			dto.setName((String) result[1]);
			dto.setImage(((String) result[2]));
			dto.setLikeCount(((Number) (result[3] == null ? 0 : result[3])).intValue());
			dtos.add(dto);
		}

		return dtos;

	}

	public List<GroupAndArtistDto> searchAllGroups(String keyword) {

		List<GroupAndArtistDto> dtos = new ArrayList<>();

		List<Object[]> results = groupRepo.searchAllGroup(keyword);

		for (Object[] result : results) {
			GroupAndArtistDto dto = new GroupAndArtistDto();
			dto.setType("group");
			dto.setId(((Number) result[0]).intValue());
			dto.setName((String) result[1]);
			dto.setImage(((String) result[2]));
			dto.setLikeCount(((Number) (result[3] == null ? 0 : result[3])).intValue());
			dtos.add(dto);
		}

		return dtos;

	}

	public List<GroupAndArtistDto> searchArtists(String keyword, String sortType, int offset) {

		List<GroupAndArtistDto> arDtos = new ArrayList<>();
		List<GroupAndArtistDto> grDtos = new ArrayList<>();

		List<Object[]> grResults = new ArrayList<>();
		List<Object[]> arResults = new ArrayList<>();

		if (sortType.equals("accuracy")) {
			grResults = groupRepo.searchGroupAccuracy(keyword, offset);
			arResults = artistRepo.searchArtistAccuracy(keyword, offset);
		} else if (sortType.equals("alphabet")) {
			grResults = groupRepo.searchGroupAlphabet(keyword, offset);
			arResults = artistRepo.searchArtistAlphabet(keyword, offset);
		} else {
			return null;
		}


		// 리스트가 null인지 확인하고, null일 경우 빈 리스트로 초기화
		arResults = (arResults != null) ? arResults : new ArrayList<>();
		grResults = (grResults != null) ? grResults : new ArrayList<>();

		for (Object[] result : grResults) {
			GroupAndArtistDto dto = new GroupAndArtistDto();
			dto.setType("group");
			dto.setId(((Number) result[0]).intValue());
			dto.setName((String) result[1]);
			dto.setImage(((String) result[2]));
			dto.setLikeCount(((Number) (result[3] == null ? 0 : result[3])).intValue());
			grDtos.add(dto);
		}

		for (Object[] result : arResults) {
			GroupAndArtistDto dto = new GroupAndArtistDto();
			dto.setType("artist");
			dto.setId(((Number) result[0]).intValue());
			dto.setName((String) result[1]);
			dto.setImage(((String) result[2]));
			dto.setLikeCount(((Number) (result[3] == null ? 0 : result[3])).intValue());
			arDtos.add(dto);
		}

		// 두 리스트를 합치기
		List<GroupAndArtistDto> combinedList = new ArrayList<>();
		combinedList.addAll(arDtos);
		combinedList.addAll(grDtos);

		// combinedList가 비어 있는지 확인하고, 비어 있지 않으면 정렬
		if (!combinedList.isEmpty()) {
			combinedList.sort((dto1, dto2) -> Integer.compare(dto2.getLikeCount(), dto1.getLikeCount()));
		}
		
		if (grResults != null) {
			for (int i = 0; i < grResults.size(); i++) {
				int grId = ((Number) grResults.get(i)[0]).intValue();
				
				List<GroupMember> gms = groupRepo.findById(grId).get().getGroupMembers();
				
				gms.forEach(x -> {
					
					List<Object[]> gm = artistRepo.artistAndLikefindById(x.getArtist().getId());
					
					for (Object[] gmm : gm) {
						GroupAndArtistDto dto = new GroupAndArtistDto();
						dto.setType("artist");
						dto.setId(((Number) gmm[0]).intValue());
						dto.setName((String) gmm[1]);
						dto.setImage(((String) gmm[2]));
						dto.setLikeCount(((Number) (gmm[3] == null ? 0 : gmm[3])).intValue());
						combinedList.add(dto);
					}
				});
				
			}
		}

		return combinedList;

	}

}
