package com.tjoeun.project.board.controller;


import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
public class CK5ImageController {

	// 절대경로 application.properties
	@Value("${CK5_image_path}")
	private String UPLOAD_URL;
	
	// 절대경로 application.properties
	@Value("${CK5_image_security_path}")
	private String UPLOAD_SECURITY_URL;
	

	@RequestMapping(value = "/image/upload" ,
					method = {RequestMethod.POST , RequestMethod.GET})
	@ResponseBody
	public String fileUpload(Model model, 
							@RequestParam(value="upload") MultipartFile fileload,
							HttpServletRequest request) throws JsonProcessingException {
		
		log.info("CK5 이미지 업로드");
		
		String json = "";
		
		// json 파일 변환용 map
		Map<String , Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		
		
		// 서버에 파일을 저장할 때에는 파일명을 시간값으로 변경
	    // DB에 저장할 때에는 원본 파일명과 시간값을 모두 저장
		// 파일의 오리지널 네임
		String originalFileName = fileload.getOriginalFilename();
	    
	    String uploadPath = UPLOAD_URL;
		
	    
	    log.info("---- uploadPath(upload_image) : " + uploadPath);
	    
	    
	    
	    // 파일의 확장자
	 	String ext = originalFileName.substring(originalFileName.indexOf("."));
		
	 	
	 	 // 서버에 저장될 때 중복된 파일 이름인 경우를 방지하기 위해 UUID에 확장자를 붙여 새로운 파일 이름을 생성
		String newFileName = UUID.randomUUID() + ext;
	    
	    // 업로드 수행	    
	    File file = new File(uploadPath + "/" + newFileName);
	    
	    log.info("업로드 이미지 파일명 : " + file);
	 	
	    
	    try {
	    	map.put("uploaded", true);
	    	map.put("url", request.getContextPath() + "/uploadImage/" + newFileName);
	    	// javateacher
	    	
	 		json = mapper.writeValueAsString(map);
	    	
	        // 실제 파일 업로드(저장)
	        FileUtils.writeByteArrayToFile(file, fileload.getBytes() );	     
	        
	    } catch (IOException e) {
	    	
	    	map.put("uploaded", false);
	    	map.put("error", "{ \"message\": \"업로드 중 에러가 발생했습니다. 다시 시도해 주세요.\" }");
	    	
	 		json = mapper.writeValueAsString(map);
	    }
	    
	    log.info("--- image file upload (json) : " + json);
	    
	    return json;
	    
	    
	}
	
}
