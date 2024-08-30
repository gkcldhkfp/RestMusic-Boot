package com.itwill.rest.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.PlayList;
import com.itwill.rest.domain.PlayListSong;
import com.itwill.rest.domain.PlayListSongId;
import com.itwill.rest.domain.Song;

public interface PlayListSongRepository extends JpaRepository<PlayListSong, Integer> {
	
	public PlayListSong findBySongAndPlayList(Song song, PlayList playList);
	
	List<PlayListSong> findByPlayListSongId_pListId(Integer id);
	
    @Modifying
    @Transactional
    @Query("DELETE FROM PlayListSong p WHERE p.playListSongId.pListId = :pListId AND p.playListSongId.songId = :songId AND p.createdTime = :createdTime")
	void deleteSongByCreatedTime(@Param("pListId") Integer pListId, @Param("songId") Integer songId, @Param("createdTime") LocalDateTime createdTime);

}