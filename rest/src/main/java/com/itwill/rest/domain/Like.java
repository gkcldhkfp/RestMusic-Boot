package com.itwill.rest.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

@Entity @Table(name = "LIKES")
@Getter @NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder @ToString @EqualsAndHashCode
public class Like {
	
	@EmbeddedId
	private LikeId likeId;
	
	@ToString.Exclude // ToString에서 제외. 안하면 무한루프에 빠질 수 있음.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SONG_ID")
	@JsonBackReference // 순환참조를 해결하기 위한 애너테이션
	@MapsId("songId")
	private Song song;
	
}
