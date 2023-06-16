package com.tjoeun.project.board.answerDomain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.tjoeun.project.board.questionDomain.BoardQuestionVO;
import com.tjoeun.project.domain.MemberVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "BoardAnswerVO")
public class BoardAnswerVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_answer_seq")
	@SequenceGenerator(name = "board_answer_seq", sequenceName = "BOARD_ANSWER_SEQ", allocationSize = 1)
	private Integer id;

	@Column(columnDefinition = "CLOB")
	private String content;

	@CreationTimestamp
	private LocalDateTime createTime;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private BoardQuestionVO boardQuestionVO;
	
	@ManyToOne
	private MemberVO author;
	
	private LocalDateTime modifyDate;	

}
