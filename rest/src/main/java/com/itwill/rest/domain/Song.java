package com.itwill.rest.domain;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder @ToString @EqualsAndHashCode
@Entity @Table(name = "SONGS")
public class Song {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer songId;

	@ToString.Exclude // ToString에서 제외. 안하면 무한루프에 빠질 수 있음.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ALBUM_ID")
	private Album album;

	@Basic(optional = false) // not null
	private String title;

	@Basic(optional = false) // not null
	private String songPath;

	@Basic(optional = false) // not null
	private String lyrics;

	private String videoLink;

	@ToString.Exclude
	@OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
	private List<SongGenre> genres;
	
}
