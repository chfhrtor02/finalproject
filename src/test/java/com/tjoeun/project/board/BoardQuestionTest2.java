package com.tjoeun.project.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tjoeun.project.board.questionDomain.BoardQuestionRepository;
import com.tjoeun.project.board.questionDomain.BoardQuestionVO;

@SpringBootTest
public class BoardQuestionTest2 {

	
	@Autowired
	public BoardQuestionRepository questionRepository;
	
	
	void testJpa() {
		
		List<BoardQuestionVO> all = this.questionRepository.findAll();
		
		assertEquals(2, all.size());
		
		
		BoardQuestionVO q = all.get(0);
		assertEquals("제목을 적으세요", q.getSubject());
		
		
		
		
	}
	
}
