package com.tjoeun.project.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tjoeun.project.domain.MemberVO;
import com.tjoeun.project.service.AdminService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

	
	private final AdminService adminService;
	
	
	@GetMapping("/list")
	public String getList(Model model) {
		
		
		List<MemberVO> member = this.adminService.getListMember();
		
		
		model.addAttribute("member" , member); 
		
		
		return "/admin/admin_list";
	}
	
	
}
