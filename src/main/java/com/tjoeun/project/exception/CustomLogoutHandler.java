package com.tjoeun.project.exception;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CustomLogoutHandler implements LogoutHandler {

	
	
	public void logout (HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		 
		String accessToken = "${ACCESS_TOKEN}";
		HttpSession session = request.getSession(false);
        String authVendor = (String) session.getAttribute("authVendor");
    try {   
		 // kakao경우
		 if(authVendor != null && authVendor.equals("kakao")) {
			URL url= new URL("https://kapi.kakao.com/v1/user/logout");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer" + accessToken);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Logout successful");
            } else {
                System.out.println("Logout failed with error code: " + responseCode);
            }
		 }else if(authVendor != null && authVendor.equals("naver")) {
			 
		 }else if(authVendor != null && authVendor.equals("google")) {
			 
		 }
	       
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}