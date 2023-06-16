package com.tjoeun.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tjoeun.project.domain.MemberRepository;
import com.tjoeun.project.domain.MemberVO;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class AdminService {

	
	
	
	private final MemberRepository memberRepository;
	
	
	
	public List<MemberVO> getListMember() {
		
		
		return this.memberRepository.findAll() ;
		
	}
	
	
}
