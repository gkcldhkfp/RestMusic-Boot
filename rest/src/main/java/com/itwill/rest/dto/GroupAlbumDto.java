package com.itwill.rest.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class GroupAlbumDto {
	private Integer albumId;
	private String albumName;
	private String albumImage;
	private String albumType;
	private LocalDate albumReleaseDate;
	private List<Integer> artistIds;
	private List<String> artistNames;
	private List<Integer> groupIds;
	private List<String> groupNames;

}
