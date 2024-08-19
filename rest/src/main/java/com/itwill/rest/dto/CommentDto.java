package com.itwill.rest.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	
	private Integer id;
	private Integer cId;
	private String nickname;
	private String cText;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
	
	
}
