package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.rest.domain.Album;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
    // groupId로 모든 albumId를 찾는 메서드
    @Query("SELECT DISTINCT ar.song.album.albumId FROM ArtistRole ar WHERE ar.group.id = :groupId")
    List<Integer> findAlbumIdsByGroupId(@Param("groupId") Integer groupId);
	
}
