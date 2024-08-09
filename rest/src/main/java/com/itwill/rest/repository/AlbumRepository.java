package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.Album;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
	
}
