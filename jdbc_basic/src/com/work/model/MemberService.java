package com.work.model;

import java.util.ArrayList;

public class MemberService {
	
	// MemberDao 객체 생성 
	private MemberDao dao = new MemberDao();
	
	/**
	 * 로그인
	 * @param member_id 아이디
	 * @param member_pw 비밀번호 
	 * @return 회원 등급, 미존재시 null
	 */
	// MemberService#login(아이디, 비밀번호) : 반환값 - 등급
	public String login(String member_id, String member_pw) {
		String grade = dao.login(member_id, member_pw);
		if(grade != null) {
			return grade;
		}
		return null;
	}
	
	/**
	 * 회원 조회 
	 * @param member_id 아이디
	 * @return 회원정보, 미존재시 null
	 */
	// ## 내정보조회/ 회원상세조회 
	//	=> MemberService#getMember(아이디) : 반환값 - 회원
	//	=> MemberDao#selectOne(아이디) : 회원
	public Member getMember(String member_id) {
		return dao.selectOne(member_id);
	}
	
	
//	## 전체회원조회 
//	=> MemberService#getMemberList() : ArrayList<회원>
//	=> MemberDao#selectList() : ArrayList<회원> 
	public ArrayList<Member> getMemberList() {
		return dao.getMemberList();
	}
	
	
//	## 등급별 조회
//	=> MemberService#getMemberList(등급) : ArrayList<회원>
//	=> MemberDao#selectList(등급) : ArrayList<회원>  
	public ArrayList<Member> getMemberList(String grade) {
		return dao.getMemberList(grade);
	}

//	## 아이디 찾기 : 휴대폰
//	=> MemberService#findId(휴대폰) : 아이디
//	=> MemberDao#findId(휴대폰) : 아이디	
	public String findId(String mobile) {
		return dao.findId(mobile);
	}
	
//	## 비밀번호 찾기 : 아이디, 휴대폰
//	=> MemberService#findPw(아이디, 휴대폰) : 비밀번호
//	=> MemberDao#findPw(아이디, 휴대폰) : 비밀번호	
	public String findPw(String member_id, String mobile) {
		return dao.findPw(member_id, mobile);
	}
	
//	## 아이디 중복체크 
//	=> MemberService#doubleCheckId(아이디) : 아이디
//	=> MemberDao#doubleCheckId(아이디) : 아이디	
	public String doubleCheckId(String member_id){
		return dao.doubleCheckId(member_id);
	}
	
//	## 관리자가 우수회원 자동등업대상자 조회 (주의사항 : 등급이 'A'가 아니면 에러처리)
//	=> MemberService#autoUpGrade(등급) : 회원
//	=> MemberDao#autoUpGrade(등급) : 회원
	public ArrayList<Member> autoUpGrade(String grade) {
		return dao.autoUpGrade(grade);
	}
	
//	## 이메일 중복 체크 
//	=> MemberService#doubleCheckEmail(이메일) : 이메일
//	=> MemberDao#doubleCheckEmail(이메일) : 이메일
	public String doubleCheckEmail(String email){
		return dao.doubleCheckEmail(email);
	}
	
	
//	## 일정기간 1년이상된 회원 목록 조회 
//	=> MemberService#getOneYearMember() : 회원
//	=> MemberDao#getOneYearMember() : 회원
	public ArrayList<Member> getOneYearMember() {
		return dao.getOneYearMember();
	}
	
//	## 담당자가 담당하는 회원의 아이디, 이름 목록 조회
//	=> MemberService#getManagingId(담당자) : 아이디
//	=> MemberDao#getManagingId(담당자) : 아이디
	public void getManagingId(String manager) {
		 dao.getManagingId(manager);
	}
	
}
