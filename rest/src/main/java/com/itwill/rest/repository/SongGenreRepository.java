package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.GenreCode;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.SongGenre;
import com.itwill.rest.domain.SongGenreId;

public interface SongGenreRepository extends JpaRepository<SongGenre, SongGenreId> {
public List<GenreCode> findBySong(Song song);
}
