package com.itwill.rest.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChoSeongSearchDto {
	private String type;
	private Integer id;
	private String name;
	private LocalDate releaseDate; // 날짜 필드는 LocalDate를 사용 (필요에 따라 java.util.Date 사용 가능)
	private Integer likeCount;
}
