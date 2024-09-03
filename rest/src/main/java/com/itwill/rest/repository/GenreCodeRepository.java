package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.GenreCode;

public interface GenreCodeRepository extends JpaRepository<GenreCode, Integer> {
	
	public GenreCode findByGenreName(String genreName);
	
}