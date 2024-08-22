package com.itwill.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.User;


public interface UserRepository extends JpaRepository<User, Integer>, UserQuerydsl {
	
	@EntityGraph(attributePaths = "roles")

	public Optional<User> findByUserId(String userId);

	public User findByEmail(String email);

	public User findByNickname(String nickname);
	
}