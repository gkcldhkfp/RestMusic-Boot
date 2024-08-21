package com.itwill.rest.repository;

import java.util.List;

import com.itwill.rest.dto.UserLikeDto;

public interface UserQuerydsl {
	
	List<UserLikeDto> selectLikesByUserid(Integer id);

}
