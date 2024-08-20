package com.itwill.rest.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@Table(name = "SONG_GENRE")
@Getter
@NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder @ToString
@EqualsAndHashCode
public class SongGenre {

	@EmbeddedId
	private SongGenreId id;

	@MapsId("songId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SONG_ID")
	@ToString.Exclude
	private Song song;

	@MapsId("genreId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GENRE_ID")
	@ToString.Exclude
	private GenreCode genreCode;
	
}
