package com.tjoeun.project.domain;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tjoeun.project.board.questionDomain.BoardQuestionVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Table(name="member_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Data  // getter setter tostring  포함
@Entity
public class MemberVO  extends BaseTimeEntity {

	// Oracle11g 에서는 strategy = GenerationType.IDENTITY 를 사용할수없다.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="user_id")
	private String userId;
	
	@NonNull
	private String pw;
	
	private String name;
	
	private char gender;

	@Column(unique = true)
	private String email;
	
	//  "010-1234-5678"의 경우, "01012345678"과 같이 "-"를 제외한 숫자만 추출해서 int로 저장할 수 있습니다. 
	//  하지만 이 방법은 번거로울 뿐 아니라, 전화번호의 형식이 바뀔 경우에도 코드를 수정해야 하는 번거로움이 있습니다. 
	//  따라서, String으로 유지하는 것이 좋습니다.
	private String mobile;
	
	private String address1;
	
	private String address2;
	
	private Date birthday;
	
	private Date joindate;

	@Column
	private String picture;
	
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private MemberVORole role;

	
	@Column
	private String authVendor; // 추가(소셜로그인 이름 가저오는용도)
	
	
	@Builder
	public MemberVO(String name , String email , String picture , MemberVORole role) {
		
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.role = role;
		
		
	}
	
	
	public MemberVO update(String name , String picture) {
		this.name = name;
		this.picture = picture;
		
		return this;
		
	}
	
	public String getRoleKey() {
		
		return this.role.getKey();
		
	}
	
	
	@OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
	private List<BoardQuestionVO> questionList;

    // 생성자, getter, setter, 기타 메서드들...

    public List<BoardQuestionVO> getQuestionList() {
        if (questionList == null) {
            questionList = new ArrayList<>();
        }
        return questionList;
	
    }
	
    public void setGender(Character inputGender) {
        // 값이 null이거나 공백일 때 기본값 입력
        if (inputGender == null || inputGender == ' ') {
            this.gender = 'M'; // 'M'은 기본값으로 지정할 값입니다.
        } else {
            this.gender = inputGender;
        }
    }
	
	
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberVO other = (MemberVO) obj;
		return Objects.equals(address1, other.address1) && Objects.equals(address2, other.address2)
				&& Objects.equals(birthday, other.birthday) && Objects.equals(email, other.email)
				&& gender == other.gender && Objects.equals(id, other.id) && Objects.equals(joindate, other.joindate)
				&& Objects.equals(mobile, other.mobile) && Objects.equals(name, other.name)
				&& Objects.equals(pw, other.pw) && role == other.role && Objects.equals(userId, other.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address1, address2, birthday, email, gender, id, joindate, mobile, name, pw, role, userId);
	}


	
	
	
	
	
	

	
	
}
