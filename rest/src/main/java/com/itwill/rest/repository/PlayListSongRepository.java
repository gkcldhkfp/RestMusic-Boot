package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.PlayList;
import com.itwill.rest.domain.PlayListSong;
import com.itwill.rest.domain.Song;

public interface PlayListSongRepository extends JpaRepository<PlayListSong, Integer> {

	public PlayListSong findBySongAndPlayList(Song song, PlayList playList);
}
