package com.itwill.rest.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.rest.Util.ConverterUtil;
import com.itwill.rest.domain.SongConverter;

public interface SongConverterRepository extends JpaRepository<SongConverter, Integer> {
	
	// SongConverter의 제목 검색 쿼리
    @Query("SELECT sc FROM SongConverter sc WHERE LOWER(sc.songConverterTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<SongConverter> searchSongConvertersByTitle(@Param("keyword") String keyword);
    
    default List<SongConverter> searchSongConverters(String keyword) {
        // 기본 제목 검색
        List<SongConverter> converters = searchSongConvertersByTitle(keyword);

        // 변형된 검색어들 (영어를 한글로 변환, 한글을 영어로 변환)
        List<String> alternateKeywords = List.of(
            ConverterUtil.convertEnglishToKorean(keyword), // 영어 검색어로 한글 제목 찾기
            ConverterUtil.convertKoreanToEnglish(keyword)  // 한글 검색어로 영어 제목 찾기
        );

        // 변형된 검색어들로 검색 수행
        for (String altKeyword : alternateKeywords) {
            converters.addAll(searchSongConvertersByTitle(altKeyword));
        }

        return converters.stream().distinct().collect(Collectors.toList());
    }
}
