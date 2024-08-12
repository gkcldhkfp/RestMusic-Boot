package com.itwill.rest.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Builder
public class UserLikeDto {
	private Integer id;
	private Integer songId;
	private Integer albumId;
	private String userId;
	private String songPath;
	private String title;
	private String albumName;
	private String albumImage;
	private String artistId;
	private String artistName;

}
