package com.itwill.rest.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.rest.Util.ConverterUtil;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.SongConverter;

public interface SongRepository extends JpaRepository<Song, Integer> {
	// 앨범 발매일 기준 내림차순 정렬(페이징)
	Page<Song> findByOrderByAlbum_AlbumReleaseDateDesc(Pageable pageable);
	
	// 좋아요 수 기준 내림차순 정렬
    @Query("SELECT s FROM Song s LEFT JOIN s.likes l GROUP BY s.songId ORDER BY COUNT(l) DESC")
    List<Song> findByOrderByLikesCountDesc();
	
	// 좋아요 수 기준 내림차순 정렬(페이징)
    @Query("SELECT s FROM Song s LEFT JOIN s.likes l GROUP BY s.songId ORDER BY COUNT(l) DESC")
    Page<Song> findByOrderByLikesCountDesc(Pageable pageable);
    
    // 좋아요 수 기준으로 특정 장르의 노래들을 내림차순으로 정렬(페이징)
    @Query("SELECT sg.song FROM SongGenre sg LEFT JOIN sg.song.likes l WHERE sg.genreCode.genreName = :genreName GROUP BY sg.song.songId ORDER BY COUNT(l) DESC")
    Page<Song> findByGenreNameOrderByLikesCountDesc(@Param("genreName") String genreName, Pageable pageable);
	
    //	List<Song> findAllByOrderByAlbum_AlbumReleaseDateDesc();
	
	// 기본 제목 검색 쿼리
    @Query("SELECT s FROM Song s WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Song> searchSongsByTitle(@Param("keyword") String keyword);
    
//    // 발음 변형을 고려하여 검색하는 메서드 추가
//    @Query("SELECT s FROM Song s WHERE s.title LIKE %:title%")
//    List<Song> searchSongsByTitle(@Param("title") String title);
    
    default List<Song> searchSongs(String keyword) {
        // 기본 제목 검색
        List<Song> songs = searchSongsByTitle(keyword);

        // 변형된 검색어들 (영어를 한글로 변환, 한글을 영어로 변환)
        List<String> alternateKeywords = List.of(
            ConverterUtil.convertEnglishToKorean(keyword), // 영어 검색어로 한글 제목 찾기
            ConverterUtil.convertKoreanToEnglish(keyword), // 한글 검색어로 영어 제목 찾기
            ConverterUtil.getPhoneticVariation(keyword)     // 발음 변형 검색어로 제목 찾기
        );

        // 변형된 검색어들로 검색 수행
        for (String altKeyword : alternateKeywords) {
            if (!altKeyword.equalsIgnoreCase(keyword)) {
                songs.addAll(searchSongsByTitle(altKeyword));
            }
        }

        return songs.stream().distinct().collect(Collectors.toList());
    }
    
}
