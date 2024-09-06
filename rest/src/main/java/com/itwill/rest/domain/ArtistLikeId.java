package com.itwill.rest.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder @Embeddable
public class ArtistLikeId {
	private Integer artistId;
	
	private Integer id;

}
