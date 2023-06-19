package com.tjoeun.project.service;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.tjoeun.project.domain.MemberRepository;
import com.tjoeun.project.domain.MemberVO;
import com.tjoeun.project.domain.OAuthAttributes;
import com.tjoeun.project.domain.SessionUser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @SuppressWarnings({"rawtypes", "unchecked"})
	@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    	
        OAuth2UserService delegate = new DefaultOAuth2UserService();        
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, 
        		oAuth2User.getAttributes());

        MemberVO user = saveOrUpdate(attributes,registrationId);
        
        user.setAuthVendor(attributes.getAuthVendor()); // 추가
        
        log.info("CustomOAuth2UserService loadUser user : " + user); // 추가
        
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
	                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
	                attributes.getAttributes(),
	                attributes.getNameAttributeKey());
    }


    private MemberVO saveOrUpdate(OAuthAttributes attributes, String registrationId) {
        MemberVO user = memberRepository.findByEmail(attributes.getEmail())
						                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
						                .orElse(attributes.toEntity());

        return memberRepository.save(user);
    }

    
    
    
    
    
}