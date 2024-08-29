package com.itwill.rest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class GroupSongDto {
	private Integer songId;
	private Integer albumId;
	private String albumName;
	private String albumImage;
	private String title;
	private List<Integer> artistIds;
	private List<String> artistNames;
	private List<Integer> groupIds;
	private List<String> groupNames;

}
