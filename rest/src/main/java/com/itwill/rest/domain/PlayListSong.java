package com.itwill.rest.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table(name = "PLAYLIST_SONGS")
@Getter @NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder @ToString @EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class PlayListSong {
	
//	@EmbeddedId
//	private PlayListSongId playListSongId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer playlistSongId;
	
//	@MapsId("pListId")
	@ManyToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	@JoinColumn(name = "P_LIST_ID")
	private PlayList playList;
	
//	@MapsId("songId")
	@ManyToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	@JoinColumn(name = "SONG_ID")
	private Song song;

	@LastModifiedDate
	@Column(name = "CREATED_TIME")
	private LocalDateTime createdTime;
	
}
