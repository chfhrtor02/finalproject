package com.tjoeun.project.board.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.tjoeun.project.board.answerDomain.BoardAnswerForm;
import com.tjoeun.project.board.questionDomain.BoardQuestionForm;
import com.tjoeun.project.board.questionDomain.BoardQuestionVO;
import com.tjoeun.project.board.service.BoardQuestionService;
import com.tjoeun.project.domain.MemberVO;
import com.tjoeun.project.service.CustomOAuth2UserService;
import com.tjoeun.project.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("/board")	// localhost:8181/board
@RequiredArgsConstructor    // final 속성의 생성자를 자동생성
@Controller
public class BoardQuestionController {


	// repository 에서 직접연결하징낳고 서비르슬 통해서 컨트롤러로 인자가 전달되게 변경
//	private final BoardQuestionRepository questionRepository;
	
	// repository 에서 직접연결하지않고 서비스를 통해서 컨트롤러로 인자가 전달되게 변경
	private final BoardQuestionService questionService;
//	private final MemberService memberService;
//	private final CustomOAuth2UserService customOAuth2UserService;
	
	
	
	
	/**
	 * 
	 * URL에 페이지 파라미터 page가 전달되지 않은 경우 디폴트 값으로 0이 되도록 설정
	 * 
	 * 
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping("/list")
	public String list(Model model , @RequestParam(value="page" , defaultValue="0") int page) {
		
		
		
		Page<BoardQuestionVO> paging = this.questionService.getList(page);
		
		
		model.addAttribute("paging" , paging); 
		
		
		return "/board/board_question_list";
		
	}
	
	
	
	
	
	
	
	
	/**
	 * 
	 * 게시판 페이지
	 * // localhost:8181/board/list
	 * 
	 * @param model
	 * @return
	 */
//	@GetMapping("/list")
//	public String list(Model model) {
//
//		
////		List<BoardQuestionVO> questionList = this.questionRepository.findAll();
//		List<BoardQuestionVO> questionList = this.qurstionService.getList();
//
//		model.addAttribute("questionList",questionList);
//		
//		
//		return "/board/board_question_list";
//		
//	}
	
	
	
