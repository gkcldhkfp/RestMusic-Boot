package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.TitleSong;
import com.itwill.rest.domain.TitleSongId;

public interface TitleSongRepository extends JpaRepository<TitleSong, TitleSongId> {
	
}
