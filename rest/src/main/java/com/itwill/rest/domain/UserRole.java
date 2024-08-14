package com.itwill.rest.domain;

public enum UserRole {
	USER("ROLE_USER"), // => public static final MemberRole User = new MemberRole("");
	ADMIN("ROLE_ADMIN"),
	GUEST("ROLE_GUEST");

	private String authority;

	UserRole(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return this.authority;
	}
}
