package com.itwill.rest.domain;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PurUserId {

	private Integer id;

	private LocalDate expirationDate;
}