	/**
	 * 
	 * 게시판 글 내용 검색 (연결)
	 * {id} = board primary key
	 * // localhost:8181/board/detail/{id}
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping(value="/detail/{id}")
	public String detail(Model model , 
						@PathVariable("id") Integer id , BoardAnswerForm answerForm) {

		BoardQuestionVO question = this.questionService.getQuestion(id);
		
		model.addAttribute("question" , question);
		
		return "/board/board_question_detail";
		
	}
	
	
	
	
	/**
	 * 
	 * 게시글등록 버튼으로 통해 get방식으로 요엉이되더라도, th:object에 의해 
	 * BoardQuestionForm 의 객체가 필요해서, GetMapping도 설정
	 * 
	 * @param questionForm
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(BoardQuestionForm questionform) {
		
		
		return "/board/board_question_form";
		
	}
	

	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param questionform
	 * @param bindingResult
	 * @param principal
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(@Valid BoardQuestionForm questionform,
	                             BindingResult bindingResult,
	                             Principal principal) {
	    if (bindingResult.hasErrors()) {
	        return "/board/board_question_form";
	    }


	    // Oauth2AuthenticationToken으로 로그인한 경우
	    if (principal instanceof OAuth2AuthenticationToken) {
	        // Oauth2 로그인 사용자의 경우에도 글을 작성할 수 있도록 처리
	        String email = getEmailFromPrincipal(principal);
	        log.info("email");
	        this.questionService.create(questionform.getSubject(), questionform.getContent(), null, email);
	    }
	    // UsernamePasswordAuthenticationToken으로 로그인한 경우
	    else if (principal instanceof UsernamePasswordAuthenticationToken) {
	    	
	    	String name = getUserIdFromPrincipal(principal);
	    	log.info(name);
	        this.questionService.create(questionform.getSubject(), questionform.getContent(), name, null);
	    }

	    
	    
	    return "redirect:/board/list";
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
//	 * 
//	 * 
//	 * 
//	 * 
//	 * @param questionform
//	 * @param bindingResult
//	 * @param principal
//	 * @return
//	 */
//	@PreAuthorize("isAuthenticated()")
//	@PostMapping("/create")
//	public String questionCreate(@Valid BoardQuestionForm questionform,
//	                             BindingResult bindingResult,
//	                             Principal principal) {
//	    if (bindingResult.hasErrors()) {
//	        return "/board/board_question_form";
//	    }
//
//
//	    // Oauth2AuthenticationToken으로 로그인한 경우
//	    if (principal instanceof OAuth2AuthenticationToken) {
//	        // Oauth2 로그인 사용자의 경우에도 글을 작성할 수 있도록 처리
//	        String email = getEmailFromPrincipal(principal);
//	        this.questionService.create(questionform.getSubject(), questionform.getContent(), null, email);
//	    }
//	    // UsernamePasswordAuthenticationToken으로 로그인한 경우
//	    else if (principal instanceof UsernamePasswordAuthenticationToken) {
//	        String userId = getUserIdFromPrincipal(principal);
//	        this.questionService.create(questionform.getSubject(), questionform.getContent(), userId, null);
//	    }
//
//	    
//	    
//	    return "redirect:/board/list";
//	}
//
//	
//	// Oauth2 로그인 사용자의 이메일 가져오기
//	private String getEmailFromPrincipal(Principal principal) {
//	    OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) principal;
//	    return oauthToken.getPrincipal().getAttribute("email");
//	}
//
//	// UsernamePasswordAuthenticationToken 로그인 사용자의 ID 가져오기
//	private String getUserIdFromPrincipal(Principal principal) {
//	    UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;
//	    return auth.getName();
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	/**
//	 * 
//	 * 
//	 * sns 로그인사용자 게시판 글작성 가능 
//	 * 
//	 * 
//	 * Validation 적용
//	 * 게시글 작성
//	 * 작성한글을 서버로 보내야함 POST
//	 * Principal 객체를 사용한 메소드에는 @PreAuthorize 를 사용해야한다.
//	 * 
//	 * @param questionform
//	 * @param bindingResult
//	 * @return
//	 */
//	@PreAuthorize("isAuthenticated()")
//	@PostMapping("/create")
//	public String questionCreate(@Valid BoardQuestionForm questionForm ,
//			BindingResult bindingResult ,
//			Principal principal) {
//		
//		if (bindingResult.hasErrors()) {
//			
//			return "/board/board_question_form";
//			
//		}
//		
//		
//		String userId = null;
//	    String email = null;
//
//	    if (principal instanceof OAuth2AuthenticationToken) {
//	        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) principal;
//	        String provider = authenticationToken.getAuthorizedClientRegistrationId();
//
//	        if (provider.equals("google") || provider.equals("naver") || provider.equals("kakao")) {
//	            email = authenticationToken.getPrincipal().getAttribute("email");
//	        }
//	    } else {
//	        userId = principal.getName();
//	    }
//
//	    questionService.create(questionForm.getSubject(), questionForm.getContent(), userId, email);
//
//		
//		return "redirect:/board/list";
//	}
	
	
	
	
	
	
	
	
	// validaion 적용전
//	public String questionCreate(@RequestParam String subject , 
//								 @RequestParam String content) {
//		
//		
//		// BoardQuestionService 로 작성데이터를 전달 
//		this.qurstionService.create(subject, content);
//		
//		
//		// return "/board/board_question_form"; 
//		return "redirect:/board/list"; //게시글 작성후 목록으로 이동 
//		
//	}
	
	
	/**
	 * 
	 * 게시글 수정 
	 * 
	 * @param questionFrom
	 * @param Id
	 * @param principal
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify (BoardQuestionForm questionForm,
								 @PathVariable("id") Integer id, Principal principal) {
		
		BoardQuestionVO question = this.questionService.getQuestion(id);
		
		
		  // OAuth2 토큰으로 로그인한 경우
	    if (principal instanceof OAuth2AuthenticationToken) {
	        String email = getEmailFromPrincipal(principal);
	        if (!question.getAuthor().getEmail().equals(email)) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	        }
	    }
	    // Username 토큰으로 로그인한 경우
	    else if (principal instanceof UsernamePasswordAuthenticationToken) {
	        String userId = getUserIdFromPrincipal(principal);
	        if (!question.getAuthor().getUserId().equals(userId)) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	        }
	    }
	    else {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	    }

		
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		
		return "/board/board_question_form";
		
	}
	

	
	
	/**
	 * 
	 * 
	 * Validation 추가 
	 * 게시글 수정후 저장할떄의 메소드
	 * 
	 * 
	 * @param questionForm
	 * @param bindingResult
	 * @param principal
	 * @param id
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify (@Valid BoardQuestionForm questionForm, BindingResult bindingResult,
								  Principal principal, @PathVariable("id") Integer id) {
		
		if(bindingResult.hasErrors()) {
			
			return "board_questionForm";
			
		}
		
		BoardQuestionVO question = this.questionService.getQuestion(id);
		
		log.info("Aauthor : " ,question.getAuthor());
		log.info("principal : " , principal);
		
		
		
		// Oauth2AuthenticationToken으로 로그인한 경우
	    if (principal instanceof OAuth2AuthenticationToken) {
	        // Oauth2 로그인 사용자의 경우에도 글을 작성할 수 있도록 처리
	        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
	    }
	    // UsernamePasswordAuthenticationToken으로 로그인한 경우
	    else if (principal instanceof UsernamePasswordAuthenticationToken) {
	    	
	    	
	        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
	    }
		
	
		return String.format("redirect:/board/detail/%s", id);
	}
	
	
	

	
	/**
	 * 
	 * 게시글 삭제
	 * 
	 * @param principal
	 * @param id
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal,@PathVariable("id") Integer id) {
		
		
		BoardQuestionVO question = this.questionService.getQuestion(id);
		
		// 로그인한 사용자와 게시글 작성자를 equals 를 사용하여 비교 
		if (principal instanceof OAuth2AuthenticationToken) {
	        String email = getEmailFromPrincipal(principal);
	        if (!question.getAuthor().getEmail().equals(email)) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	        }
	    }
	    // Username 토큰으로 로그인한 경우
	    else if (principal instanceof UsernamePasswordAuthenticationToken) {
	        String userId = getUserIdFromPrincipal(principal);
	        if (!question.getAuthor().getUserId().equals(userId)) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	        }
	    }
	    else {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	    }
		
		this.questionService.delete(question);
		
		return "redirect:/board/list/";
		
	}
	

	
}
