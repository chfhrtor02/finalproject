package com.tjoeun.project.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class SessionUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	private String name;
	private String email;
	private String picture;
	private String authVendor;
	
	public SessionUser(MemberVO user) {
		
		this.name = user.getName();
		this.email = user.getEmail();
		this.picture = user.getPicture();
		this.authVendor = user.getAuthVendor();
		
	}
	
	
}
