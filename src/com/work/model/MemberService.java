package com.work.model;

import java.sql.SQLException;
import java.util.ArrayList;

import com.work.util.Utility;


/**
 * <pre>
 * -- 회원관리시스템 서비스 클래스
 * </pre>
 * 
 * @author 김수정
 * @version ver.1.0
 * @since jdk1.8
 *
 */
public class MemberService {
	
	Utility util = new Utility();
	// MemberDao 객체 생성 
	
	//4. 사용 :Singleton Pattern 클래스 사용
	// Member DAO는 반드시 SINGLETON PATTERN으로
	//클래스이름 instance = 클래스이름.getInstance();
	private MemberDao dao = MemberDao.getInstance();
	
	/**
	 * 로그인
	 * @param member_id 아이디
	 * @param member_pw 비밀번호 
	 * @return 회원 등급, 미존재시 null
	 * @throws SQLException 
	 */
	public String login(String member_id, String member_pw) throws SQLException {
		String grade = dao.login(member_id, member_pw);
		if(grade != null) {
			return grade;
		}
		return null;
	}
	
	/**
	 * 회원 상세 조회 
	 * @param member_id 아이디
	 * @return 회원정보, 미존재시 null
	 */
	public Member getMember(String member_id) {
		return dao.selectOne(member_id);
	}
	
	
	/**
	 * 전체 회원 조회
	 * @return 전체 회원 ArrayList<Member>
	 */
	public ArrayList<Member> getMemberList() {
		return dao.getMemberList();
	}
	
	
	/**
	 * 등급별 조회
	 * @param grade 등급
	 * @return 회원 반환
	 */
	public ArrayList<Member> getMemberList(String grade) {
		return dao.getMemberList(grade);
	}

	/**
	 * 아이디 찾기
	 * @param mobile 휴대폰
	 * @return 아이디
	 */
	public String findId(String mobile) {
		return dao.findId(mobile);
	}
	
	
	/**
	 * 비밀번호 찾기1 - 휴대폰
	 * @param member_id 아이디 
	 * @param name 이름 
	 * @param mobile 휴대폰
	 * @return 
	 */
	public String findMemerPwByMobile(String member_id, String name, String mobile) {
		String tmpMemberPw;
		// 1. dao 해당 회원 존재여부 체킹 
		boolean isExist = dao.findMemerPwByMobile(member_id, name, mobile);

		//2.
		if (isExist) {
			tmpMemberPw = util.getSecureAlphabetString(15, true, false);
			dao.updateMemberPw(tmpMemberPw, member_id);
			return tmpMemberPw;
		}
	return null;

	}
	
	/**
	 * 비밀번호 찾기2 - 이메일
	 * 1 단계 : 비밀번호 찾기 : dao 해당 회원 존재 유무 체킹 (코드구현)
	 * 
	 * 2 단계 : 
		if (존재여부체킹) {
			2. 임시암호 발급 : Java Utility 구현
			3. dao(아이디[, 이름, 이메일,] 변경암호) 변경 요청
		}
	 * @param member_id
	 * @param name
	 * @param mobile
	 * @return
	 */
	public String findMemerPwByEmail(String member_id, String name, String email) {
		String tmpMemberPw;
		// 1. dao 해당 회원 존재여부 체킹 
		boolean isExist = dao.findMemerPwByEmail(member_id, name, email);

		//2.
		if (isExist) {
			tmpMemberPw = util.getSecureAlphabetString(15, true, false);
			dao.updateMemberPw(tmpMemberPw, member_id);
			return tmpMemberPw;
		}
	return null;	
	}
	
	/**
	 * 비밀번호 변경
	 * @param memberId 아이디 
	 * @param memberNewPw 변경 비밀번호
	 * @return
	 */
	public int updateMemberPw(String memberId, String memberPw, String memberNewPw) {
		
		return dao.updateMemberPw(memberId,memberPw, memberNewPw);
	}

	
	/**
	 * 아이디 중복 체크
	 * @param member_id 아이디
	 * @return 아이디
	 */
	public String doubleCheckId(String member_id){
		return dao.doubleCheckId(member_id);
	}
	
	
	/**
	 * 관리자가 우수회원 자동등업 대상자 조회 
	 * (주의사항 : 등급이 'A'만 접근 가능하도록)
	 * @param grade 등급
	 * @return 회원
	 */
	public ArrayList<Member> autoUpGrade(String grade) {
		return dao.autoUpGrade(grade);
	}
	
	/**
	 * 이메일 중복 체크
	 * @param email 이메일
	 * @return 이메일
	 */
	public String doubleCheckEmail(String email){
		return dao.doubleCheckEmail(email);
	}
	
	
	/**
	 * 일정기간 1년 이상된 회원 목록 조회 
	 * @return 회원
	 */
	public ArrayList<Member> getOneYearMember() {
		return dao.getOneYearMember();
	}
	
	/**
	 * 담당자가 담당하는 회원의 아이디, 이름 목록 조회
	 * @param manager
	 */
	public boolean getManagingId(String manager) {
		 return dao.getManagingId(manager);
	}
	
}
