package com.itwill.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.PlayList;
import com.itwill.rest.domain.User;
import com.itwill.rest.dto.playlist.PlayListCreateDto;
import com.itwill.rest.dto.playlist.PlayListFirstAlbumImgDto;
import com.itwill.rest.repository.PlayListRepository;
import com.itwill.rest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayListService {
	
	private final UserRepository userRepo;
	private final PlayListRepository playListRepo;
	
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

}
