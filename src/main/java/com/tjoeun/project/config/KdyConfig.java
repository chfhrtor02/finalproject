package com.tjoeun.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class KdyConfig implements WebMvcConfigurer {

	
	// !!!절대경로!!! application.properties 와 동일하게 작성 
	private static String UPLOAD_URL = "C:/CK5_image_path/";
	
	// !!!절대경로!!! application.properties 와 동일하게 작성
	private static String UPLOAD_SECURITY_URL = "file:///C:/CK5_image_path/";
	
	
	private final String[] RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/",
			"classpath:/resources/",
			"classpath:/static/image/" , 
			UPLOAD_URL , 
			UPLOAD_SECURITY_URL
			};
	
	
	private final String[] CK5_IMAGE_RESOURCE_LOCATIONS = {
			UPLOAD_URL , 
			UPLOAD_SECURITY_URL
			};
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		if (!registry.hasMappingForPattern("/webjars/**")) {
			registry.addResourceHandler("/webjars/**").addResourceLocations(
					"classpath:/META-INF/resources/webjars/");
		}
		
		if (!registry.hasMappingForPattern("/**")) {
			registry.addResourceHandler("/**").addResourceLocations(
					RESOURCE_LOCATIONS);
		}
		
		if (!registry.hasMappingForPattern("/uploadImage/**")) {
			registry.addResourceHandler("/uploadImage/**").addResourceLocations(
					CK5_IMAGE_RESOURCE_LOCATIONS);
		}
	}
	
}
