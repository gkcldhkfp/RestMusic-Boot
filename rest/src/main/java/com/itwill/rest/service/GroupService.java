package com.itwill.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.GroupLike;
import com.itwill.rest.domain.GroupLikeId;
import com.itwill.rest.domain.GroupMember;
import com.itwill.rest.dto.GroupAlbumDto;
import com.itwill.rest.dto.GroupAndArtistDto;
import com.itwill.rest.dto.GroupInfoDto;
import com.itwill.rest.dto.GroupSongDto;
import com.itwill.rest.dto.SongSearchResultDto;
import com.itwill.rest.repository.AlbumRepository;
import com.itwill.rest.repository.ArtistRepository;
import com.itwill.rest.repository.GroupLikeRepository;
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
	private final GroupLikeRepository groupLikeRepo;
	
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
	public List<GroupSongDto> readSongs(Integer groupId) {
		log.info("readSongs(groupId={})", groupId);
		
	    // 1. groupId로 해당 GROUP이 참여한 앨범 ID를 조회
	    List<Integer> albumIds = albumRepo.findAlbumIdsByGroupId(groupId);

	    // 2. 앨범 ID 리스트를 이용하여 앨범 정보를 조회
	    List<Album> albums = albumRepo.findAllById(albumIds);

	    // 3. 앨범에 포함된 곡 정보를 추출하여 그룹이 참여한 곡만 필터링
	    return albums.stream()
	            .flatMap(album -> album.getSongs().stream())
	            .filter(song -> song.getArtistRole().stream()
	            		.filter(artistRole -> artistRole.getGroup() != null)
	                    .anyMatch(artistRole -> groupId.equals(artistRole.getGroup().getId()))) // groupId를 equal 시켜서 앨범에서 해당 group이 참여한 곡들만 get해옴.
	            .map(song -> {
	                // 곡에 포함된 아티스트와 그룹을 추출
	                List<Artist> songArtists = song.getArtistRole().stream()
	                		.filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10)
	                        .map(artistRole -> artistRole.getArtist())
	                        .distinct()
	                        .collect(Collectors.toList());

	                List<Group> songGroups = song.getArtistRole().stream()
	                		.filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10)
	                		.filter(artistRole -> artistRole.getGroup() != null)
	                        .map(artistRole -> artistRole.getGroup())
	                        .filter(groupObj -> groupObj != null)
	                        .distinct()
	                        .collect(Collectors.toList());

	                // 그룹에 속한 아티스트 ID 목록
	                List<Integer> groupArtistIds = songGroups.stream()
	                        .flatMap(group -> groupMemberRepo.findByGroupId(group.getId()).stream())
	                        .map(groupMember -> groupMember.getArtist().getId())
	                        .collect(Collectors.toList());

	                // 아티스트 ID와 이름 리스트 생성 (그룹에 속하지 않은 아티스트만)
	                List<Integer> artistIds = songArtists.stream()
	                        .filter(artist -> !groupArtistIds.contains(artist.getId()))
	                        .map(Artist::getId)
	                        .collect(Collectors.toList());

	                List<String> artistNames = songArtists.stream()
	                        .filter(artist -> !groupArtistIds.contains(artist.getId()))
	                        .map(Artist::getArtistName)
	                        .collect(Collectors.toList());

	                // 그룹 ID와 이름 리스트 생성
	                List<Integer> groupIds = songGroups.stream()
	                        .map(Group::getId)
	                        .collect(Collectors.toList());

	                List<String> groupNames = songGroups.stream()
	                        .map(Group::getGroupName)
	                        .collect(Collectors.toList());

	                return GroupSongDto.builder()
	                        .songId(song.getSongId())
	                        .albumId(song.getAlbum().getAlbumId())
	                        .albumName(song.getAlbum().getAlbumName())
	                        .albumImage(song.getAlbum().getAlbumImage())
	                        .title(song.getTitle())
	                        .artistIds(artistIds)
	                        .artistNames(artistNames)
	                        .groupIds(groupIds)
	                        .groupNames(groupNames)
	                        .build();
	            })
	            .collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<GroupAlbumDto> readAlbums(Integer groupId) {
		log.info("readAlbums(groupId={})", groupId);
		
	    // 1. groupId로 해당 GROUP이 참여한 앨범 ID를 조회
		List<Integer> albumIds = albumRepo.findAlbumIdsByGroupId(groupId);

	    // 2. 앨범 ID 리스트를 이용하여 앨범 정보를 조회
	    List<Album> albums = albumRepo.findAllById(albumIds);

	    // 3. 앨범에 포함된 아티스트와 그룹 정보를 추출하여 DTO로 변환
	    List<GroupAlbumDto> groupAlbumDtos = albums.stream()
	            .map(album -> {
	                // 앨범에 포함된 아티스트와 그룹을 추출
	                List<Artist> albumArtists = album.getSongs().stream()
	                        .flatMap(song -> song.getArtistRole().stream())
	                		.filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10)
	                        .map(artistRole -> artistRole.getArtist())
	                        .distinct()
	                        .collect(Collectors.toList());

	                List<Group> albumGroups = album.getSongs().stream()
	                        .flatMap(song -> song.getArtistRole().stream())
	                		.filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10)
	                        .filter(artistRole -> artistRole.getGroup() != null)
	                        .map(artistRole -> artistRole.getGroup())
	                        .filter(groupObj -> groupObj != null)
	                        .distinct()
	                        .collect(Collectors.toList());

	                // 그룹에 속한 아티스트 ID 목록
	                List<Integer> groupArtistIds = albumGroups.stream()
	                        .flatMap(group -> groupMemberRepo.findByGroupId(group.getId()).stream())
	                        .map(groupMember -> groupMember.getArtist().getId())
	                        .collect(Collectors.toList());

	                // 아티스트 ID와 이름 리스트 생성 (그룹에 속하지 않은 아티스트만)
	                List<Integer> artistIds = albumArtists.stream()
	                        .filter(artist -> !groupArtistIds.contains(artist.getId()))
	                        .map(Artist::getId)
	                        .collect(Collectors.toList());

	                List<String> artistNames = albumArtists.stream()
	                        .filter(artist -> !groupArtistIds.contains(artist.getId()))
	                        .map(Artist::getArtistName)
	                        .collect(Collectors.toList());

	                // 그룹 ID와 이름 리스트 생성
	                List<Integer> groupIds = albumGroups.stream()
	                        .map(Group::getId)
	                        .collect(Collectors.toList());

	                List<String> groupNames = albumGroups.stream()
	                        .map(Group::getGroupName)
	                        .collect(Collectors.toList());

	                return GroupAlbumDto.builder()
	                        .albumId(album.getAlbumId())
	                        .albumName(album.getAlbumName())
	                        .albumImage(album.getAlbumImage())
	                        .albumType(album.getAlbumType())
	                        .albumReleaseDate(album.getAlbumReleaseDate())
	                        .artistIds(artistIds)
	                        .artistNames(artistNames)
	                        .groupIds(groupIds)
	                        .groupNames(groupNames)
	                        .build();
	            })
	            .collect(Collectors.toList());

	    return groupAlbumDtos;
	}
	
	public boolean isLiked(GroupLikeId groupLikeId) {
		
		return groupLikeRepo.existsById(groupLikeId);
		
	}
	
	public boolean like(GroupLikeId groupLikeId) {
		log.info("like(likeId={})", groupLikeId);
		
		boolean exists = groupLikeRepo.existsById(groupLikeId);
		
		log.info("like={}",exists);
		
		if(exists) {
			groupLikeRepo.deleteById(groupLikeId);
			return false;
		} else {
			GroupLike like = GroupLike.builder().groupLikeId(groupLikeId).build(); 
			groupLikeRepo.save(like);
			return true;
			
		}
	}
	
	public List<GroupAndArtistDto> findGroupAndArtistDtoByGruoupId(int id) {
		
		List<GroupMember> result = groupMemberRepo.findByGroupId(id);
		
		List<GroupAndArtistDto> dtos = new ArrayList<>();
		
		
		for (GroupMember m : result) {
			GroupAndArtistDto dto = new GroupAndArtistDto();
			dto.setType("artists");
            dto.setId(m.getArtist().getId());
            dto.setImage(m.getArtist().getArtistImage());
            dto.setName(m.getArtist().getArtistName());
            dto.setLikeCount(m.getArtist().getArtistLikes().size());
            dtos.add(dto);
        }
		
		dtos.forEach(System.out :: println);
		
		return dtos;
	}

}
