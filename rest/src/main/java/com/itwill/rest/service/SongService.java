package com.itwill.rest.service;

import org.springframework.stereotype.Service;

import com.itwill.rest.repository.SongRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {
	
	private final SongRepository songRepo;
	
}
