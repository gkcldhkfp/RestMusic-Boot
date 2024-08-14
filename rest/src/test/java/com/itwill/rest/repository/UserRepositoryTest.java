package com.itwill.rest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.User;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepo;

//	@Test
	public void saveTest() {
		log.info("userRepo = {}", userRepo);
		userRepo.save(User.builder().userName("name").userId("id")
				.password("password")
				.email("email").nickname("nick").build());

	}
	
}
