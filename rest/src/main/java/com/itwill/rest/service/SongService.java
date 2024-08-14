package com.itwill.rest.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.itwill.rest.dto.SongDetailsDto;
import com.itwill.rest.repository.SongRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {
	
	private final SongRepository songRepo;
	
	public SongDetailsDto readDetails(int songId) {
		log.info("readDetails(id={})",songId);
		
		SongDetailsDto dto = songRepo.searchDetailsById(songId);
		
		log.info("lyrics={}",dto.getLyrics());
		
		try {
            // 파일을 ClassPath에서 읽어옵니다.
            ClassPathResource resource = new ClassPathResource("static/lyrics/" + dto.getLyrics());
            // Files.readString() 메서드를 사용하여 문자열로 읽어옵니다.
            String lyrics = Files.readString(Paths.get(resource.getURI()), StandardCharsets.UTF_8);
            // 읽어온 문자열을 DTO에 설정합니다.
            dto.setLyrics(lyrics);
        } catch (IOException e) {
            e.printStackTrace();
        }
	    
		
		return dto; 
	}
	
}
