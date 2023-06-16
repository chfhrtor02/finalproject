package com.tjoeun.project.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * 
 * 자료형이라 setter없이 getter만 사용
 * 
 * @author ssucn
 *
 */
@Getter
@RequiredArgsConstructor
public enum MemberVORole {

	ADMIN("ROLE_ADMIN" , "관리자"),
	
	GUEST("ROLE_GUEST", "손님"),
	
	USER("ROLE_USER", "일반 사용자");
	
	
	MemberVORole(String key, String title) {
		
	        this.key = key;
	        this.title = title;
	        this.value = key;
	}
	
	
	
	private final String value;
	
	private final String key;
	private final String title;

	
}
