package com.itwill.rest.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table(name = "PUR_USERS")
@Getter @NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Builder
@ToString @EqualsAndHashCode
public class PurUser {
	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	@JoinColumn(name = "USER_ID")
	private User user;

	private LocalDate expirationDate;
}
