package com.itwill.rest.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@Builder.Default
	@ToString.Exclude
	@JsonManagedReference // 순환참조 문제를 해결하기 위한 애너테이션
	private List<Song> songs = new ArrayList<>();
	
	@ToString.Exclude
	@OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
	@Builder.Default
	@JsonManagedReference // 순환참조 문제를 해결하기 위한 애너테이션
	private List<TitleSong> titleSongs = new ArrayList<>();

	@OneToMany(mappedBy = "album")
	@Builder.Default
	@ToString.Exclude
	@JsonManagedReference // 순환참조 문제를 해결하기 위한 애너테이션
	private List<AlbumLike> albumLikes = new ArrayList<>();

	public Integer getAlbumLikeCount() {
		return albumLikes.size();
	}
	
	/* //편의 메서드
	// 앨범의 수록곡을 등록하는 메서드
	public Album setSongs (Set<Song> songs){
		this.songs = songs;
		return this;
	}

	// 앨범의 수록곡을 추가하는 메서드
	public Album addSong (Song song) {
		songs.add(song);
		return this;
	}

	// 앨범의 특정 곡을 삭제하는 메서드
	public Album removeSong(Song song) {
		songs.remove(song);
		return this;
	}

	// 앨범의 수록곡을 모두 삭제하는 메서드
	public Album clearSong() {
		songs.clear();
		return this;
	} */

}
