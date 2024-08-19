package com.itwill.rest.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Song;
import com.itwill.rest.service.AlbumSongsService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongPlayerDto {
	
	private String albumImage;

	private Integer songId;

	private String title;

	private String songPath;

	private List<Integer> artistId;

	private List<String> artistName;

	private Integer groupId;

	private String groupName;

	public static SongPlayerDto fromEntity(Album album, Song song, AlbumSongsService albumServ) {

		SongPlayerDto dto = new SongPlayerDto(
				album.getAlbumImage(),
				song.getSongId(),
				song.getTitle(),
				song.getSongPath(),
				albumServ.selectSingersBySong(song).stream().map(a -> a.getId()).collect(Collectors.toList()),
				albumServ.selectSingersBySong(song).stream().map(a -> a.getArtistName()).collect(Collectors.toList()),
				albumServ.selectGroupBySong(song) != null ? albumServ.selectGroupBySong(song).getId() : null,
				albumServ.selectGroupBySong(song) != null ? albumServ.selectGroupBySong(song).getGroupName() : null
		);


		return dto;
}

}
