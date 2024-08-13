package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
	
}
