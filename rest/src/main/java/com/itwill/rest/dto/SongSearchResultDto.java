package com.itwill.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SongSearchResultDto {

	private int songId;
	private String songTitle;
	private String albumImage;
	private String albumTitle;
	private String singerNames;
	private String singerIds;
	

}
