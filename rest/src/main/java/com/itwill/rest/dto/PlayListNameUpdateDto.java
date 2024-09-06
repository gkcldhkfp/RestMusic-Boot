package com.itwill.rest.dto;

import com.itwill.rest.domain.PlayList;

import lombok.Data;

@Data
public class PlayListNameUpdateDto {
	private Integer pListId;
	private String pListName;
	
	public PlayList toEntity() {
		return PlayList.builder().pListId(pListId).pListName(pListName).build();
	}

}
