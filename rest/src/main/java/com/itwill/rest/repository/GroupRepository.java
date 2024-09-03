package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
	
}
