package com.itwill.rest.dto;

import java.time.LocalDate;
import java.util.List;

import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.Group;

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
	private List<Artist> artists;
	private List<Group> groups;

}
