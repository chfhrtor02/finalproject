package com.tjoeun.project.board.questionDomain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.project.domain.MemberVO;


public interface BoardQuestionRepository extends JpaRepository<BoardQuestionVO , Integer>{

	
	List<BoardQuestionVO> findBySubject(String subject);
	
	
	// Page 객체사용  >> 서비스 >> 컨트롤러 순서로 작성
	Page<BoardQuestionVO> findAll(Pageable pageable);
	
	
	List<BoardQuestionVO> findByAuthor(MemberVO author);
	
	
}
