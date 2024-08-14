package com.itwill.rest.domain;

import java.time.LocalDate;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@EqualsAndHashCode
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Basic(optional = false)
	@NaturalId
	private String userName;
	
	@Basic(optional = false)
	@NaturalId
	private String userId;
	
	@Basic(optional = false)
	@NaturalId
	private String password;
	
	@Basic(optional = false)
	@NaturalId
	private String email;
	
	@Basic(optional = false)
	private String nickname;

	private String userProfile;

	private String hintQuestion;

	private String hintAnswer;

	@Builder.Default
	private Integer isActive = 1;

	private LocalDate deactivatedUntil;

	// 권한 설정 하는 부분
	// 스프링 시큐리티 적용 후 사용할 오버라이드 메서드
	/* 
	@Builder.Default // Builder 패턴에서도 null이 아닌 HashSet<> 객체로 초기화 될 수 있도록 하는 설정.
	@ToString.Exclude // ToString 메서드에서 제외
	@ElementCollection(fetch = FetchType.LAZY) // 연관 테이블(member_roles) 사용.
	// @ManyToMany 애너테이션 안씀?
	// ! 그거 대신에 ElementCollection 애너테이션 쓴거임.
	@Enumerated(EnumType.STRING) // DB 테이블에 저장될 때 상수(enum) 이름(문자열)을 사용.
	private Set<UserRole> roles = new HashSet<>();

	// 편의 메서드
	// 유저의 권한을 부여하는 메서드.
	public User addRole(UserRole role) {
		roles.add(role);
		return this;
	}

	// 유저의 권한을 한 개 삭제하는 메서드
	public User removeRole(UserRole role) {
		roles.remove(role);
		return this;
	}

	// 유저의 권한을 삭제하는 메서드
	public User clearRoles() {
		roles.clear(); // Set<>이 가지고 있는 모든 원소를 지움.
		return this;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		// ! 람다표현식을 사용하지 않는 방법
		// ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		// for(MemberRole r : roles) {
		// GrantedAuthority auth = new SimpleGrantedAuthority(r.getAuthority());
		// authorities.add(auth);
		// }

		// ! 람다표현식을 사용한 방법
		List<SimpleGrantedAuthority> authorities = roles.stream().map((r) -> new SimpleGrantedAuthority(r.getAuthority()))
				.toList();

		return authorities;
	} */
}