package com.itwill.rest.dto;

import lombok.Data;

@Data
public class CommentCreateDto {
	
	private String cText;
	private Integer writer;
	private Integer songId;

}
