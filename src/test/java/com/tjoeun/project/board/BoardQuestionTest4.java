package com.tjoeun.project.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tjoeun.project.board.service.BoardQuestionService;

@SpringBootTest
public class BoardQuestionTest4 {

	@Autowired
	private BoardQuestionService questionService;
	
	@Test
	void testWrite() {
		
		for (int i = 1 ; i <= 300 ; i++) {

			String Subject = String.format("테스트모드가즈아:[%03d]", i);
			String content = "없다.";
			
			this.questionService.create(Subject, content , null , null);
			
			
		}
		
		
	}
	
	
	
	
}
