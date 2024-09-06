package com.itwill.rest.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PurchaseController {
	
	@GetMapping("/purchase")
	public String purchase() {
		log.debug("purchase get");
		
		return "purchase/purchase";
	}

}
