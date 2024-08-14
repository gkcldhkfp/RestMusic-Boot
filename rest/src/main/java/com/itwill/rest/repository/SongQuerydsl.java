package com.itwill.rest.repository;

import com.itwill.rest.dto.SongDetailsDto;

public interface SongQuerydsl {

	SongDetailsDto searchDetailsById(int id);
	
}
