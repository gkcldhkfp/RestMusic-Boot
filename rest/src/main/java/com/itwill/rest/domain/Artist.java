package com.itwill.rest.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="ARTISTS")
@Getter @NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
@ToString
@Builder
public class Artist {
	@Id
	@Column(name = "ARTIST_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	private String artistName;
	
	private String artistImage;
	
	private String artistDescription;

	// @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
	// @ToString.Exclude
	// private List<ArtistRole> roles;
	
	@OneToMany(mappedBy = "artist")
	@Builder.Default
	@ToString.Exclude
	@JsonManagedReference // 순환참조 문제를 해결하기 위한 애너테이션
	private List<ArtistLike> artistLikes = new ArrayList<>();
	
}