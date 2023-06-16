package com.tjoeun.project.board;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tjoeun.project.board.questionDomain.BoardQuestionRepository;
import com.tjoeun.project.board.questionDomain.BoardQuestionVO;

@SpringBootTest
public class BoardQuestionTest {

	
	@Autowired
	private BoardQuestionRepository questionRepository;
	
	
	
	@Test
	void testJpa() {
		
		BoardQuestionVO q1 = new BoardQuestionVO();
		
		q1.setSubject("제목을 적으세요");
		q1.setContent("내용도 같이 적어주세요.");
		q1.setCreateDate(LocalDateTime.now());
		
		this.questionRepository.save(q1);

		
		
		BoardQuestionVO q2 = new BoardQuestionVO();
		
		q2.setSubject("다른 제목을 적으세요");
		q2.setContent("다른 내용도 같이 적어주세요.");
		q2.setCreateDate(LocalDateTime.now());
		
		this.questionRepository.save(q2);
		
		
		// JunitTestSuccess!!
	}
	
}
