package com.itwill.rest.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Like;
import com.itwill.rest.domain.LikeId;
import com.itwill.rest.dto.SongChartDto;
import com.itwill.rest.dto.SongLikeDto;
import com.itwill.rest.repository.SongLikeRepository;
import com.itwill.rest.repository.SongRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SongService {
	private final SongRepository songRepo;
	private final SongLikeRepository songLikeRepo;
	
	@Transactional(readOnly = true)
	public Song selectBySongId(Integer songId) {
		log.info("songId = {}", songId);
		Song song = songRepo.findById(songId).orElseThrow();
		log.info("song = {}", song);
		return song;
	}
	
	
	// top30
	@Transactional(readOnly = true)
    public List<SongChartDto> readTopSongs() {
		log.info("readTopSongs()");
        return songRepo.getTopSongs();
    }
	
	// 장르별 차트(전체)
	@Transactional(readOnly = true)
	public List<SongChartDto> readAllSongs() {
	    log.info("readAllSongs()");
	    return songRepo.getAllSongs();
	}
	
	// 장르별 차트
	@Transactional(readOnly = true)
	public List<SongChartDto> readSongsByGenre(String genreName) {
	    log.info("readSongsByGenre({})", genreName);
	    return songRepo.getSongsByGenre(genreName);
	}
	
	// 최신 음악
	@Transactional(readOnly = true)
    public List<SongChartDto> readNewestSongs() {
        log.info("readNewestSongs()");
        return songRepo.getNewestSongs();
    }
	
	// 최신 음악(페이징)
//	@Transactional(readOnly = true)
//	public Page<SongChartDto> readNewestSongs(PageRequest pageRequest) {
//	    log.info("readNewestSongs(page={}, size={})", pageRequest.getPageNumber(), pageRequest.getPageSize());
//	    return songRepo.getNewestSongs(pageRequest.getPageNumber(), pageRequest.getPageSize());
//	}
	
	// 특정 사용자가 특정 노래를 좋아요 했는지 여부 확인
	public boolean isUserLikedSong(SongLikeDto dto) {
		log.info("isUserLikedSong(dto = {})", dto);
        LikeId likeId = new LikeId(dto.getSongId(), dto.getLoginUserId());
        return songLikeRepo.existsByLikeId(likeId);
    }

	// 특정 곡의 좋아요 개수 확인
    public int countSongLikes(Integer songId) {
    	log.info("countSongLikes(songId = {})", songId);
        return (int) songLikeRepo.countByLikeIdSongId(songId);
    }

    // 좋아요 추가
    public void addSongLike(SongLikeDto dto) {	
        log.info("addSongLike(dto = {})", dto);
        LikeId likeId = new LikeId(dto.getSongId(), dto.getLoginUserId());
        Like like = Like.builder().likeId(likeId).build();
        songLikeRepo.save(like);
    }
    
    // 좋아요 취소
    public void cancelSongLike(SongLikeDto dto) {
        log.info("cancelSongLike(dto = {})", dto);
        LikeId likeId = new LikeId(dto.getSongId(), dto.getLoginUserId());
        songLikeRepo.deleteById(likeId);
    }
    
}
