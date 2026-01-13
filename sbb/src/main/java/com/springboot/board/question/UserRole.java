package com.springboot.board.question;

import lombok.Getter;

@Getter
public enum UserRole {
	// 열거형(바뀌지 않을 값을 정의)
	
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	UserRole(String value) {
		this.value = value;
	}
	
	private String value;
}