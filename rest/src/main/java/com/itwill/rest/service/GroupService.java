package com.itwill.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.Group;
import com.itwill.rest.dto.GroupAlbumDto;
import com.itwill.rest.dto.GroupInfoDto;
import com.itwill.rest.repository.AlbumRepository;
import com.itwill.rest.repository.ArtistRepository;
import com.itwill.rest.repository.GroupMemberRepository;
import com.itwill.rest.repository.GroupRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupService {
	
	private final GroupRepository groupRepo;
	private final GroupMemberRepository groupMemberRepo;
	private final ArtistRepository artistRepo;
	private final AlbumRepository albumRepo;
	
	@Transactional(readOnly = true)
    public GroupInfoDto getGroupInfoByGroupId(Integer groupId) {
		// groupId로 그룹을 조회
        Group group = groupRepo.findById(groupId).orElseThrow();
		
        // 그룹에 속한 아티스트 ID를 조회
        List<Integer> artistIds = groupMemberRepo.findByGroupId(groupId).stream()
                .map(groupMember -> groupMember.getArtist().getId())
                .collect(Collectors.toList());

        // 아티스트 정보를 조회
        List<Artist> artists = artistRepo.findAllById(artistIds);
        
        // 그룹 정보와 아티스트 정보를 포함하는 DTO로 변환
        GroupInfoDto dto = GroupInfoDto.builder()
                .groupId(group.getId())
                .groupName(group.getGroupName())
                .groupDescription(group.getGroupDescription())
                .groupImage(group.getGroupImage())
                .artists(artists)
                .build();
        
        // DTO 리턴
        return dto;
    }
	
	@Transactional(readOnly = true)
	public List<GroupAlbumDto> readAlbums(Integer groupId) {
		log.info("readAlbums(groupId={})", groupId);
		
	    // 1. 그룹의 역할 ID 10에 해당하는 앨범 ID를 조회
		List<Integer> albumIds = albumRepo.findAlbumIdsByGroupId(groupId);

	    // 2. 앨범 ID 리스트를 이용하여 앨범 정보를 조회
	    List<Album> albums = albumRepo.findAllById(albumIds);

	    // 3. 앨범에 포함된 아티스트와 그룹 정보를 추출하여 DTO로 변환
	    List<GroupAlbumDto> groupAlbumDtos = albums.stream()
	            .map(album -> {
	                // 앨범에 포함된 아티스트와 그룹을 추출
	                List<Artist> albumArtists = album.getSongs().stream()
	                        .flatMap(song -> song.getArtistRole().stream())
	                        .map(artistRole -> artistRole.getArtist())
	                        .distinct()
	                        .collect(Collectors.toList());

	                // 앨범에 포함된 그룹 정보를 추출
	                List<Group> albumGroups = album.getSongs().stream()
	                        .flatMap(song -> song.getArtistRole().stream())
	                        .map(artistRole -> artistRole.getGroup())
	                        .filter(groupObj -> groupObj != null)
	                        .distinct()
	                        .collect(Collectors.toList());

	                // 그룹과 연관된 아티스트의 ID를 수집
	                List<Integer> groupArtistIds = albumGroups.stream()
	                        .flatMap(grp -> groupMemberRepo.findByGroupId(grp.getId()).stream())
	                        .map(groupMember -> groupMember.getArtist().getId())
	                        .collect(Collectors.toList());

	                // 아티스트 리스트에서 그룹에 연관된 아티스트를 필터링하여 이름 숨기기
	                List<Artist> filteredArtists = albumArtists.stream()
	                        .map(artist -> {
	                            if (groupArtistIds.contains(artist.getId())) {
	                                // 그룹과 연관된 아티스트는 정보는 null로 설정
	                                return Artist.builder()
	                                        .id(artist.getId())
	                                        .artistName(null)
	                                        .artistImage(null)
	                                        .artistDescription(null)
	                                        .build();
	                            }
	                            return artist;
	                        })
	                        .collect(Collectors.toList());

	                return GroupAlbumDto.builder()
	                        .albumId(album.getAlbumId())
	                        .albumName(album.getAlbumName())
	                        .albumImage(album.getAlbumImage())
	                        .albumType(album.getAlbumType())
	                        .albumReleaseDate(album.getAlbumReleaseDate())
	                        .artists(filteredArtists) // 필터링된 아티스트 리스트 설정
	                        .groups(albumGroups) // 그룹 리스트 설정
	                        .build();
	            })
	            .collect(Collectors.toList());

	    return groupAlbumDtos;
	}

}
