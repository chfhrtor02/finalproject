package com.tjoeun.project.service;

import java.sql.Date;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tjoeun.project.board.exception.DataNotFoundException;
import com.tjoeun.project.domain.MemberRepository;
import com.tjoeun.project.domain.MemberVO;
import com.tjoeun.project.domain.MemberVORole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	
	
	
	// 스프링 시큐리티에 설정
	private final PasswordEncoder passwordEncoder;
	
	
	/**
	 * 
	 * 회원가입
	 * 
	 * @param userId
	 * @param email
	 * @param pw
	 * @return
	 */
	public MemberVO insertMember(String pw , String name , char gender ,
								 String email , String mobile , String address1 , 
								 String address2 , Date birthday, MemberVORole role, String userId,
								 String authVendor) {
		
		MemberVO memberVO = new MemberVO();
		
		
		// BCryptPasswordEncoderw 사용 >> SecurityConfig
		memberVO.setPw(passwordEncoder.encode(pw));
		memberVO.setName(name);
		memberVO.setEmail(email);
		memberVO.setGender(gender);
		memberVO.setEmail(email);
		memberVO.setMobile(mobile);
		memberVO.setAddress1(address1);
		memberVO.setAddress2(address2);
		memberVO.setBirthday(birthday);
		memberVO.setRole(role);
		memberVO.setUserId(userId);
		memberVO.setAuthVendor("form");
		
		
		this.memberRepository.save(memberVO);
		
		return memberVO;
	}

	
	
	/**
	 * 
	 * 사용자 정보 조회 : 일반 사용자 
	 * 
	 * @param userId
	 * @return
	 */
	public MemberVO getMemberVO(String email) {

		
		
		Optional<MemberVO> memberVO = this.memberRepository.findByEmail(email);
	    if (memberVO.isPresent()) {
	        MemberVO member = memberVO.get();
	        if (member != null) {
	            return member;
	        } else {
	            throw new DataNotFoundException("일반회원가입 사용자가 아닙니다.");
	        }
	    } else {
	        throw new DataNotFoundException("일반회원가입 사용자가 아닙니다.");
	    }
		
	}
	
	
	
	
	
	public MemberVO getMemberByUserId(String userId) {
	    Optional<MemberVO> memberOptional = memberRepository.findByUserId(userId);
	    return memberOptional.orElse(null); // 또는 memberOptional.orElseThrow();
	}
	
	
	
	
	
//	public MemberVO getMemberVO(String userId, String email) {
//		
//	    Optional<MemberVO> memberVO;
//	    
//	    if (userId != null) {
//	        memberVO = this.memberRepository.findByUserId(userId);
//	        
//	    } else if (email != null) {
//	        memberVO = this.memberRepository.findByEmail(email);
//	        
//	    } else {
//	        throw new IllegalArgumentException("UserId와 Email 모두 없습니다.");
//	    }
//
//	    if (memberVO.isPresent()) {
//	        return memberVO.get();
//	    } else {
//	    	
//	        throw new DataNotFoundException("일반회원가입 사용자가 아닙니다.");
//	    }
//	}
	
	
	
	
	
	
	
