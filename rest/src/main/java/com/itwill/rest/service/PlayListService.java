package com.itwill.rest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.PlayList;
import com.itwill.rest.domain.PlayListSong;
import com.itwill.rest.domain.PlayListSongId;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.User;
import com.itwill.rest.dto.PlayListCreateDto;
import com.itwill.rest.dto.PlayListFirstAlbumImgDto;
import com.itwill.rest.dto.PlayListNameUpdateDto;
import com.itwill.rest.dto.PlayListSongInfoDto;
import com.itwill.rest.repository.GroupMemberRepository;
import com.itwill.rest.repository.PlayListRepository;
import com.itwill.rest.repository.PlayListSongRepository;
import com.itwill.rest.repository.SongRepository;
import com.itwill.rest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayListService {
	
	private final SongRepository songRepo;
	private final UserRepository userRepo;
	private final PlayListRepository playListRepo;
	private final PlayListSongRepository playListSongRepo;
	private final GroupMemberRepository groupMemberRepo;
	
	@Transactional(readOnly = true)
	public List<PlayListFirstAlbumImgDto> getPlayListByUserId(Integer id) {
		log.info("getPlayListByUserId={}", id);
		
		List<PlayListFirstAlbumImgDto> result = playListRepo.selectByUserId(id);
		
		return result;
	}
	
	@Transactional
	public PlayList create(PlayListCreateDto dto) {
		log.info("create(dto={})", dto);
		
		User user = userRepo.findById(dto.getId()).orElseThrow();
		
		PlayList entity = PlayList.builder()
				.user(user)
				.pListName(dto.getPListName())
				.build();
		
		playListRepo.save(entity);
		
		return entity;
	}
	
	@Transactional
	public void deleteByListId(Integer pListId) {
		log.info("deleteById(pListId={})", pListId);
		
		playListRepo.deleteById(pListId);
	}
	
	@Transactional(readOnly = true)
	public PlayList getPlayListInfoByListId(Integer pListId) {
		log.info("getPlayListInfoByListId={}", pListId);
		
		PlayList playlist = playListRepo.findById(pListId).orElseThrow();
		
		return playlist;
	}
	
	@Transactional(readOnly = true)
	public List<PlayListSongInfoDto> getSongByPlayListId(Integer id) {
	    log.info("getSongByPlayListId(pListId={})", id);

	    // 1. 플레이리스트에서 곡 정보를 조회
	    List<Object[]> playListSongsData = playListSongRepo.findByPlayListId(id);

	    // 2. 곡 정보와 관련된 아티스트, 그룹, 앨범 정보를 추출하여 DTO로 변환
	    return playListSongsData.stream().map(data -> {
	        Integer playListId = (Integer) data[0];
	        Integer songId = (Integer) data[1];
	        LocalDateTime createdTime = (LocalDateTime) data[2];

	        // 곡 정보 조회
	        Song song = songRepo.findById(songId).orElseThrow();
	        Album album = song.getAlbum();

	        // 곡, 앨범 정보 추출
	        String title = song.getTitle();
	        Integer albumId = album.getAlbumId();
	        String albumImage = album.getAlbumImage();
	        String albumName = album.getAlbumName();

	        // 곡에 관련된 아티스트와 그룹 정보 추출
	        List<Artist> songArtists = song.getArtistRole().stream()
	                .filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10) // 아티스트 필터링
	                .map(artistRole -> artistRole.getArtist())
	                .distinct()
	                .collect(Collectors.toList());

	        List<Group> songGroups = song.getArtistRole().stream()
	                .filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10) // 그룹 필터링
	                .filter(artistRole -> artistRole.getGroup() != null)
	                .map(artistRole -> artistRole.getGroup())
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

	        return PlayListSongInfoDto.builder()
	                .pListId(playListId)
	                .songId(songId)
	                .createdTime(createdTime)
	                .title(title)
	                .albumId(albumId)
	                .albumImage(albumImage)
	                .albumName(albumName)
	                .artistId(artistIds)
	                .artistName(artistNames)
	                .groupId(groupIds)
	                .groupName(groupNames)
	                .build();
	    }).collect(Collectors.toList());
	}
	
	@Transactional
	public void deleteListSongBySongId(Integer pListId, Integer songId, LocalDateTime createdTime) {
		log.info("deleteByListId(pListId={}, songId={}, createdTime={})", pListId, songId, createdTime);

		playListSongRepo.deleteSongByCreatedTime(pListId, songId, createdTime);
	}
	
	public Boolean checkSongInPlayList(PlayListSongId id) {
		log.info("checkSongInPlayList(id={})",id);
		
		Song song = songRepo.findById(id.getSongId()).get();
		PlayList playList = playListRepo.findById(id.getPListId()).get();
		
		PlayListSong result = playListSongRepo.findBySongAndPlayList(song, playList);
		
		if(result == null) {
			return true;
		} else {
			return false;
		}
		
	}

	@Transactional
	public int songAddToPlayList(PlayListSongId id) {
		log.info("songAddToPlayList(id={})",id);
		
		PlayList playList = playListRepo.findById(id.getPListId()).get();
		Song song = songRepo.findById(id.getSongId()).get(); 
		
		playListSongRepo.save(PlayListSong.builder()
				.playList(playList)
				.song(song)
				.build());
		
		return 1;
	}
	
	@Transactional
	public void updatePlayListNameByListId(PlayListNameUpdateDto dto) {
		log.info("updateByListId(dto={})", dto);
		
		PlayList result = playListRepo.findById(dto.getPListId()).orElseThrow();
		
		result.update(dto.getPListName());
	}

}
