package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import com.itwill.rest.dto.SongChartDto;

public interface SongQuerydsl {
	// top 30
	List<SongChartDto> getTopSongs();
	
	// 장르별 차트(전체)
	List<SongChartDto> getAllSongs();
	
	// 장르별 차트
	List<SongChartDto> getSongsByGenre(String genreName);
	
	// 최신 음악
	List<SongChartDto> getNewestSongs();
	
	// 최신 음악(페이징)
//	Page<SongChartDto> getNewestSongs(int page, int size);
}
