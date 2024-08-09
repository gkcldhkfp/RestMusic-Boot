package com.itwill.rest.domain;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Fetch;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ALBUMS")
@Getter @NoArgsConstructor 
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @Builder @ToString
public class Album {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 자동번호생성
	private Integer albumId;

	private String albumName;

	private String albumImage;

	@Basic(optional = false) // not null
	private String albumType;

	private LocalDate albumReleaseDate;

	@OneToMany(mappedBy = "album")
	private List<Song> songs;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
	private List<TitleSong> titleSongs;
	
	

}
