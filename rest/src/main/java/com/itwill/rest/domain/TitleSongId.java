package com.itwill.rest.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor @NoArgsConstructor
public class TitleSongId {

	private Long songId;
	private Long albumId;
	
	
}
