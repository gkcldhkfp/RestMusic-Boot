package com.itwill.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumLikeDto {
	private Integer albumId;
	private Integer loginUserId;
	
}
