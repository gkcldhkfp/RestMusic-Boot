package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.itwill.rest.domain.Song;

public interface SongRepository extends JpaRepository<Song, Integer>, SongQuerydsl {
	
	List<Song> findAllByOrderByAlbum_AlbumReleaseDateDesc();
	
}
