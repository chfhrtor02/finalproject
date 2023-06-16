package com.tjoeun.project.board.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.tjoeun.project.board.answerDomain.BoardAnswerForm;
import com.tjoeun.project.board.answerDomain.BoardAnswerVO;
import com.tjoeun.project.board.questionDomain.BoardQuestionVO;
import com.tjoeun.project.board.service.BoardAnswerService;
import com.tjoeun.project.board.service.BoardQuestionService;
import com.tjoeun.project.domain.MemberVO;
import com.tjoeun.project.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/reply")
@RequiredArgsConstructor
@Controller
public class BoardAnswerController {

	
	private final BoardQuestionService questionService;
	private final BoardAnswerService answerService;
	private final MemberService memberService;
	
	/**
	 * 
	 * Valodation 적용 
	 * 작성자 확인을 위해 시큐리티 Principal 객체를 사용
	 * 댓글작성
	 * localhost:8181/reply/create/{id}
	 * 
	 * @param model
	 * @param id
	 * @param answerForm
	 * @param bindingresult
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createAnswer(Model model , @PathVariable("id") Integer id ,
							   @Valid BoardAnswerForm answerForm ,
							   BindingResult bindingresult ,
							   Principal principal) {
		
		
		BoardQuestionVO question = this.questionService.getQuestion(id);
		
		if(bindingresult.hasErrors()) {
			
			model.addAttribute("question" , question) ; 
			
			return "/board/board_question_detail";
			
		}
		
		// Oauth2AuthenticationToken으로 로그인한 경우
	    if (principal instanceof OAuth2AuthenticationToken) {
	        // Oauth2 로그인 사용자의 경우에도 글을 작성할 수 있도록 처리
	        String email = getEmailFromPrincipal(principal);
	        
	        this.answerService.create(question, answerForm.getContent(), null, email);
	    }
	    // UsernamePasswordAuthenticationToken으로 로그인한 경우
	    else if (principal instanceof UsernamePasswordAuthenticationToken) {
	    	
	    	String name = getUserIdFromPrincipal(principal);
	    	
	        this.answerService.create(question, answerForm.getContent(), name, null);
	    }
		
		
		
		return String.format("redirect:/board/detail/%s", id);
		
	}
	
	
	// Oauth2 로그인 사용자의 이메일 가져오기
	private String getEmailFromPrincipal(Principal principal) {
	    OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) principal;
	    return oauthToken.getPrincipal().getAttribute("email");
	}

	

	//UsernamePasswordAuthenticationToken 로그인 사용자의 ID 가져오기
	private String getUserIdFromPrincipal(Principal principal) {
	    UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;
	    return auth.getName();
	}
	
//	/**
//	 *
//	 * Validation 적용전
//	 * 
//	 * 댓글 작성
//	 * // localhost:8181/reply/create/{id}
//	 * 
//	 * @param model
//	 * @param id
//	 * @param content
//	 * @return
//	 */
//	@PostMapping("/create/{id}")	
//	public String createAnswer(Model model , 
//							   @PathVariable("id") Integer id , 
//							   @RequestParam String content) {
//
//		BoardQuestionVO question = this.questionService.getQuestion(id);
//		
//		this.answerService.create(question, content);
//		
//		return String.format("redirect:/board/detail/%s", id);
//		
//	}
	
	/**
	 * 
	 * 답변 수정 클릭시 URL 처리
	 * 
	 * @param answerForm
	 * @param id
	 * @param principal
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(BoardAnswerForm answerForm ,@PathVariable("id") Integer id,
								Principal principal) {
		
		BoardAnswerVO answer = this.answerService.getAnswer(id);
		
		  // OAuth2 토큰으로 로그인한 경우
	    if (principal instanceof OAuth2AuthenticationToken) {
	        String email = getEmailFromPrincipal(principal);
	        if (!answer.getAuthor().getEmail().equals(email)) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	        }
	    }
	    // Username 토큰으로 로그인한 경우
	    else if (principal instanceof UsernamePasswordAuthenticationToken) {
	        String userId = getUserIdFromPrincipal(principal);
	        if (!answer.getAuthor().getUserId().equals(userId)) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	        }
	    }
	    else {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	    }

		
		answerForm.setContent(answer.getContent());
		
		return "/board/board_answer_form";
		
	}
	
	/**
	 * 
	 * 답변수정 처리
	 * 
	 * @param answerForm
	 * @param bindingResult
	 * @param id
	 * @param principal
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid BoardAnswerForm answerForm, BindingResult bindingResult,
								@PathVariable("id") Integer id, Principal principal) {
		
		if (bindingResult.hasErrors()) {
			
			return "/board/board_answer_form";
			
		}
		
		BoardAnswerVO answer = this.answerService.getAnswer(id);
		
		// Oauth2AuthenticationToken으로 로그인한 경우
	    if (principal instanceof OAuth2AuthenticationToken) {
	        // Oauth2 로그인 사용자의 경우에도 글을 작성할 수 있도록 처리
	        this.answerService.modify(answer, answerForm.getContent());
	    }
	    // UsernamePasswordAuthenticationToken으로 로그인한 경우
	    else if (principal instanceof UsernamePasswordAuthenticationToken) {
	    	
	    	
	        this.answerService.modify(answer, answerForm.getContent());
	    }
		
	    
	    
		return String.format("redirect:/board/detail/%s", answer.getBoardQuestionVO().getId());
		
	}
	
	
	
	
	/**
	 * 
	 * reply 삭제 
	 * 
	 * @param principal
	 * @param id
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
		
		BoardAnswerVO answer = this.answerService.getAnswer(id);
		
		// 로그인한 사용자와 게시글 작성자를 equals 를 사용하여 비교 
		if (principal instanceof OAuth2AuthenticationToken) {
	        String email = getEmailFromPrincipal(principal);
	        if (!answer.getAuthor().getEmail().equals(email)) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	        }
	    }
	    // Username 토큰으로 로그인한 경우
	    else if (principal instanceof UsernamePasswordAuthenticationToken) {
	        String userId = getUserIdFromPrincipal(principal);
	        if (!answer.getAuthor().getUserId().equals(userId)) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	        }
	    }
	    else {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	    }
		
		this.answerService.delete(answer);
		
		return String.format("redirect:/board/detail/%s", answer.getBoardQuestionVO().getId());
		
	}
	
	
	
	
	
	
}
