package com.itwill.rest.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Embeddable
public class ArtistRoleId {

	private Integer artistId;

	private Integer songId;

	private Integer roleId;

}
