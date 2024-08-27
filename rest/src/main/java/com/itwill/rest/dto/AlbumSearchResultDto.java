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
public class AlbumSearchResultDto {
	
	private Integer albumId;

	private String albumName;

	private String albumImage;

	private String albumType;

	private LocalDate albumReleaseDate;
	
	private String singerNames;
	
	private String singerIds;

}
