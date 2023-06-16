package com.tjoeun.project.board.answerDomain;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardAnswerForm {

	@NotEmpty(message = "내용을 적어주세요.")
	private String content;
	
}
