package com.itwill.rest.repository;

import java.util.List;

import com.itwill.rest.dto.PlayListFirstAlbumImgDto;

public interface PlayListQuerydsl {

	List<PlayListFirstAlbumImgDto> selectByUserId(Integer id);
	
}
