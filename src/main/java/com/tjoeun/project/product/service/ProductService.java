package com.tjoeun.project.product.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tjoeun.project.board.exception.DataNotFoundException;
import com.tjoeun.project.domain.MemberRepository;
import com.tjoeun.project.domain.MemberVO;
import com.tjoeun.project.product.domain.ProductRepository;
import com.tjoeun.project.product.domain.ProductVO;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ProductService {

	
	
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;
	
	
	
	
	
	public List<ProductVO> getList() {
		
		return this.productRepository.findAll();
		
	}
	
	
	
	public Page<ProductVO> getList(int page) {
		
		// 게시판 작성글 역순정렬
		List<Sort.Order> sorts = new ArrayList<>();
		// 작성일기준 역정렬
		sorts.add(Sort.Order.desc("createDate"));
		

		// 페이지당 보여질 갯수설정								역정렬 삽입
		PageRequest pageable = PageRequest.of(page, 8, Sort.by(sorts));
		
		return this.productRepository.findAll(pageable);
	}
	
	
	
	
	
	
	
	public Page<ProductVO> getListMain(int page) {
		
		// 게시판 작성글 역순정렬
		List<Sort.Order> sorts = new ArrayList<>();
		// 작성일기준 역정렬
		sorts.add(Sort.Order.desc("createDate"));
		

		// 페이지당 보여질 갯수설정								역정렬 삽입
		PageRequest pageable = PageRequest.of(page, 3, Sort.by(sorts));
		
		return this.productRepository.findAll(pageable);
	}
	
	
	
	
	
	
	public ProductVO getProductVO(Long id) {
		
		
		Optional<ProductVO> productVO = this.productRepository.findById(id);
		
		if(productVO.isPresent()) {
			
			return productVO.get();
			
		} else {
			
			throw new DataNotFoundException("상품정보가 업습니다.");
			
		}
		
	}
	
	
	public void createProduct(String productIMG , String productName , String productDetail , String productPrice, String userId , String email ) {
		
		ProductVO productVO = new ProductVO();
		
		productVO.setProductIMG(productIMG);
		productVO.setProductName(productName);
		productVO.setProductDetail(productDetail);
		productVO.setProductPrice(productPrice);
		productVO.setCreateDate(LocalDateTime.now());
		
		
		MemberVO member = null;
		
//		if (userId != null) {
//			member = memberRepository.findMemberByUserIdOrEmail(userId, null);
//	    }
	    if (userId == null) {
            member = memberRepository.findByEmail(email).get();
         } 
		else if (email != null) {
	    	member = memberRepository.findMemberByUserIdOrEmail(null, email);
	    }

	    if (member == null) {
	        throw new DataNotFoundException("일반회원 또는 SNS회원이 아닙니다.");
	        
	    }

		productVO.setAuthor(member);
		
		
		productRepository.save(productVO);
	}


//	public void createProduct(String productName , String productDetail , String productPrice, String userId , String email ) {
//		
//		ProductVO productVO = new ProductVO();
//		
//		
//		productVO.setProductName(productName);
//		productVO.setProductDetail(productDetail);
//		productVO.setProductPrice(productPrice);
//		productVO.setCreateDate(LocalDateTime.now());
//		
//		
//		MemberVO member = null;
//		
//		if (userId != null) {
//			member = memberRepository.findMemberByUserIdOrEmail(userId, null);
//	        
//	        
//	    } else if (email != null) {
//	    	member = memberRepository.findMemberByUserIdOrEmail(null, email);
//	    }
//
//	    if (member == null) {
//	        throw new DataNotFoundException("일반회원 또는 SNS회원이 아닙니다.");
//	        
//	    }
//
//		productVO.setAuthor(member);
//		
//		
//		productRepository.save(productVO);
//	}


}
