package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.PlayListSong;
import com.itwill.rest.domain.PlayListSongId;

public interface PlayListSongRepository extends JpaRepository<PlayListSong, PlayListSongId> {

}
