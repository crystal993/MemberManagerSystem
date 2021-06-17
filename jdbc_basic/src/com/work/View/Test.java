package com.work.View;

import java.util.ArrayList;

import com.work.model.Member;
import com.work.model.MemberService;

public class Test {
	
	/*
	 * ##JDBC 관련 클래스 의존관계
	1. Test 
		=> MemberService#login(아이디, 비밀번호) : 반환값 - 등급
		=> MemberDao#login(아이디, 비밀번호) : 반환값 - 등급
	 */
	public static void main(String[] args) {
		MemberService service = new MemberService();
		
		print("로그인");
		String grade = service.login("user01", "password01");
		
		if(grade != null) {
			System.out.println("로그인 성공 등급 : " + grade);
		}
		else {
			System.out.println("로그인 실패 : 회원정보를 확인하시기 바랍니다.");
		}
		
		print("로그인");
		grade = service.login("user05", "password05");
		
		if(grade != null) {
			System.out.println("로그인 성공 등급 : " + grade);
		}
		else {
			System.out.println("로그인 실패 : 회원정보를 확인하시기 바랍니다.");
		}
		
		
		print("회원상세조회 : user01");
		Member dto = service.getMember("user01");
		if(dto != null) {
			System.out.println(dto);
		} else {
			System.out.println("조회 실패 : 회원정보가 존재하지 않습니다.");
		}
		
		print("회원전체조회");
		ArrayList<Member> list = service.getMemberList();
		for(Member member : list) {
			System.out.println(member);
		}
		
		print("등급별 회원 전체 조회 : G");
		list = service.getMemberList("G");
		for(Member member : list) {
			System.out.println(member);
		}
		
		print("등급별 회원 전체 조회 : S");
		list = service.getMemberList("S");
		for(Member member : list) {
			System.out.println(member);
		}
		
		print("등급별 회원 전체 조회 : A");
		list = service.getMemberList("A");
		for(Member member : list) {
			System.out.println(member);
		}
		
		print("아이디 찾기 - 휴대폰(010-1234-1112)");
		String member_id = service.findId("010-1234-1112");
		if(member_id != null) {
			System.out.println("[성공]회원님의 아이디는 " + member_id + " 입니다!");
		}
		else {
			System.out.println("조회 실패 : 회원정보가 존재하지 않거나 입력 정보가 올바르지 않습니다.");
		}
		

		print("비밀번호 찾기 - 아이디: user02 휴대폰(010-1234-1112)");
		String member_pw = service.findPw("user02","010-1234-1112");
		
		if(member_pw != null) {
			System.out.println("[성공]회원님의 비밀번호는 " + member_pw + " 입니다!");
		}
		else {
			System.out.println("조회 실패 : 회원정보가 존재하지 않거나 입력 정보가 올바르지 않습니다.");
		}
		
		print("[invalid]비밀번호 찾기 - 아이디: dsad 휴대폰(010-1234-1112)");
		member_pw = service.findPw("dsad","010-1234-1112");
		if(member_pw != null) {
			System.out.println("[성공]회원님의 비밀번호는 " + member_pw + " 입니다!");
		}
		else {
			System.out.println("조회 실패 : 회원정보가 존재하지 않거나 입력 정보가 올바르지 않습니다.");
		}
		
		
		print("아이디 중복체크 - user02");
		member_id = service.doubleCheckId("user02");
		if(member_id != null) {
			System.out.println("[아이디 중복] " + member_id + " 존재하는 아이디입니다.");
		}
		else {
			System.out.println("[중복 없음] 회원님은 "+ member_id + "를 사용할 수 있습니다.");
		}
		
		
		print("[invalid]아이디 중복체크 - crystal");
		String searchId = "crystal";
		member_id = service.doubleCheckId("searchId");
		if(member_id != null) {
			System.out.println("[아이디 중복] " + member_id + " 존재하는 아이디입니다.");
		}
		else {
			System.out.println("[중복 없음] 회원님은 "+ searchId + "를 사용할 수 있습니다.");
		}
		
		
		print(" 관리자(A)가 우수회원 자동등업대상자 조회 ");
		list = service.autoUpGrade("A");
			for(Member member : list) {
				System.out.println(member);
			}
		
		print("[invalid]일반회원(G) 우수회원 자동등업대상자 조회 ");
		list = service.autoUpGrade("G");
		if(list != null) {
			for(Member member : list) {
				
					System.out.println(member);
			}
		}
		
		
		
		print("이메일 중복체크 - user01@work.com ");
		String email = service.doubleCheckEmail("user01@work.com");
		if(email != null) {
			System.out.println("[이메일 중복] " + email + " 존재하는 이메일입니다.");
		}
		else {
			System.out.println("[중복 없음] 회원님은 "+ email + "를 사용할 수 있습니다.");
		}
		
		
		
		print("[invalid]이메일 중복체크 - ggg7152@gmail.com ");
			
			String searchEmail = "ggg7152@gmail.com";
			email = service.doubleCheckEmail(searchEmail);
		
		if(email != null) {
			System.out.println("[이메일 중복] " + email + " 존재하는 이메일입니다.");
		}
		else {
			System.out.println("[중복 없음] 회원님은 "+ searchEmail + " 를 사용할 수 있습니다.");
		}
		
		
		
		print("일정기간(1년 이상)된 회원의 목록 조회");
		list = service.getOneYearMember();
			if(list != null) {
				for(Member member : list) {
					System.out.println(member);
				}
			}
		
		String manager = "송중기";
		print(manager+"가 담당하는 회원의 아이디, 이름 목록 조회");	
			service.getManagingId(manager);
		
		manager = "김수정";	
		print("[invalid] "+manager+"가 담당하는 회원의 아이디, 이름 목록 조회");	
		service.getManagingId(manager);	
			
	}
	
	public static void print(String message) {
		System.out.println("\n###" + message);
	}
}
