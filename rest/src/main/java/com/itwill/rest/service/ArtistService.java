package com.itwill.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Artist;
import com.itwill.rest.dto.ArtistSearchResultDto;
import com.itwill.rest.dto.GroupAndArtistDto;
import com.itwill.rest.dto.GroupSearchResultDto;
import com.itwill.rest.repository.ArtistRepository;
import com.itwill.rest.repository.GroupRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArtistService {
	
	private final ArtistRepository artistRepo;
	private final GroupRepository groupRepo;
	
	@Transactional(readOnly = true)
	public Artist findById(Integer id) {
		log.info("findById={}", id);
		
		Artist artist = artistRepo.findById(id).orElseThrow();
		
		return artist;
	}

	public List<GroupAndArtistDto> searchAllArtist(String keyword){
			
		List<GroupAndArtistDto> dtos = new ArrayList<>();
		
		List<Object[]> results = artistRepo.searchAllArtist(keyword);
    	
    	for (Object[] result : results) {
    		GroupAndArtistDto dto = new GroupAndArtistDto();
    		dto.setType("artist");
            dto.setId(((Number) result[0]).intValue());
            dto.setName((String) result[1]);
            dto.setImage(((String) result[2]));
            dto.setLikeCount(((Number) result[3]).intValue());
            dtos.add(dto);
        }
			
		return dtos;
	
	}
	
	public List<GroupAndArtistDto> searchAllGroups(String keyword){
		
		List<GroupAndArtistDto> dtos = new ArrayList<>();
		
		List<Object[]> results = groupRepo.searchAllGroup(keyword);
    	
    	for (Object[] result : results) {
    		GroupAndArtistDto dto = new GroupAndArtistDto();
    		dto.setType("group");
            dto.setId(((Number) result[0]).intValue());
            dto.setName((String) result[1]);
            dto.setImage(((String) result[2]));
            dto.setLikeCount(((Number) result[3]).intValue());
            dtos.add(dto);
        }
			
		return dtos;
	
	}
	
	
	public List<GroupAndArtistDto> searchArtists(String keyword, String sortType, int offset){
		
		List<GroupAndArtistDto> arDtos = new ArrayList<>();
		List<GroupAndArtistDto> grDtos = new ArrayList<>();
		
		List<Object[]> grResults = new ArrayList<>();
		List<Object[]> arResults = new ArrayList<>();
		
		if(sortType.equals("accuracy")) {
			grResults = groupRepo.searchGroupAccuracy(keyword, offset);
			arResults = artistRepo.searchArtistAccuracy(keyword, offset);		
		} else if (sortType.equals("alphabet")) {
			grResults = groupRepo.searchGroupAlphabet(keyword, offset);
			arResults = artistRepo.searchArtistAlphabet(keyword, offset);
		} else {
			return null;
		}
		
		// 리스트가 null인지 확인하고, null일 경우 빈 리스트로 초기화
		arResults = (arResults != null) ? arResults : new ArrayList<>();
		grResults = (grResults != null) ? grResults : new ArrayList<>();

		for (Object[] result : grResults) {
			GroupAndArtistDto dto = new GroupAndArtistDto();
			dto.setType("group");
			dto.setId(((Number) result[0]).intValue());
			dto.setName((String) result[1]);
			dto.setImage(((String) result[2]));
			dto.setLikeCount(((Number) result[3]).intValue());
			grDtos.add(dto);
		}
		
		for (Object[] result : arResults) {
			GroupAndArtistDto dto = new GroupAndArtistDto();
			dto.setType("artist");
			dto.setId(((Number) result[0]).intValue());
			dto.setName((String) result[1]);
			dto.setImage(((String) result[2]));
			dto.setLikeCount(((Number) result[3]).intValue());
			arDtos.add(dto);
		}
		
		// 두 리스트를 합치기
		List<GroupAndArtistDto> combinedList = new ArrayList<>();
		combinedList.addAll(arDtos);
		combinedList.addAll(grDtos);

		// combinedList가 비어 있는지 확인하고, 비어 있지 않으면 정렬
		if (!combinedList.isEmpty()) {
		    combinedList.sort((dto1, dto2) -> Integer.compare(dto2.getLikeCount(), dto1.getLikeCount()));
		}
    	
			
		return combinedList;
	
	}
	
	
	
	
	
	
}
