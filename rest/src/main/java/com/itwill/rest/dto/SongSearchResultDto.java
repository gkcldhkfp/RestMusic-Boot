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

	private Integer songId;
	private String title;
	private Integer albumId;
	private String albumName;
	private String albumImage;
	private String artistName;
	private String groupName; 
	private String artistId;
	private String groupId;
    private Integer likeCount;


}
