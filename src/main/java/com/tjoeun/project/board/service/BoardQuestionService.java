package com.tjoeun.project.board.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tjoeun.project.board.exception.DataNotFoundException;
import com.tjoeun.project.board.questionDomain.BoardQuestionRepository;
import com.tjoeun.project.board.questionDomain.BoardQuestionVO;
import com.tjoeun.project.domain.MemberRepository;
import com.tjoeun.project.domain.MemberVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardQuestionService {

	
	private final BoardQuestionRepository questionRepository;
	
	private final MemberRepository memberRepository;
	
	/**
	 * 
	 * 전체 게시판 전체 제목(subject) 검색 
	 * 
	 * @return
	 */
	public List<BoardQuestionVO> getList() {
		
		return this.questionRepository.findAll();
		
		
	}
	
	
	
	
	
	public BoardQuestionVO getQuestion(Integer id) {
		
		
		Optional<BoardQuestionVO> question = this.questionRepository.findById(id);
		
		if (question.isPresent()) {
			
			return question.get();
			
		} else {
			
			// RuntimeException을 상속받음
			// 에러 발생시 404 error
			throw new DataNotFoundException("Question not found");
			
		}
		

		
		
	}
	
	public void create(String subject, String content, String userId, String email) {
	    BoardQuestionVO question = new BoardQuestionVO();
	    
	    
	    question.setSubject(subject);
	    question.setContent(content);
	    question.setCreateDate(LocalDateTime.now());
	    
	    MemberVO member = null;

//	    if (userId != null) {
//	        member = memberRepository.findMemberByUserIdOrEmail(userId, null);
//	        
//	    } 
	    
	    if (userId == null) {
            member = memberRepository.findByEmail(email).get();
         } 
	    else if (email != null) {
	        member = memberRepository.findMemberByUserIdOrEmail(null, email);
	    }

	    if (member == null) {
	        throw new DataNotFoundException("일반회원가입 사용자가 아닙니다.");
	    }

	    question.setAuthor(member);
	    
	    
	    questionRepository.save(question);
	}
	
	
	
	
	
	/**
	 * 
	 * 게시판화면에 보여질 페이지지정
	 * 
	 * @param page
	 * @return
	 */
	public Page<BoardQuestionVO> getList (int page) {
		
		
		// 게시판 작성글 역순정렬
		List<Sort.Order> sorts = new ArrayList<>();
		// 작성일기준 역정렬
		sorts.add(Sort.Order.desc("createDate"));
		
		
		// 페이지당 보여질 갯수설정								역정렬 삽입
		PageRequest pageable = PageRequest.of(page, 15, Sort.by(sorts));
		
		
		return this.questionRepository.findAll(pageable);
		
	}
	
	/**
	 * 
	 * 메인화면에 보여질 페이지지정
	 * 
	 * @param page
	 * @return
	 */
	public Page<BoardQuestionVO> getMainList (int page) {
		
		
		// 게시판 작성글 역순정렬
		List<Sort.Order> sorts = new ArrayList<>();
		// 작성일기준 역정렬
		sorts.add(Sort.Order.desc("createDate"));
		
		
		// 페이지당 보여질 갯수설정								역정렬 삽입
		PageRequest pageable = PageRequest.of(page, 6, Sort.by(sorts));
		
		
		return this.questionRepository.findAll(pageable);
		
	}
	
	/**
	 * 
	 * 게시글 수정
	 * 
	 * @param question
	 * @param subject
	 * @param content
	 */
	public void modify(BoardQuestionVO question, String subject , String content) {

		question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
		
	}
	
	
	
	/**
	 * 
	 * 게시글 삭제
	 * 
	 * @param question
	 */
	public void delete(BoardQuestionVO question) {
		
		this.questionRepository.delete(question);
		
	}
	
	
	
	
}
