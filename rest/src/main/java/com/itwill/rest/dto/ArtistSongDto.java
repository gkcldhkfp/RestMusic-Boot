package com.itwill.rest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class ArtistSongDto {
	private Integer songId;
	private Integer albumId;
	private String albumName;
	private String albumImage;
	private String title;
	private List<Integer> artistId;
	private List<String> artistName;
	private List<Integer> groupId;
	private List<String> groupName;

}
