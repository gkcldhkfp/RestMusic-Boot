package com.itwill.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Group;
import com.itwill.rest.dto.ArtistAlbumDto;
import com.itwill.rest.repository.GroupRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupService {
	
	private final GroupRepository groupRepo;
	
	@Transactional(readOnly = true)
	public Group findById(Integer id) {
		log.info("findById={}", id);
		
		Group group = groupRepo.findById(id).orElseThrow();
		
		return group;
	}
	
	@Transactional(readOnly = true)
	public List<ArtistAlbumDto> readAlbums(Integer groupId) {
		log.info("readAlbums={}", groupId);
		
		return null;
	}

}
