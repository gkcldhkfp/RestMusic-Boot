package com.itwill.rest.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable @Data
@NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlayListSongId {
	private Integer pListId;

	private Integer songId;
	
}
