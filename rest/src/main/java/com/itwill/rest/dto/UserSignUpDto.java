package com.itwill.rest.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.itwill.rest.domain.User;

import lombok.Data;

@Data
public class UserSignUpDto {
	private String userId;

	private String userName;
	
	private String password;

	private String email;

	private String nickname;

	private String userProfile;

	private String hintQuestion;

	private String hintAnswer;

	public User toEntity(PasswordEncoder encoder) {
		return User.builder()
			.userId(userId)
			.userName(userName)
			.password(encoder.encode(password))
			.email(email)
			.nickname(nickname)
			.userProfile(userProfile)
			.hintQuestion(hintQuestion)
			.hintAnswer(hintAnswer)
			.build();
	}
}
