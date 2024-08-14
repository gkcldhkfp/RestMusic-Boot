package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
}
