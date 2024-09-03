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
    
}
