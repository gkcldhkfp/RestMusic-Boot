package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.SongGenre;
import com.itwill.rest.domain.SongGenreId;

public interface SongGenreRepository extends JpaRepository<SongGenre, SongGenreId> {
	
}
