package com.tjoeun.project.domain;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateMemberForm {

	private String UserId;
	
	
	@NotEmpty(message = "사용자 비밀번호는 필수항목 입니다.")
	private String pw;
	
	
	private String name;
	
	
	private char gender;

	
	@NotEmpty(message = "사용자 E-mail주소는 필수항목 입니다.")
	@Email
	private String email;
	
	
	@NotEmpty(message = "사용자 연락처는 필수항목 입니다.")
	private String mobile;
	
	
	private String address1;
	
	
	private String address2;
	
	
	private Date birthday;
	
	
}
