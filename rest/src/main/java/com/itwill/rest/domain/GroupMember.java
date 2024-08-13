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
@Table(name = "GROUP_MEMBERS")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Builder
public class GroupMember {
	@EmbeddedId
	private GroupMemberId id;

	@MapsId("groupId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID")
	@ToString.Exclude
	private Group group;
	
	@MapsId("artistId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARTIST_ID")
	@ToString.Exclude
	private Artist artist;

	/* // 편의 메서드
	// 그룹의 멤버를 등록하는 메서드
	public GroupMember setArtists(Set<Artist> artists) {
		this.artists = artists;
		return this;
	}

	// 그룹의 멤버를 추가하는 메서드
	public GroupMember addArtist(Artist artist) {
		artists.add(artist);
		return this;
	}

	// 그룹의 특정 멤버를 삭제하는 메서드
	public GroupMember removeArtist(Artist artist) {
		artists.remove(artist);
		return this;
	}

	// 그룹의 멤버를 모두 삭제하는 메서드
	public GroupMember clearArtist() {
		artists.clear();
		return this;
	} */
}
