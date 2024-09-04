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
@Table(name = "ARTIST_ROLES")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Builder
public class ArtistRole {
	@EmbeddedId
	private ArtistRoleId id;

	@MapsId("artistId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARTIST_ID")
	@ToString.Exclude
	private Artist artist;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID")
	@ToString.Exclude
	private Group group;

	@MapsId("songId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SONG_ID")
	@ToString.Exclude
	private Song song;

	@MapsId("roleId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	@ToString.Exclude
	private RoleCode roleCode;
	
}
