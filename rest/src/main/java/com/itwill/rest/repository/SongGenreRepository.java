package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.GenreCode;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.SongGenre;
import com.itwill.rest.domain.SongGenreId;

public interface SongGenreRepository extends JpaRepository<SongGenre, SongGenreId> {
	public List<GenreCode> findBySong(Song song);
	
	// GenreCode를 통해 SongGenre 리스트를 페이징하여 조회하는 메서드
    Page<SongGenre> findByGenreCode(GenreCode genreCode, Pageable pageable);
    
}
