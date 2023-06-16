package com.tjoeun.project.board.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tjoeun.project.board.answerDomain.BoardAnswerRepository;
import com.tjoeun.project.board.answerDomain.BoardAnswerVO;
import com.tjoeun.project.board.exception.DataNotFoundException;
import com.tjoeun.project.board.questionDomain.BoardQuestionVO;
import com.tjoeun.project.domain.MemberRepository;
import com.tjoeun.project.domain.MemberVO;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class BoardAnswerService {

	
	private final BoardAnswerRepository answerRepository;
	private final MemberRepository memberRepository;
	
	/**
	 * 
	 * reply 내용 저장 
	 * 
	 * 
	 * @param question
	 * @param content
	 */
	public void create (BoardQuestionVO question , String content , String userId, String email) {
		
		BoardAnswerVO answer = new BoardAnswerVO();
		
		answer.setContent(content);;
		answer.setCreateTime(LocalDateTime.now());
		answer.setBoardQuestionVO(question);
		
		MemberVO member = null;

//	    if (userId != null) {
//	    	member = memberRepository.findMemberByUserIdOrEmail(userId, null);
//	        
//	        
//	    }
	    if (userId == null) {
            member = memberRepository.findByEmail(email).get();
         } 
	    else if (email != null) {
	    	member = memberRepository.findMemberByUserIdOrEmail(null, email);
	    }

		
		answer.setAuthor(member);
		
		this.answerRepository.save(answer);
		
		
	}
	
	
	
	/**
	 * 
	 * reply 조회
	 * 
	 * @param id
	 * @return
	 */
	public BoardAnswerVO getAnswer(Integer id) {
		
		Optional<BoardAnswerVO> answer = this.answerRepository.findById(id);
		
		if(answer.isPresent()) {
			
			return answer.get();
			
		} else {
			
			throw new DataNotFoundException("reply를 찾을수 없습니다");
			
		}
	}
	
	
	/**
	 * 
	 * reply 수정
	 * 
	 * @param answer
	 * @param content
	 */
	public void modify(BoardAnswerVO answer, String content) {
		
		
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		
		this.answerRepository.save(answer);
		
	}
	
	
	
	/**
	 * 
	 * reply 삭제
	 * 
	 * @param answer
	 */
	public void delete (BoardAnswerVO answer) {
		
		this.answerRepository.delete(answer);
		
	}
	
	
}
