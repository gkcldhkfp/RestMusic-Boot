package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.Song;

public interface SongRepository extends JpaRepository<Song, Integer>, SongQuerydsl {
	
}
