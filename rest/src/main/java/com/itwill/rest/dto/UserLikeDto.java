package com.itwill.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserLikeDto {
	private Integer songId;
	private Integer albumId;
	private String title;
	private String albumName;
	private String albumImage;
	private String artistId;
	private String artistName;

}
