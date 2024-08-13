package com.itwill.rest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.User;
import com.itwill.rest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepo;
	
	@Transactional(readOnly = true)
	public User readById(Integer id) {
		log.info("readById={}", id);
		
		User user = userRepo.findById(id).orElseThrow();
		
		return user;
	}

}
