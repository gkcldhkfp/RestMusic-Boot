package com.itwill.rest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.PlayList;
import com.itwill.rest.domain.PlayListSong;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.User;
import com.itwill.rest.dto.playlist.PlayListCreateDto;
import com.itwill.rest.dto.playlist.PlayListFirstAlbumImgDto;
import com.itwill.rest.repository.PlayListRepository;
import com.itwill.rest.repository.PlayListSongRepository;
import com.itwill.rest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayListService {
	
	private final UserRepository userRepo;
	private final PlayListRepository playListRepo;
	private final PlayListSongRepository playListSongRepo;
	
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
	public List<PlayListSong> getSongByPlayListId(Integer id) {
		// 플리 아이디로 음원 찾기
		List<PlayListSong> playListSongs = playListSongRepo.findByPlayListSongId_pListId(id);
//		List<Song> songs = playListSongs.stream().map(ps -> ps.getSong()).collect(Collectors.toList());
		
		return playListSongs;
	}
	
	@Transactional
	public void deleteListSongBySongId(Integer pListId, Integer songId, LocalDateTime createdTime) {
		log.info("deleteByListId(pListId={}, songId={}, createdTime={})", pListId, songId, createdTime);

		playListSongRepo.deleteSongByCreatedTime(pListId, songId, createdTime);
	}
}
