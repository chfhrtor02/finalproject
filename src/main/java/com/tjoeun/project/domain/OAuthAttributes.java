package com.tjoeun.project.domain;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Setter
@Getter
public class OAuthAttributes {
	
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String authVendor;//추가
    private String registrationId;
	
	
    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, 
    						String name, String email, String picture,
    						String authVendor) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.authVendor = authVendor;
    }
	
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        
    	OAuthAttributes result = null;
    	
    	// 인자명 통일화
    	if("naver".equals(registrationId)) {
            result = ofNaver("id", attributes);
        }
    	else if("google".equals(registrationId)) {
            result = ofGoogle(userNameAttributeName, attributes);    		
    	}
    	else if("kakao".equals(registrationId)){
    		result = ofKakao("id", attributes);
        }
    	
    	log.info("registrationId : " + registrationId);
    	
    	result.setAuthVendor(registrationId); // 추가
    	
    	return result;
	}
	
    // google
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        
    	return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .authVendor((String) attributes.get("authVendor"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    
 // kakao
    @SuppressWarnings("unchecked")
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
    	
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .authVendor((String) attributes.get("authVendor"))
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }
    
    // naver
    @SuppressWarnings("unchecked")
	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
       
    	Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .authVendor((String) attributes.get("authVendor"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    
	
	
	
	public MemberVO toEntity() {
		
		return MemberVO.builder().name(name)
								.email(email)
								.picture(picture)
								.role(MemberVORole.USER) // 기본권한은 GUEST
								.authVendor(authVendor)
								.build();
								
		
	}
	
	
}
