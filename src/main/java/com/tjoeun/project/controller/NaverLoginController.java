package com.tjoeun.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.project.domain.SessionUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/login")
@RequiredArgsConstructor
@Controller
public class NaverLoginController {


	
	@Autowired
	private HttpSession httpSession;
	
	@GetMapping("/oauth2/naver")
	public String naverOAuth2Login (Model model) {
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
		log.info("sessionUser : {}",sessionUser);
		if (sessionUser != null) {
            model.addAttribute("user", sessionUser.getName());
        }
        return "redirect:/";
	}
	
	@GetMapping("/naver/fail")
	public String naverFail() {
	    return "/crud/login";
	}
	
	
}
