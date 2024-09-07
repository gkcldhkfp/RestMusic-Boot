package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.itwill.rest.domain.AlbumFirstCon;

//public interface AlbumFirstConRepository extends JpaRepository<AlbumFirstCon, Long> {
public interface AlbumFirstConRepository extends JpaRepository<AlbumFirstCon, Integer> {
	
}
