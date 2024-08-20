package com.itwill.rest.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.Group;
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

	private List<Integer> groupId;

	private List<String> groupName;

	public static SongPlayerDto fromEntity(Album album, Song song, AlbumSongsService albumServ) {

		// Set을 사용해 중복된 그룹과 아티스트를 처리
		Set<String> processedGroupNames = new HashSet<>();
		Set<Integer> processedArtistIds = new HashSet<>();

		// 그룹을 먼저 가져오기
		List<Group> groups = albumServ.selectGroupBySong(song);

		// 그룹을 처리
		List<Group> uniqueGroups = new ArrayList<>();
		if (groups != null) {
			for (Group group : groups) {
				if (!processedGroupNames.contains(group.getGroupName())) {
					processedGroupNames.add(group.getGroupName());
					uniqueGroups.add(group);

					// 그룹 멤버를 processedArtistIds에 추가하여 중복 제거
					group.getGroupMembers().forEach(member -> processedArtistIds.add(member.getArtist().getId()));
				}
			}
		}

		// 아티스트를 필터링: 그룹에 포함되지 않은 아티스트만 가져오기
		List<Artist> filteredArtists = albumServ.selectSingersBySong(song).stream()
				.filter(artist -> !processedArtistIds.contains(artist.getId()))
				.collect(Collectors.toList());

		// DTO 생성
		SongPlayerDto dto = new SongPlayerDto(
				album.getAlbumImage(),
				song.getSongId(),
				song.getTitle(),
				song.getSongPath(),
				filteredArtists.stream().map(Artist::getId).collect(Collectors.toList()),
				filteredArtists.stream().map(Artist::getArtistName).collect(Collectors.toList()),
				uniqueGroups.stream().map(Group::getId).collect(Collectors.toList()),
				uniqueGroups.stream().map(Group::getGroupName).collect(Collectors.toList()));

		return dto;
	}

}
