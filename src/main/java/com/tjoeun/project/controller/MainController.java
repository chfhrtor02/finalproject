package com.tjoeun.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjoeun.project.board.questionDomain.BoardQuestionRepository;
import com.tjoeun.project.board.questionDomain.BoardQuestionVO;
import com.tjoeun.project.board.service.BoardQuestionService;
import com.tjoeun.project.product.domain.ProductVO;
import com.tjoeun.project.product.service.ProductService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class MainController {

	
	private final BoardQuestionRepository questionRepository;
	private final BoardQuestionService questionService;
	private final ProductService productService;
	
	
	
	// home버튼 클릭시 경로 
	
	@GetMapping("/")
	public String root(Model model , @RequestParam(value="page" , defaultValue="0") int page) {
		
		Page<BoardQuestionVO> paging = this.questionService.getMainList(page);
		Page<ProductVO> productVO = this.productService.getListMain(page);
		
		model.addAttribute("product" , productVO);
		model.addAttribute("paging" , paging); 
	
		return "/main/main";
	}

	
/*	@GetMapping("/sns")
	public String sns() {
		return "/login/sns";
	}
	
	@GetMapping("/naverProxy")
	public String naverProxy(@RequestParam("linking") String linking, HttpSession session) {
		String path = "/oauth2/authorization/naver";
		session.setAttribute("linkingOther2", linking);
		return "redirect:"+path;
	}
	
	@GetMapping("/googleProxy")
	public String googleProxy(@RequestParam("linking") String linking, HttpSession session) {
		String path = "/oauth2/authorization/google";
		session.setAttribute("linkingOther2", linking);
		return "redirect:"+path;
	}
	@GetMapping("/kakaoProxy")
	public String kakaoProxy(@RequestParam("linking") String linking, HttpSession session) {
		String path = "/oauth2/authorization/kakao";
		session.setAttribute("linkingOther2", linking);
		return "redirect:"+path;
	}
	*/
	
	@ResponseBody
	@GetMapping("/board")
	public String index() {
		
		return "안녕하세요";
		
	}
	
	
	
	
	
}
