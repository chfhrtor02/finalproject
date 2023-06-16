package com.tjoeun.project.service;


import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tjoeun.project.domain.MemberRepository;
import com.tjoeun.project.domain.MemberVO;
import com.tjoeun.project.domain.MemberVORole;

import lombok.RequiredArgsConstructor;

/**
 * 
 * 로그인기능을 시큐리티에 등록해서 사용하기때문에 작성
 * 
 * 
 * @author ssucn
 *
 */
@RequiredArgsConstructor
@Service
public class MemberVOSecurityService implements UserDetailsService {

	
	private final MemberRepository memberRepository;
	
	
	
	/**
	 * 
	 * 스프링시큐리티 로그인 처리의 핵심
	 * 사용자명이 admin 인경우에는 admin권한 부여
	 * 그외의 경우는 user 권한 부여
	 * loadUserByUsername은 시큐리티에서 User객체의 비밀번호가 입력받은 비밀번호와 일치하는지 검사하는 로직을 보유 
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException{
		
		
		Optional<MemberVO> _memberVO = this.memberRepository.findByUserId(userId);
		
		if(_memberVO.isEmpty()) {
			
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다");
			
		}
		
		MemberVO memberVO = _memberVO.get();
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if("admin".equals(userId)) {
			
			authorities.add(new SimpleGrantedAuthority(MemberVORole.ADMIN.getValue()));
			
		} else {
			
			authorities.add(new SimpleGrantedAuthority(MemberVORole.USER.getValue()));
			
		}
		
		
		
		return new User(memberVO.getUserId() , memberVO.getPw() , authorities);
	}
	
	
}
