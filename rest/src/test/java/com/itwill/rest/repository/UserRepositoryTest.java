package com.itwill.rest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepo;

	@Test
	@Transactional
	public void diTest() {
		log.info("userRepo = {}", userRepo);

	}
	
}
