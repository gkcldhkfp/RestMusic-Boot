package com.itwill.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.rest.domain.PurUser;
import com.itwill.rest.domain.PurUserId;
import com.itwill.rest.domain.User;
import com.itwill.rest.repository.PurUserRepository;
import com.itwill.rest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PurchaseService {

	private final PurUserRepository purUserRepo;

	private final UserRepository userRepo;
	
	// 아직 완성 x
	public int PurchaseSuccess (Integer id) {
		log.info("PurchaseSuccess=(id={})", id);
		
		return 0;
	}

	public boolean isPurchaseUser (Integer id) {
		log.info("isPurchaseUser()");
		boolean result = false;

		User user = userRepo.findById(id).orElseGet(null);
		if (user == null) {
			return result;
		}
		PurUserId purUserId = new PurUserId();
		purUserId.setId(id);

		// TODO: 결제 유저 테이블에 PK추가 후 id로 결제 유저인 지 확인하는 로직 구현 필요.
		List<PurUser> purUsers = purUserRepo.findByPurUserId_id(id);
		// List<PurUser> purUsers = null;
		if (purUsers.size() >= 1) {
			result = true;
		}
		
		return result; // 결제한 유저

	}

}
