package com.itwill.rest.domain;

import java.time.LocalDateTime;

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

@Entity @Table(name = "DEL_USERS")
@Getter @NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Builder
@ToString @EqualsAndHashCode
public class DelUser {
	// FK
	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID")
	@ToString.Exclude
	private User user;

	
	private LocalDateTime deletedTime;

	/* @MapsId("deletedTime")
	private LocalDateTime deletedTime;  */
	//? DB에서 디폴트값을 줬는데 여기에서도 디폴트값을 주면 에러날 것 같음.
	//? 객체 생성 시 자동으로 디폴트 값이 들어가지 않으면 테이블에서 주는
	//! 디폴트 값을 제거하고 여기에서 디폴트값을 설정해야 함!
}
