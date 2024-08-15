package com.itwill.rest.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GroupMemberId {
	private Integer groupId;

	private Integer artistId;
}
