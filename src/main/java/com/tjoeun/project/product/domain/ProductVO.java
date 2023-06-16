package com.tjoeun.project.product.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tjoeun.project.domain.MemberVO;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="product")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class ProductVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	
	private String productName;
	
	
	@Column(columnDefinition = "CLOB")
	private String productDetail;
	
	
	private LocalDateTime createDate;
	
	
	private String productPrice;
	
	
	private String productIMG;
	
	@ManyToOne
	private MemberVO author;
	
	
	
	
}
