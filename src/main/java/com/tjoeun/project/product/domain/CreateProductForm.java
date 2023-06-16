package com.tjoeun.project.product.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CreateProductForm {

	
	private String productIMG;
	
	
	@NotEmpty(message = "상품이름을 입력하세요.")
	@Size(max=200)
	private String productName;
	

	
	@NotEmpty(message = "상품 상세설명을 입력하세요.")
	private String productDetail;

	@NotEmpty(message = "상품 가격을 입력해주세요")
	private String productPrice;
	
}
