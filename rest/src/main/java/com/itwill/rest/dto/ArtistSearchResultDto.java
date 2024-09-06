package com.itwill.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ArtistSearchResultDto {
	
	private Integer artistId;
	private String artistName;
	private String artistImage;
	private Integer likeCount;
}
