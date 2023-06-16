package com.tjoeun.project.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;




public interface MemberRepository extends JpaRepository<MemberVO , Long> {

	// 로그인 구현을 위한 메소드
	Optional<MemberVO> findByUserId(String userId);

	Optional<MemberVO> findByEmail(String email);
	
	MemberVO findMemberByUserIdOrEmail(String userId, String email);
	
	
	@EntityGraph(attributePaths = "questionList")
    MemberVO findWithQuestionListById(Long id);

	MemberVO getMemberByUserId(String userId);
	
	// 전체 회원조회
	List<MemberVO> findAll();
	
}
