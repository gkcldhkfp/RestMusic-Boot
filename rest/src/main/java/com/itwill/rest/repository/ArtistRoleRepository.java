package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.ArtistRole;
import com.itwill.rest.domain.ArtistRoleId;
import com.itwill.rest.domain.Song;

@Repository
public interface ArtistRoleRepository extends JpaRepository<ArtistRole, ArtistRoleId> {
	// 음원에 참여한 아티스트 Role 객체를 리턴하는 메서드
	List<ArtistRole> findBySongAndRoleCode_RoleId(Song song, Integer roleCodeId);

	// 앨범에 참여한 아티스트 Role 객체를 리턴하는 메서드
	List<ArtistRole> findBySong_AlbumAndRoleCode_RoleId(Album album, Integer roleCodeId);

	// 음원에 참여한 모든 아티스트 객체를 리턴하는 메서드
	List<ArtistRole> findBySong(Song song);
}
