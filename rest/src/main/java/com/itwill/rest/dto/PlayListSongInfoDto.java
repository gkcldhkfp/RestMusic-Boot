package com.itwill.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class PlayListSongInfoDto {
	private Integer pListId; // playList id(playlists 테이블)
//	private String pListName; // playList 이름(playlists 테이블)
	private Integer songId; // playList 음원 id(playlist_songs 테이블)
	private LocalDateTime createdTime; // playlist에 song 추가날짜(playlist_songs 테이블)
	private String title; // song 제목(songs 테이블)
	private Integer albumId; // song(음원)의 albumId(songs 테이블)
	private String albumImage; // album 커버(album 테이블)
	private String albumName;
	private List<Integer> artistId; // 가수 id(artists 테이블)
	private List<String> artistName; // 가수 이름(artists 테이블)
	private List<Integer> groupId;
	private List<String> groupName;

}
