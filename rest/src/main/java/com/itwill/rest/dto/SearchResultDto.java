package com.itwill.rest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SearchResultDto {
	
	private List<SongSearchResultDto> song;
	
	private List<AlbumSearchResultDto> album;
	
	private List<ArtistSearchResultDto> artist;

}
