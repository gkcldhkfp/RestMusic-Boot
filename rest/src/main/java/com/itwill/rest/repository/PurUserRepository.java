package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.PurUser;
import com.itwill.rest.domain.User;

public interface PurUserRepository extends JpaRepository<PurUser, User> {
	
}
