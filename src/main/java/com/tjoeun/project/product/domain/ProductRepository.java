package com.tjoeun.project.product.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductVO , Long> {

	
	
	Optional<ProductVO> findById(Long id);
	
	
	
	// Page 객체사용  >> 서비스 >> 컨트롤러 순서로 작성
	Page<ProductVO> findAll(Pageable pageable);
	
	
	
	List<ProductVO> findAllByProductName(String productName);
	
	
}
