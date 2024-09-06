package com.itwill.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupAndArtistDto {
	private String type;
	private Integer id;
	private String name;
	private String image;
	private Integer likeCount;
}
