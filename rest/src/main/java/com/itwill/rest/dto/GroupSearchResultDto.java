package com.itwill.rest.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GroupSearchResultDto {
	
	
	private Integer groupId;
	private String groupName;
	private String groupImage;
	private Integer likeCount;

}
