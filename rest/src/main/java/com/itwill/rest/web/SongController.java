package com.itwill.rest.web;

import org.springframework.stereotype.Controller;

import com.itwill.rest.service.SongService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SongController {

	private final SongService songSvc;
	
}
