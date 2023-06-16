package com.tjoeun.project.board.questionDomain;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.tjoeun.project.board.answerDomain.BoardAnswerVO;
import com.tjoeun.project.domain.MemberVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name="BoardQuestionVO")
@Entity
public class BoardQuestionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "board_question_seq")
	@SequenceGenerator(name="board_question_seq", sequenceName="BOARD_QUESTION_SEQ" , allocationSize = 1)
	private Integer id;
	
	@Column(length = 400)
	private String subject;
	
	@Column(columnDefinition = "CLOB")
	private String content;
	
	@CreatedDate
	private LocalDateTime createDate;
	
	@ManyToOne
	private MemberVO author;
	
	private LocalDateTime modifyDate;
	
	
	// Answer 엔티티에서 Question 엔티티를 참조한 속성명 question을 mappedBy에 전달
	// CascadeType.REMOVE는 1개의 글에 여러개의 답글을 달수있다.
	@OneToMany(mappedBy = "boardQuestionVO" , cascade = CascadeType.REMOVE)
	private List<BoardAnswerVO> answerList;
	
	
	public void setAuthor(MemberVO author) {
		
        this.author = author;
        if (!author.getQuestionList().contains(this)) {
            author.getQuestionList().add(this);
        }
    }
	
}
