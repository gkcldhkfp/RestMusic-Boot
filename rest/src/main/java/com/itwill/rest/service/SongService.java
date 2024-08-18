package com.itwill.rest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Song;
import com.itwill.rest.repository.SongRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongService {
	private final SongRepository songRepo;
	
	@Transactional(readOnly = true)
	public Song selectBySongId(Integer songId) {
		log.info("songId = {}", songId);
		Song song = songRepo.findById(songId).orElseThrow();
		log.info("song = {}", song);
		return song;
	}
}
