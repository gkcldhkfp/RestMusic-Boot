package com.itwill.rest.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.rest.dto.UserLikeDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserQuerydslTest {
	
	@Autowired
	private UserQuerydslImpl userQuerydsl;
	
//	@Test
	public void testSelectLikesByUserid() {
		List<UserLikeDto> userLike = userQuerydsl.selectLikesByUserid(1);
		
		assertNotNull(userLike);
		
		for (UserLikeDto dto : userLike) {
			System.out.println("title: " + dto.getTitle()
								+ ", albumName: " + dto.getAlbumName()
								+ ", artistName: " + dto.getArtistName());
		}
	}
}
