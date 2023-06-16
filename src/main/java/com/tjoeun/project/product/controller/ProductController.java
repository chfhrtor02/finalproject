package com.tjoeun.project.product.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tjoeun.project.product.domain.CreateProductForm;
import com.tjoeun.project.product.domain.ProductVO;
import com.tjoeun.project.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/product")
@Controller
public class ProductController {

	
	
	private final ProductService productService;
	
	
	
	@GetMapping("/list")
	public String product(Model model , @RequestParam(value="page" , defaultValue="0") int page) {
		
		Page<ProductVO> productVO = this.productService.getList(page);
		
		model.addAttribute("paging" , productVO);
		
		
		return "/product/product_list";
	}
	
	
	
	
	
	
	
	
	@GetMapping("/detail/{id}")
	public String productDetail(Model model, @PathVariable("id") Long id ) {
		
		ProductVO productVO = this.productService.getProductVO(id);

		model.addAttribute("product" , productVO);
		
		return "/product/product_detail";
	}
	
	
	
	
	
	
	
	
	@GetMapping("/create")
	public String createProduct(CreateProductForm createProductForm) {
		
		
		
		return "/product/create_product_form";
	}
	
	
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String createProduct(@Valid CreateProductForm createProductForm,
								BindingResult bindingResult,
								Principal principal) {
		
		
		if(bindingResult.hasErrors()) {
			
			return "/product/create_product_form";
		}
		
		
		
		if(principal instanceof OAuth2AuthenticationToken) {
			
			String email = getEmailFromPrincipal(principal);
			
			this.productService.createProduct(createProductForm.getProductIMG()  ,createProductForm.getProductName(), createProductForm.getProductDetail(), createProductForm.getProductPrice(), null , email);
			
		} else if (principal instanceof UsernamePasswordAuthenticationToken) {
			
			
			String name = getUserIdFromPrincipal(principal);
				
			this.productService.createProduct(createProductForm.getProductIMG() , createProductForm.getProductName(), createProductForm.getProductDetail(), createProductForm.getProductPrice(), name , null);	
			
			
		}
		
		
		return "redirect:/product/list";
		
	}
	
	// Oauth2 로그인 사용자의 이메일 가져오기
	private String getEmailFromPrincipal(Principal principal) {
	    OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) principal;
	    return oauthToken.getPrincipal().getAttribute("email");
	}


	//UsernamePasswordAuthenticationToken 로그인 사용자의 ID 가져오기
	private String getUserIdFromPrincipal(Principal principal) {
	    UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;
	    return auth.getName();
	}
	
	
	
}
