package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.RoleCode;

public interface RoleCodeRepository extends JpaRepository<RoleCode, Integer> {
	
}
