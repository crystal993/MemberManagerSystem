package com.work.model;

import java.io.Serializable;

/**
 * DTO Pattern : Data Transfer Object
	=> 데이터를 가지고 n/w 통해서 이동하는 객체
	=> 테이블당 한개의 클래스로서 테이블의 구조참조
	=> 컬럼 매핑되도록 멤버변수 선언
	=> 설계규칙: encapsulation, 직렬화객체(implements java.io.Serializable)
	=> 자바 멤버변수 아이디 : String memberId 
	=> DB 컬럼명 아이디 : MEMBER_ID varchar_2
 * @author Playdata
 *
 */

/**
 * <pre>
 * 회원 DTO 클래스
 * </pre>
 * 
 * @author 김수정
 * @version ver.1.0
 * @since jdk1.8
 *
 */
public class Member implements Serializable {

	private String member_id;
	
	private String member_pw;
	
	private String name;
	
	private String mobile; 
	
	private String email;
	
	private String entryDate;
	
	private String Grade;
	
	private int mileage;
	
	private String manager;
	
	public Member() {
		// TODO Auto-generated constructor stub
	}

	
	
	public Member(String member_id, String member_pw, String name, String mobile, String email, String entryDate,
			String grade, int mileage, String manager) {
		super();
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.entryDate = entryDate;
		Grade = grade;
		this.mileage = mileage;
		this.manager = manager;
	}



	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_pw() {
		return member_pw;
	}

	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getGrade() {
		return Grade;
	}

	public void setGrade(String grade) {
		Grade = grade;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((member_id == null) ? 0 : member_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (member_id == null) {
			if (other.member_id != null)
				return false;
		} else if (!member_id.equals(other.member_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(member_id);
		builder.append(", ");
		builder.append(member_pw);
		builder.append(", ");
		builder.append(name);
		builder.append(", ");
		builder.append(mobile);
		builder.append(", ");
		builder.append(email);
		builder.append(", ");
		builder.append(entryDate);
		builder.append(", ");
		builder.append(Grade);
		builder.append(", ");
		builder.append(mileage);
		builder.append(", ");
		builder.append(manager);
		builder.append("");
		return builder.toString();
	}
	
	
	
}
