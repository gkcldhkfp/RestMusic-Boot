package com.itwill.rest.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "SONGS")
public class Song {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer songId;

	@ToString.Exclude // ToString에서 제외. 안하면 무한루프에 빠질 수 있음.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ALBUM_ID")
	@JsonBackReference // 순환참조를 해결하기 위한 애너테이션
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
	@Builder.Default
	@JsonBackReference // 순환참조를 해결하기 위한 애너테이션
	private List<ArtistRole> artistRole = new ArrayList<>();

	@ToString.Exclude
	@OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
	@Builder.Default
	@JsonBackReference // 순환참조를 해결하기 위한 애너테이션
	private List<SongGenre> genres = new ArrayList<>();

	@ToString.Exclude
	@OneToMany(mappedBy = "likeId.songId", fetch = FetchType.LAZY)
	@Builder.Default
	@JsonBackReference // 순환참조를 해결하기 위한 애너테이션
	private List<Like> likes = new ArrayList<>();

	// // 편의 메서드 추가
	// public int getLikesCount() {
	// return likes.size();
	// }

	/*
	 * @ToString.Exclude
	 * 
	 * @OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
	 * 
	 * @Builder.Default
	 * private Set<ArtistRole> roles = new HashSet<>();
	 * 
	 * //편의 메서드
	 * // 음원의 장르를 등록하는 메서드
	 * public Song setGenres (Set<SongGenre> genres){
	 * this.genres = genres;
	 * return this;
	 * }
	 * 
	 * // 음원의 장르를 추가하는 메서드
	 * public Song addGenre (SongGenre genre) {
	 * genres.add(genre);
	 * return this;
	 * }
	 * 
	 * // 음원의 특정 장르를 삭제하는 메서드
	 * public Song removeGenre(SongGenre genre) {
	 * genres.remove(genre);
	 * return this;
	 * }
	 * 
	 * // 음원의 장르를 모두 삭제하는 메서드
	 * public Song clearGenre() {
	 * genres.clear();
	 * return this;
	 * }
	 * 
	 * // 음원의 역할을 등록하는 메서드
	 * public Song setRoles (Set<ArtistRole> roles){
	 * this.roles = roles;
	 * return this;
	 * }
	 * 
	 * // 음원의 역할을 추가하는 메서드
	 * public Song addRole (ArtistRole role) {
	 * roles.add(role);
	 * return this;
	 * }
	 * 
	 * // 음원의 특정 역할을 삭제하는 메서드
	 * public Song removeRole(ArtistRole role) {
	 * roles.remove(role);
	 * return this;
	 * }
	 * 
	 * // 음원의 역할을 모두 삭제하는 메서드
	 * public Song clearRoles() {
	 * roles.clear();
	 * return this;
	 * }
	 */

}
