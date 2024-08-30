package com.itwill.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDetailsDto {
	private Integer songId;
	
	private String songTitle;
	
	private Integer albumId;
	private String albumName;
	private String albumImage;
	
	private String genres;
	
	private String singers;
	private String singerIds;
	
	private String writers;
	private String writerIds;
	
	private String composers;
	private String composerIds;
	
	private String arrangers;
	private String arrangerIds;
	
	private String lyrics;

}
