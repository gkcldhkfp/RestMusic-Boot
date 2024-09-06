package com.itwill.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itwill.rest.domain.User;


public interface UserRepository extends JpaRepository<User, Integer>, UserQuerydsl {
	
	@EntityGraph(attributePaths = "roles")

	public Optional<User> findByUserId(String userId);

	public User findByEmail(String email);

	public User findByNickname(String nickname);

	@Query("select count(u) > 0 from User u where u.id = :id and u.password = :password")
    boolean checkPassword(Integer id, String password);

    @Query("select count(u) > 0 from User u where u.userId = :userId and u.isActive = 1")
    boolean checkUserIsActive(String userId);

    @Query("select count(u) > 0 from User u where u.userId = :userId and u.deactivatedUntil > current_date")
    boolean checkDeactivationPeriod(String userId);
    
}
