package com.tjoeun.project.DTO;

import java.sql.Date;

import com.tjoeun.project.domain.MemberVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDTO {

	private String id;
	private String userId;
	private String pw;
	private String name;
	private char gender;
	private String email;
	private String mobile;
	private String address1;
	private String address2;
	private Date birthday;
	private Date joindate;
	
	
	// 사용자에게 받은 DTO를 VO로 전환하는 생성자
	
	public MemberDTO(final MemberVO memberVO) {
		
		this.userId = memberVO.getUserId();
		this.pw = memberVO.getPw();
		this.name = memberVO.getName();
		this.gender = memberVO.getGender();
		this.email = memberVO.getEmail();
		this.mobile = memberVO.getMobile();
		this.address1 = memberVO.getAddress1();
		this.address2 = memberVO.getAddress2();
		this.birthday = memberVO.getBirthday();
		this.joindate = memberVO.getJoindate();
	}
	
	// 사용자에게 받은 DTO를 VO로 전환하는 메소드 

	
	public static MemberVO toMemberVO(final MemberDTO dto) {
		
		
		return MemberVO.builder().userId(dto.getUserId())
								 .pw(dto.getPw())
								 .name(dto.getName())
								 .gender(dto.getGender())
								 .email(dto.getEmail())
								 .mobile(dto.getMobile())
								 .address1(dto.getAddress1())
								 .address2(dto.getAddress2())
								 .birthday(dto.getBirthday())
								 .joindate(dto.getJoindate())
								 .build();
	
	}
	
	
	
}