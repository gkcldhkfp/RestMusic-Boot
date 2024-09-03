package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.rest.domain.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
	@Query(value = "SELECT * FROM ALBUMS WHERE GET_INITIAL_SOUND(album_name) LIKE CONCAT('%', GET_INITIAL_SOUND(:keyword), '%')", nativeQuery = true)
	List<Album> findByAlbumNameInitialSound(@Param("keyword") String keyword);
}
