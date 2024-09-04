package com.itwill.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.SavedRequest;

@Configuration
// -> 스프링 컨테이너에서 생성하고 관리하는 설정 컨포넌트.
// -> 스프링 컨테이너에서 필요한 곳에 의존성 주입을 해줌.
@EnableMethodSecurity
public class SecurityConfig {

	// ! Spring Security 5 버전부터 비밀번호는 반드시 암호화를 해야만 함.
	// 만약 비밀번호를 암호화하지 않으면, HTTP 403 에러가 발생(access denied, 접근 거부) 또는
	// HTTP 500 (internal server error, 내부 서버 오류) 에러가 발생함.
	// 비밀번호를 암호화하는 객체를 스프링 컨테이너가 bean으로 관리 해야함.
	@Bean
	// @Bean => 객체를 스프링 컨테이너에서 관리하도록 생성자를 bean으로 등록함.
	// 스프링 레거시에서는 web.xml에서 bean 태그로 등록했음
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		// ! 암호화 알고리즘 중 하나.
		// ! BCryptPasswordEncoder는 PasswordEncoder 인터페이스를 구현한 수많은 클래스 중 하나임.
	}

	// 사용자 관리(로그인, 로그아웃, 회원가입 등)를 위한 서비스 인터페이스.
	// 스프링 부트 애플리케이션에서 스프링 시큐리티를 이용한 로그인/로그아웃을 하려면
	// ! UserDetailsService 인터페이스를 구현하는 서비스 클래스와
	// ! UserDetails 인터페이스를 구현하는 엔터티 클래스가 반드시있어야 함.
	// 사용자 엔터티와 사용자 서비스를 구현하기 전에 테스트 용도로 사용할 코드.
	// ? 스프링 부트가 자동으로 만들어주는 아이디 외의 다른 아이디를 생성해서 테스트하기 위해서 필요함.
	/*
	 * @Bean
	 * public UserDetailsService inMemoryDetailsService() {
	 * // 애플리케이션이 동작 중에 메모리에 임시 저장하는 사용자 객체를 생성
	 * // * import org.springframework.security.~~ 이거임 다른거하면 에러남
	 * // UserDetails를 구현하는 임시 엔터티 클래스
	 * // ? 나중에 만들 Member 엔터티도 이런 메서드들을 사용해서 만들 듯?
	 * UserDetails user1 = User.withUsername("user1")
	 * .password(passwordEncoder().encode("1111"))
	 * // ! 아규먼트 타입은 String이지만 암호화를 하지 않으면 로그인 안됨. 만들어둔 암호화 메서드로 암호화해야함.
	 * .roles("USER") // 사용자 권한(ADMIN, USER, ...)
	 * .build(); // User 객체를 생성
	 * 
	 * UserDetails user2 = User.withUsername("user2")
	 * .password(passwordEncoder().encode("2222"))
	 * .roles("ADMIN", "USER")
	 * .build();
	 * 
	 * UserDetails user3 = User.withUsername("user3")
	 * .password(passwordEncoder().encode("3333"))
	 * .roles("ADMIN")
	 * .build();
	 * 
	 * // User 타입 객체 3개를 가지고 있는 UserDetailsService 객체를 생성하고 리턴.
	 * return new InMemoryUserDetailsManager(user1, user2, user3);
	 * }
	 */

	// 스프링 시큐리티 필터 체인 객체(bean)
	// 로그인/로그아웃, 인증 필터에서 필요한 설정을 구성.

	// - 로그인 페이지(뷰), 로그아웃 페이지 설정.
	// - 페이지 접근 권한(ADMIN, USER) 설정.
	// - 인증 설정(로그인 없이 접근 가능한 페이지 vs 로그인해야만 접근 가능한 페이지)
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// *시큐리티 관련 설정들을 구성.
		// *대부분의 메서드는 아규먼트로 람다표현식(콜백함수)를 사용함.
		// *메서드를 사용하는 경우도 람다표현식을 리턴해주는 메서드임.
		// *http가 가지고 있는 메서드는 설정할 수 있는 객체를 아규먼트로 전달해 주는거임.
		// *설정할 때 아규먼트로 전달해주는 객체가 가지고있는 메서드로 설정하면 됨.

		// !CSRF 기능을 비활성화:
		// CSRF(Cross Site Request Forgery) 기능은 활성화시키는 게 좋음: 보안 관련 기능임.
		// EX: 은행 사이트와 똑같은 사이트를 만들어두고 이 사이트에서 로그인했을 때
		// 그 정보로 진짜 사이트에서 로그인을 하는 해킹을 방지하는 기술.
		// 방식: 숨겨놓은 input 필드에 서버에서 자동 생성된 보안 키를 POST방식 요청을 보낼 때 같이 보내서
		// 가짜 사이트에서 보낸 요청을 걸러낼 수 있음.
		// ? 근데 왜 비활성화함?
		// 기능을 활성화 시키면 Ajax로 요청을 보낼 때도 CSRF 토큰이 필요함(보안 키)
		// 우리는 Ajax요청을 보낼 때 이걸 고려해서 만들지 않았으므로
		// 활성화를 해두면 Ajax 요청으로 구현한 기능들이 동작하지 않음.
		// ? => HTTP 403 에러 발생
		http.csrf((csrf) -> csrf.disable()); // 기본 값: csrf.Enable()

		// 로그인 페이지(폼) 설정 - 스프링 시큐리티에서 제공하는 기본 HTML 페이지를 사용.
		// http.formLogin(Customizer.withDefaults());
		// Custom 로그인 페이지를 사용.
		http.formLogin((login) -> login.
			loginPage("/member/signin")
			.successHandler((request, response, authentication) -> {
                SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                String targetUrl = savedRequest != null ? savedRequest.getRedirectUrl() : "/home";
                response.sendRedirect(targetUrl);
            })
            .permitAll());


		// ! frame태그 사용 시 스프링 시큐리티에서 해주어야하는 설정
		http.headers(c -> c.frameOptions(op -> op.sameOrigin()));

		// ! 페이지 접근 권한, 인증 구성: 아래의 1 또는 2 방법 중 한 가지를 선택.
		// 1. HttpSecurity.authorizeHttpRequests(Customizer customizer) 메서드에서 설정.
		// -> 장점: 한 곳에서 모든 설정을 구성할 수 있음.
		// -> 단점: 새로운 요청 경로가 생길 때마다 설정 구성 코드를 수정해야 함.
		// 2. 컨트롤러 메서드에서 애너테이션으로 설정.
		// (1) SecurityConfig 빈에 @EnableMethodSecurity 애너테이션을 설정.
		// (2) 각각이 컨트롤러 메서드에서 @PreAuthorize 또는 @PostAuthorize 애너테이션을 설정.
		http.authorizeHttpRequests((auth) ->
		// auth.anyRequest().authenticated()
		// ? 어떤 요청이든(anyRequest) 인증되어야함(authenticated)
		// 모든 요청 주소에 대해서 (role에 상관 없이) 아이디/비밀번호 인증을 하는 경우:

		// auth.anyRequest().hasRole("USER")
		// ? 모든 요청 주소에 대해서 "USER" 권한을 가진 아이디/비밀번호 인증을 하는 경우

		auth
				// : *과 **의 차이. **: /api/comment를 포함하고 하위 폴더가 몇단계가 있던 포함함.
				// : *: /api/comment를 포함하지 않고 하위폴더를 1단계만 포함함. ex: /api/comment/20
				.requestMatchers("/post/create", "/post/details", "/post/modify", "/post/delete", "/post/update")
				// 설정할 URL 주소를 나열함. 요청 방식도 설정할 수 있음.
				.hasAnyRole("USER")
				// 위의 주소는 아이디/비밀번호 인증이 필요함
				// .requestMatchers("").hasRole("ADMIN") 이런식으로 연결이 가능.
//				.requestMatchers("/api/comment/**")
//				.hasAnyRole("ADMIN", "USER")
				.anyRequest()
				// 위에 설정한 URL 주소를 제외한 모든 주소는
				.permitAll()
		// 모두 허용함.
		// ? 로그인이 필요한 페이지와 그렇지 않은 페이지를 구분해서 설정 구성

		// ! 주소설정 메서드 1번(리턴타입: AuthorizedUrl)
		// ! 설정한 주소에 대한 접근권한 메서드 1번(리턴타입: AuthorizationManagerRequestMatcherRegistry)
		// ? 번갈아가면서 사용이 가능함. => 최종 리턴 타입이 AuthorizationManagerRequestMatcherRegistry 이어야
		// 함.
		);

		return http.build(); // DefaultSecurityFilterChanin 객체를 생성해서 리턴.
	}


}
