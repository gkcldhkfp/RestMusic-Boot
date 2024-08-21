package com.itwill.rest.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ArtistAlbumDto {
	private Integer albumId;
	private String albumName;
	private String albumImage;
	private String albumType;
	private LocalDate albumReleaseDate;
	private String artistName;
	private String artistId;

}
