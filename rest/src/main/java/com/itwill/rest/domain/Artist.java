package com.itwill.rest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private Long id;
	
	private String artistName;
	
	private String artistImage;
	
	private String artistDescription;

	// @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
	// @ToString.Exclude
	// private List<ArtistRole> roles;
}
