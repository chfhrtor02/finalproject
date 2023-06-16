package com.tjoeun.project.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tjoeun.project.board.questionDomain.BoardQuestionRepository;
import com.tjoeun.project.board.questionDomain.BoardQuestionVO;

@SpringBootTest
public class BoaedQuestionTest3 {

	@Autowired
    private BoardQuestionRepository questionRepository;

    @Test
    void testFindBySubject() {
        // 테스트용 질문 데이터 추가
        BoardQuestionVO question1 = new BoardQuestionVO();
        question1.setSubject("제목을 적으세요12111");
        question1.setContent("내용을 적으세요1211");
        questionRepository.save(question1);

        BoardQuestionVO question2 = new BoardQuestionVO();
        question2.setSubject("제목을 적으세요12111");
        question2.setContent("다른 내용을 적으세요121");
        questionRepository.save(question2);

        // 주제로 질문 조회
        List<BoardQuestionVO> questions = questionRepository.findBySubject("제목을 적으세요12111");

        // 조회된 질문 개수 확인
        assertEquals(2, questions.size());

        // 조회된 질문의 주제가 일치하는지 확인
        for (BoardQuestionVO question : questions) {
            assertEquals("제목을 적으세요12111", question.getSubject());
        }
	
    }
}
