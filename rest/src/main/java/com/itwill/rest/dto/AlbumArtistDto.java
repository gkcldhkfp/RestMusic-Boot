package com.itwill.rest.dto;

import java.util.ArrayList;
import java.util.List;

import com.itwill.rest.domain.ArtistRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumArtistDto {
	private Integer artistId;

	private String artistName;

	private Integer groupId;

	private String groupName;

	public static List<AlbumArtistDto> fromEntity(List<ArtistRole> list){
		List<AlbumArtistDto> albumArtists = new ArrayList<>();
		list.forEach(l -> {
			AlbumArtistDto dto = AlbumArtistDto.builder()
				.artistId(l.getArtist().getId())
				.artistName(l.getArtist().getArtistName())
				.groupId(l.getGroup() != null ? l.getGroup().getId() : null)
				.groupName(l.getGroup() != null ? l.getGroup().getGroupName() : null)
			.build();
			albumArtists.add(dto);
		});
		
		return albumArtists;
	}
}
