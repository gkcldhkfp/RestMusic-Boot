package com.itwill.rest.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@EqualsAndHashCode @ToString
@Builder
public class Artist {
	@Id
	@Column(name = "ARTIST_ID")
	private Integer id;
	
	private String artistName;
	
	private String artistImage;
	
	private String artistDescription;

	@OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<ArtistRole> roles;
}
