package com.itwill.rest.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Embeddable
public class ArtistRoleId {

	private Long artistId;

	private Long songId;

	private Long roleId;

}
