package com.itwill.rest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.PlayList;
import com.itwill.rest.domain.PlayListSong;
import com.itwill.rest.domain.PlayListSongId;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.User;
import com.itwill.rest.dto.playlist.PlayListCreateDto;
import com.itwill.rest.dto.playlist.PlayListFirstAlbumImgDto;
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
	private final PlayListSongRepository plsRepo;
	
	@Transactional(readOnly = true) // 작동 x
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

	public Boolean checkSongInPlayList(PlayListSongId id) {
		log.info("checkSongInPlayList(id={})",id);
		
		Song song = songRepo.findById(id.getSongId()).get();
		PlayList playList = playListRepo.findById(id.getPListId()).get();
		
		PlayListSong result = plsRepo.findBySongAndPlayList(song, playList);
		
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
		
		plsRepo.save(PlayListSong.builder()
				.playList(playList)
				.song(song)
				.build());
		
		return 1;
	}

}
