package com.itwill.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadMoreDataDto {
	

	private String keyword;
	private String sortType;
	private Integer offset;
	
	
}
