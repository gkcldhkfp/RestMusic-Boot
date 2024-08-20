package com.itwill.rest.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.itwill.rest.domain.Like;
import com.itwill.rest.domain.LikeId;
import com.itwill.rest.domain.Song;
import com.itwill.rest.dto.SongDetailsDto;
import com.itwill.rest.repository.LikeRepository;
import com.itwill.rest.repository.SongRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {
	
	private final SongRepository songRepo;
	private final LikeRepository likeRepo;
	
	public SongDetailsDto readDetails(int songId) {
		log.info("readDetails(id={})",songId);
		
		SongDetailsDto dto = songRepo.searchDetailsById(songId);
		
		log.info("lyrics={}",dto);
		
		try {
            // 파일을 ClassPath에서 읽어옵니다.
            ClassPathResource resource = new ClassPathResource("static/lyrics/" + dto.getLyrics());
            // Files.readString() 메서드를 사용하여 문자열로 읽어옵니다.
            String lyrics = Files.readString(Paths.get(resource.getURI()), StandardCharsets.UTF_8);
            // 읽어온 문자열을 DTO에 설정합니다.
            if(!lyrics.equals("")) {
            dto.setLyrics(lyrics);
            } else {
            	dto.setLyrics("가사를 찾을 수 없습니다");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return dto; 
	}
	
	public boolean like(LikeId likeId) {
		
//		Optional<Like> like = likeRepo.findById(LikeId.builder().id(dto.getUserId()).songId(dto.getSongId()).build());
//		log.info("like={}",like);
		
		
		boolean exists = likeRepo.existsById(likeId);
		
		log.info("like={}",exists);
		
		if(exists) {
			likeRepo.deleteById(likeId);
			return false;
		} else {
			Like like = Like.builder().likeId(likeId).build(); 
			likeRepo.save(like);
			return true;
			
		}
	}
	
	public boolean isLiked(LikeId likeId) {
		
//		Optional<Like> result = likeRepo.findById(likeId);
//		
//		if(result.isEmpty()) {
//			return false;
//		} else {
//			return true;
//		}
		
		return likeRepo.existsById(likeId);
		
	}

	public Song selectBySongId(Integer songId) {
		log.info("songId = {}", songId);
		Song song = songRepo.findById(songId).get();
		return song;
	}
	
}
