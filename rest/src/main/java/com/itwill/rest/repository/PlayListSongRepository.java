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
import com.itwill.rest.domain.Song;

public interface PlayListSongRepository extends JpaRepository<PlayListSong, Integer> {
	
	public PlayListSong findBySongAndPlayList(Song song, PlayList playList);
	
	// 플레이리스트 ID로 곡 리스트를 조회하는 메서드
	// (현재 생성한 PlayListSong 객체에는 DB와 맞지 않은 필드명이 존재, 따라서 PlayListSong 객체 전체를 반환 시도할 경우, 오류가 생기므로 필요한 데이터만
	// 반환할 수 있도록 List<Object[]> 배열로 반환 및 서비스에서 사용)
    @Query("SELECT p.playList.pListId, p.song.songId, p.createdTime FROM PlayListSong p WHERE p.playList.pListId = :pListId order by p.playlistSongId desc")
    List<Object[]> findByPlayListId(@Param("pListId") Integer pListId);
	
    @Modifying
    @Transactional
    @Query("DELETE FROM PlayListSong p WHERE p.playList.pListId = :pListId AND p.song.songId = :songId AND p.createdTime = :createdTime")
	void deleteSongByCreatedTime(@Param("pListId") Integer pListId, @Param("songId") Integer songId, @Param("createdTime") LocalDateTime createdTime);
    
}
