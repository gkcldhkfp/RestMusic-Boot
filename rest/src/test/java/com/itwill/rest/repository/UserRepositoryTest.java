package com.itwill.rest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.service.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService userServ;
	
//	@Test
	@Transactional
	public void findTest() {
		log.info("findTest");
		// boolean result = userServ.checkNickname("nick1");
		boolean result = userServ.checkUserId("user12");
		System.out.println(result);
	}
	
}
