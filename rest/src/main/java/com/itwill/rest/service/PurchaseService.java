package com.itwill.rest.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PurchaseService {
	
	// 아직 완성 x
	public int PurchaseSuccess (Integer id) {
		log.info("PurchaseSuccess=(id={})", id);
		
		return 0;
	}

	// 아직 완성 x
	public boolean isPurchaseUser (Integer id) {
		log.info("isPurchaseUser()");
		int result = 0;
		if (result >= 1) {
			return true; // 결제한 유저
		} else {
			return false; // 결제유저 아님.
		}
	}

}
