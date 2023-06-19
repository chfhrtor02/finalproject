package com.tjoeun.project.link;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.tjoeun.project.domain.OAuthAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationSuccess implements AuthenticationSuccessHandler {
   
   private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
   public CustomAuthenticationSuccess() {
	   log.info("테스트1");
   }
   
   public void onAuthenticationSuccess(HttpServletRequest request ,HttpServletResponse response,
            Authentication authenticationRespo) throws IOException ,ServletException {
      
	  LinkRepository linkrepository;
//	  Authentication authentication = null;
	  Link link;
	  
      log.info(request.getRequestURI());
      
      HttpSession session = request.getSession();
      
      String other2 = (String)session.getAttribute("linking");
      
      log.info("linking(session) : " + other2);
      
          
      
      if(Boolean.parseBoolean(other2)) {
    	  OAuth2UserRequest userRequest;
    	  OAuth2UserService delegate = new DefaultOAuth2UserService();
    	  OAuth2User oAuth2User;
    	  
    	  OAuthAttributes2 attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
  				oAuth2User.getAttributes());
    	  
    	  
			log.debug(social);
			log.debug(email);
//			linkrepository.save(link);
			
			session.removeAttribute("linking");
			
			redirectStrategy.sendRedirect(request, response, "/info");
      }else {
    	  redirectStrategy.sendRedirect(request, response, "/");
      }
      
      //session.removeAttribute("linking");
      
      
      //redirectStrategy.sendRedirect(request, response, "/");
   }
   
}
