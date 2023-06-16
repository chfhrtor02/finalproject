package com.tjoeun.project.controller;


import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.project.domain.DeleteMemberForm;
import com.tjoeun.project.domain.InsertMemberForm;
import com.tjoeun.project.domain.UpdateMemberForm;
import com.tjoeun.project.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/crud")
@RequiredArgsConstructor
@Controller
public class MemberController {

	
	private final MemberService memberService;
	
	
	
	/**
	 * 
	 * 회원가입 연결 맵핑 
	 * 
	 * @param insertMemberForm
	 * @return
	 */
	@GetMapping("/register")
	public String register (InsertMemberForm insertMemberForm) {

		return "/register/register_form";
		
	}
	
	
	@PostMapping("/register")
	public String register (@Valid InsertMemberForm insertMemberForm , BindingResult bindingResult) {
		
		
		if (bindingResult.hasErrors()) {
			
			return "/register/register_form";
			
		}
		
		
		try {

			memberService.insertMember( 
									   insertMemberForm.getPw(),
									   insertMemberForm.getName(),
									   insertMemberForm.getGender(),
									   insertMemberForm.getEmail(),
									   insertMemberForm.getMobile(),
									   insertMemberForm.getAddress1(),
									   insertMemberForm.getAddress2(),
									   insertMemberForm.getBirthday(),
									   insertMemberForm.getRole(),
									   insertMemberForm.getUserId(),
									   insertMemberForm.getAuthVendor()
									   );

			
		// userId 의 등록여부에 따른 회원가입 오류 	
		} catch (DataIntegrityViolationException e) {
			
			e.printStackTrace();
			bindingResult.reject("rgeisterFailed" , "등록된 아이디입니다.");
			
			return "/register/register_form";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			bindingResult.reject("registerFailed" , e.getMessage());
			
			return "/register/register_form";
		}
	
		
		return "redirect:/";
		
	}
	
	
	
	
	/**
	 * 
	 * 실제 로그인을진행하는 post매핑은 스프링시큐리티가 직접처리
	 * 사이트 사용자 로그인을 위한 매핑 
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		
		return "/login/login_form";
	}
	
	
	
	
	
	
	/**
	 * 
	 * 회원정보수정 연결 맵핑 
	 * 
	 * @param insertMemberForm
	 * @return
	 */
	@GetMapping("/update")
	public String update (HttpSession session, Model model) {

		String userId = (String) session.getAttribute("userId");
        String name = (String) session.getAttribute("name");
        String email = (String) session.getAttribute("email");
        String mobile = (String) session.getAttribute("mobile");
        String address1 = (String) session.getAttribute("address1");
        String address2 = (String) session.getAttribute("address2");
        Date birthday = (Date) session.getAttribute("birthday");
        
        UpdateMemberForm updateMemberForm = new UpdateMemberForm();
        
        updateMemberForm.setUserId(userId);
        updateMemberForm.setName(name);
        updateMemberForm.setEmail(email);
        updateMemberForm.setMobile(mobile);
        updateMemberForm.setAddress1(address1);
        updateMemberForm.setAddress2(address2);
        updateMemberForm.setBirthday(birthday);
        model.addAttribute(updateMemberForm);
		return "/register/update_form";
		
	}
	
	/**
	 * 
	 * 회원정보수정 연결 맵핑 
	 * 
	 * @param updateMemberForm
	 * @return
	 */
	@PostMapping("/update")
	public String Update (@Valid UpdateMemberForm updateMemberForm , BindingResult bindingResult) {

		
		
		if (bindingResult.hasErrors()) {
			
			return "/register/update_form";
			
		}
		
		try {

			memberService.updateMember(
									   updateMemberForm.getPw(),
									   updateMemberForm.getName(),
									   updateMemberForm.getGender(),
									   updateMemberForm.getEmail(),
									   updateMemberForm.getMobile(),
									   updateMemberForm.getAddress1(),
									   updateMemberForm.getAddress2(),
									   updateMemberForm.getBirthday(),
									   updateMemberForm.getUserId()
									   );

			
		// userId 의 등록여부에 따른 회원가입 오류 	
		} catch (DataIntegrityViolationException e) {
			
			e.printStackTrace();
			bindingResult.reject("rgeisterFailed" , "등록된 아이디입니다.");
			
			return "/register/update_form";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			bindingResult.reject("updateFailed" , e.getMessage());
			
			return "/register/update_form";
		}
		return "/register/update_form";
		
	}
	
	/**
	 * 
	 * 회원탈퇴 연결 맵핑 
	 * 
	 * @param delettMemberForm
	 * @return
	 */
	@GetMapping("/delete")
    public String delete(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        log.info("userId = {}", userId);
        DeleteMemberForm deleteMemberForm = new DeleteMemberForm();
        deleteMemberForm.setUserId(userId);
        model.addAttribute("deleteMemberForm", deleteMemberForm);
        return "/register/delete_form";
    }
	
	
	/**
	 * 
	 *  회원탈퇴 로직 맵핑
	 * 
	 * @param userId
	 * @param deleteMemberForm
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	//@PostMapping("/delete")
	//public String delete(@ModelAttribute @Valid DeleteMemberForm deleteMemberForm, BindingResult bindingResult, HttpServletRequest request) {
//		   
//		HttpSession session = request.getSession(false);
//		String userId = deleteMemberForm.getUserId();
	//	
//			log.info("가져와 좀 : " , userId);
	//
//	        // 회원 탈퇴 서비스 호출
//	        memberService.deleteMemberForm(userId);
	//
//	        // 세션 무효화
//	        session.invalidate();
	//
//	        return "redirect:/";
//	        }
		
		@PostMapping("/delete")
	    public String delete(@Valid DeleteMemberForm deleteMemberForm, BindingResult bindingResult, HttpServletRequest request) {
	        HttpSession session = request.getSession(false);
	        String userId = deleteMemberForm.getUserId();

	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        userId = authentication.getName();
	        
	        log.info("가져와 좀 : {}", userId);

	        // 회원 탈퇴 서비스 호출
	        this.memberService.deleteMemberForm(userId);

	        // 세션 무효화
	        session.invalidate();

	        return "redirect:/";
	    }
	
	
	
	
	
	
	
	
//	@PostMapping("/update")
//	public String updateMember (@RequestParam("userId")String userId, 
//			@Valid UpdateMemberForm updateMemberForm ,
//			BindingResult bindingResult) {
//		
//		if (bindingResult.hasErrors()) {
//			
//			return "/update/update_member_form";
//			
//		}
//		
//		try {
//			
//			MemberVO updateMember = memberService.getMemberVO(userId);
//			
//			updateMember.setPw(updateMemberForm.getPw());
//			updateMember.setName(updateMemberForm.getName());
//			updateMember.setGender(updateMemberForm.getGender());
//			updateMember.setMobile(updateMemberForm.getMobile());
//			updateMember.setAddress1(updateMemberForm.getAddress1());
//			updateMember.setAddress2(updateMemberForm.getAddress2());
//			updateMember.setBirthday(updateMemberForm.getBirthday());
//			
//			
//			memberService.updateMember(userId, updateMember);
//			
//			
//		}  catch (DataNotFoundException e) {
//            e.printStackTrace();
//            bindingResult.reject("updateFailed", "회원이 아닙니다.");
//            return "/update/update_member_form";
//        
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            bindingResult.reject("updateFailed", e.getMessage());
//            return "/update/update_member_form";
//		
//        }
//		
//		return "redirect:/update/update_member_form";
//		
//	}
	
}
		
	

