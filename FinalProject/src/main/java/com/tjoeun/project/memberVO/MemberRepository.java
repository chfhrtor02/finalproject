<<<<<<< HEAD
package com.tjoeun.project.memberVO;


import org.springframework.data.jpa.repository.JpaRepository;

// javateacher
// @Repository
public interface MemberRepository extends JpaRepository<MemberVO , String> {

	boolean findByIdAndPw(String id, String pw);



	
	
=======
package com.tjoeun.project.memberVO;


import org.springframework.data.jpa.repository.JpaRepository;

// javateacher
// @Repository
public interface MemberRepository extends JpaRepository<MemberVO , String> {

	boolean findByIdAndPw(String id, String pw);



	
	
>>>>>>> origin/master
}