//	/**
//	 * 
//	 * 회원정보 검색 object 객체를 사용하여  MemberVO 사용자일때와 User사용자일 때를 구분한다.
//	 * 
//	 * @param userId
//	 * @return
//	 */
//	public MemberVO getMemberVO(Object object) {
//		MemberVO memberVO = null;
//		
//		if (object instanceof MemberVO) {
//			memberVO = (MemberVO) object;
//		} else if (object instanceof User) {
//			User user = (User) object;
//			Optional<MemberVO> optionalMemberVO = memberRepository.findById(user.getId());
//			if (optionalMemberVO.isPresent()) {
//				memberVO = optionalMemberVO.get();
//			} else {
//				throw new DataNotFoundException("회원이 아닙니다.");
//			}
//		}
//		
//		return memberVO;
//	}
	
	
	
	
	
	/**
	 * 회원 수정
	 * @param pw
	 * @param name
	 * @param gender
	 * @param email
	 * @param mobile
	 * @param address1
	 * @param address2
	 * @param birthday
	 * @param userId
	 * @return
	 */
	public MemberVO updateMember(String pw, String name, char gender, String email, String mobile ,
								 String address1, String address2, Date birthday, String userId) { //, HttpSession session

	
	
		Optional<MemberVO> optionalMemberVO = memberRepository.findByUserId(userId);

		if (optionalMemberVO.isPresent()) { // 회원이 존재하는지 체크
			
			MemberVO memberVO = optionalMemberVO.get();
			
			memberVO.setPw(passwordEncoder.encode(pw));
			memberVO.setName(name);
			memberVO.setGender(gender);
			memberVO.setEmail(email);
			memberVO.setMobile(mobile);
			memberVO.setAddress1(address1);
			memberVO.setAddress2(address2);
			memberVO.setBirthday(birthday);
		
		
			this.memberRepository.save(memberVO);
		
		
		return memberVO;
		
		} else {
			
			throw new DataNotFoundException("회원정보 오류");
			
		}
		
		
		
	}
	
	
	
	
	
	/**
	 * 
	 * 회원탈퇴 
	 * @param userId
	 */
//	public void deleteMemberForm(@Valid String userId) {
//	    Optional<MemberVO> optionalMemberVO = memberRepository.findByUserId(userId);
//	    log.info("userId = " + userId);
//	    
//	  if (optionalMemberVO.isPresent()) {
//	        MemberVO memberVO = optionalMemberVO.get();
//	        log.info("회원 탈퇴: userId4 = " + userId);
//	        memberRepository.delete(memberVO);
//	        log.info("회원이 성공적으로 탈퇴되었습니다.");
//	    } else {
//	    	
//	        log.warn("회원 탈퇴 실패: userId " + userId + "에 해당하는 회원이 없습니다.");
//	    }
//	}
	public void deleteMemberForm(String userId) {
//		log.info("memberRepository.findAll().get(0);"+memberRepository.findAll().get(0));
//		log.info("findAllById : " + memberRepository.findById(61));
//		log.info("findAllBy : " + memberRepository.findByName("ewq"));
		log.info("memberRepository.findByUserId(userId) : "+memberRepository.findByUserId(userId));
	    
		Optional<MemberVO> optionalMemberVO = memberRepository.findByUserId(userId);
	    log.info("userId = " + userId);
//	    log.info("optionalMemberVO1 : " + optionalMemberVO.get());
//	    log.info("optionalMemberVO2 : " + optionalMemberVO.isPresent());
	    if (!optionalMemberVO.isEmpty()) {
	        MemberVO memberVO = optionalMemberVO.get();
	        log.info("회원 탈퇴: userId4 = " + userId);
	        memberRepository.delete(memberVO);
	        log.info("회원이 성공적으로 탈퇴되었습니다.");
	    } else {
	        log.warn("회원 탈퇴 실패 userId :  " + userId + "에 해당하는 회원이 없습니다.");
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	/**
//	 * 
//	 * 회원정보 수정 
//	 * 
//	 * 
//	 * @param userId
//	 * @param memberVO
//	 * @return
//	 */
//	public MemberVO updateMember(String userId , MemberVO memberVO) {
//		
//		Optional<MemberVO> optionalMember = memberRepository.findByUserId(userId);
//		
//		
//		if (optionalMember.isPresent()) {
//			
//			MemberVO updateMember = optionalMember.get();
//			
//			updateMember.setPw(updateMember.getPw());
//			updateMember.setName(updateMember.getName());
//			updateMember.setGender(updateMember.getGender());
//			updateMember.setMobile(updateMember.getMobile());
//			updateMember.setAddress1(updateMember.getAddress1());
//			updateMember.setAddress2(updateMember.getAddress2());
//			updateMember.setBirthday(updateMember.getBirthday());
//			
//			memberRepository.save(updateMember);
//			
//			return updateMember;
//			
//		} else {
//			
//			throw new DataNotFoundException("회원이 아닙니다");
//			
//		}
//		
//	}
	
}

