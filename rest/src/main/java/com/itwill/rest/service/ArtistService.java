package com.itwill.rest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Artist;
import com.itwill.rest.repository.ArtistRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArtistService {
	
	private final ArtistRepository artistRepo;
	
	@Transactional(readOnly = true)
	public Artist findById(Integer id) {
		log.info("findById={}", id);
		
		Artist artist = artistRepo.findById(id).orElseThrow();
		
		return artist;
	}

}
