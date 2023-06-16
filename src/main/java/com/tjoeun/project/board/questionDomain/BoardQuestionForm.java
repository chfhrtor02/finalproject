package com.tjoeun.project.board.questionDomain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardQuestionForm {

	
	// validation 적용 >> 컨트롤러에서 데이터 검증수행 
	
	
	@NotEmpty(message = "제목을 입력하세요.")
	@Size(max=200)
	private String subject;
	
	
	@NotEmpty(message = "내용을 입력하세요.")
	private String content;
	
	
}
