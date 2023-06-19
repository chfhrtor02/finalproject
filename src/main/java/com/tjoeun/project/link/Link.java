package com.tjoeun.project.link;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tjoeun.project.domain.MemberVORole;
import com.tjoeun.project.link.OAuthAttributes2.OAuthAttributes2Builder;

import lombok.Builder;

@Entity
@Table(name = "link_tbl") // 뷰 이름으로 변경
public class Link {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_generator")
	    private Long id;
	 	
	 	@Column
	 	private String name;
	 	
	 	@Column
	    private String authVendor;
	    
	    @Column
	    private String email;
	    
	    @Column
	    private MemberVORole role;
	    
	    @Builder
	    public Link(String name,String email, String authVendor,MemberVORole role) {
	    	this.name = name;
	    	this.authVendor = authVendor;
	    	this.email = email;
	    	this.role = role;
	    }
	    
//	    @Builder
//	    public Sns update(String name) {
//	    	this.name = name;
//	    	
//	    	return this;
//	    }

}
