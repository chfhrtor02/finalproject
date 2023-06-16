package com.tjoeun.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tjoeun.project.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

/**
 * 
 * EnableMethodSecurity(prePostEnabled = true)는 @PreAuthorize 를 사용하려면 필수적용
 * 
 * @author ssucn
 *
 */
@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig  {

	
	private final CustomOAuth2UserService customOAuth2UserService;
	
	// 절대경로 application.properties 
	@Value("${CK5_image_security_path}")
	private String UPLOAD_URL;
	
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		
		return (web) -> web.ignoring().antMatchers("/image/upload" , "uploadIMage/**");
		
	}
	
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		// 인가되지 않은 모든 요청을 받겠다. /**
		http.authorizeHttpRequests().requestMatchers(
	                new AntPathRequestMatcher("/**")).permitAll()
	            .and()
	            	// csrf = 사이트 취약점 공격을 방지
	                .csrf()
	            .and()
	                .headers()
	                .addHeaderWriter(new XFrameOptionsHeaderWriter(
	                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
	                
	            .and()
	            	.formLogin()
	            	.loginPage("/crud/login")
	            	.usernameParameter("userId")
	            	.passwordParameter("pw")
	            	.defaultSuccessUrl("/")
	            .and()
	                .logout()
	                												// 로그아웃 URL설정
	                .logoutRequestMatcher(new AntPathRequestMatcher("/crud/logout"))
	                				// 로그아웃 성공시 루트페이지로 이동
	                .logoutSuccessUrl("/")
	                .invalidateHttpSession(true)
	            .and()
		            .oauth2Login()
		            .defaultSuccessUrl("/")
					.userInfoEndpoint()
					.userService(customOAuth2UserService)
					
					;
	        return http.build();
		
	}
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	
	
	
}
