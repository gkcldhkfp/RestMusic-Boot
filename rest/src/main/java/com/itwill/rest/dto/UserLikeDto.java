package com.itwill.rest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserLikeDto {
	private Integer songId;
	private String title;
	private Integer albumId;
	private String albumName;
	private String albumImage;
	private List<Integer> artistId;
	private List<String> artistName;
	private List<Integer> groupId;
	private List<String> groupName;
	

}
