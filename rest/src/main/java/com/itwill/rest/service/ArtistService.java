package com.itwill.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Artist;
import com.itwill.rest.dto.ArtistAlbumDto;
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
	
	@Transactional(readOnly = true)
	public List<ArtistAlbumDto> readAlbums(Integer artistId) {
		log.info("readAlbums={}", artistId);
		
		List<ArtistAlbumDto> list = artistRepo.selectAlbumsByArtistId(artistId);
		
		return list;
	}

}
