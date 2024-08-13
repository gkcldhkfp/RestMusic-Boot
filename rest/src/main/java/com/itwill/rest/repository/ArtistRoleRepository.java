package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.ArtistRole;
import com.itwill.rest.domain.ArtistRoleId;

public interface ArtistRoleRepository extends JpaRepository<ArtistRole, ArtistRoleId> {
	
}
