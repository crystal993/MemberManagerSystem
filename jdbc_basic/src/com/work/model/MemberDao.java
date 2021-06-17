package com.work.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * ## DAO Pattern
 * -- C
 * -- R
 * -- U
 * -- D
 * 
 * ## jdbc 프로그래밍 절차(순서) 
	1. jdbc driver 로딩 : 생성자에서 수행
		=> api보고 하기
		=> 객체를 생성해서 메모리에 올린다는 의미.
		
		생성자의 역할 1. 초기화 2. 서비스를 제공하기 전에 선행 처리 되어야할 것도 넣기
	
	2. db 서버연결 : url, user, password  => Connection
		
		
	3. 연결된 서버와 통로 개설 => Statement, PreparedStatement, CallableStatement
	4. 통로이용 sql 실행 요청
	5. 실행결과 처리
	6. 자원해제
	
 * @author Playdata
 *
 */
public class MemberDao {
	//여기서 db 연결을 하려고 함.
	
	
	// ## JDBC(자바와 db 연동) 관련 property : driver, url db벤더에서 결정
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	private String user = "scott";
	private String password = "tiger";
	
	/**
	 * 1. jdbc driver 로딩
	 * @throws ClassNotFoundException 
	 */
	public MemberDao()  {
		
		try {
			Class.forName(driver);
			System.out.println("[생성] 드라이버 로딩");
		} catch (ClassNotFoundException e) {
			System.out.println("[오류] 드라이버 로딩 오류");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 동적 PreparedStatement 로그인2 - 하드코딩 
	 * 
	 * 	2. db 서버연결 : url, user, password  => Connection 
	 * 		=>  DriverManager에 메소드 
	 * 
		3. 연결된 서버와 통로 개설 => Statement, PreparedStatement, CallableStatement
		4. 통로이용 sql 실행 요청
			// C U D => 레코드 추가, 변경, 삭제 => 수행결과 적용된 레코드 수 반환
			int rows = stmt.executeUpdate(sql);
		
			// R => 조회 => 결과 PK 컬럼이면 0이나 1개 => IF문 , 여러개(0~n) 반환 => WHILE문
			ResultSet rs = stmt.executeQuery(sql);

		5. 실행결과 처리
		6. 자원해제
		
	 * @param member_id 아이디
	 * @param member_pw 비밀번호 
	 * @return 회원 등급, 미존재시 null
	 */
	// MemberService#login(아이디, 비밀번호) : 반환값 - 등급
	public String login2(String member_id, String member_pw) {
		
		
		Connection conn;
		try {
			//2. db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			
			//3. 연결된 서버와 통로 개설
			// ★ 주의사항 : sql 구문 뒤에 ;(세미콜론)이 와서는 안된다. 쿼리문이 ; 으로 끝난것이므로 실행이 안될 것임.
			// error1 : String sql = "select Grade from member where member_ID = ? member_pw = ?;";
			// error2 :String sql = "select * from member where member_ID = '?'";
			String sql = "select Grade from member where member_ID = ? member_pw = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// 3. 주의사항 : ?에 매핑되는 값을 설정
			stmt.setString(1, member_id);
			stmt.setString(2, member_pw);
			
			//4. Statement 통로이용 sql 실행 요청 : 정적(하드코딩 - 무조건 설정해놓은거로 연결됨.), 로그인을 위한 SQL구문
			//String sql = "select Grade from member where member_ID ='user01' and member_pw = 'password01'";
			
			
			//4. 통로이용 sql 실행 요청
			// ★ 주의사항 : 실행시에 이미 전용통로로 개설되었으므로 sql 구문을 지정해서는 안된다.
			// ResultSet rs = stmt.executeQuery(sql); // error
			ResultSet rs = stmt.executeQuery();
			
			//5. 
			if(rs.next()) {
				String grade = rs.getString("grade");
				return grade;
			}
			
			//6. 
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			System.out.println("[오류] 로그인");
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	/**
	 * 정적 PreparedStatement 로그인1 - 
	 * 
	 * 	2. db 서버연결 : url, user, password  => Connection 
	 * 		=>  DriverManager에 메소드 
	 * 
		3. 연결된 서버와 통로 개설 => Statement, PreparedStatement, CallableStatement
		4. 통로이용 sql 실행 요청
			// C U D => 레코드 추가, 변경, 삭제 => 수행결과 적용된 레코드 수 반환
			int rows = stmt.executeUpdate(sql);
		
			// R => 조회 => 결과 PK 컬럼이면 0이나 1개 => IF문 , 여러개(0~n) 반환 => WHILE문
			ResultSet rs = stmt.executeQuery(sql);

		5. 실행결과 처리
		6. 자원해제
		
	 * @param member_id 아이디
	 * @param member_pw 비밀번호 
	 * @return 회원 등급, 미존재시 null
	 */
	// MemberService#login(아이디, 비밀번호) : 반환값 - 등급
	public String login(String member_id, String member_pw) {
		
		
		Connection conn;
		try {
			//2. db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			
			//3. 연결된 서버와 통로 개설
			Statement stmt = conn.createStatement();
			
			//4. Statement 통로이용 sql 실행 요청 : 정적(하드코딩 - 무조건 설정해놓은거로 연결됨.), 로그인을 위한 SQL구문
			//String sql = "select Grade from member where member_ID ='user01' and member_pw = 'password01'";
			String sql = "select Grade from member where member_ID = '" + member_id + "' and member_pw = '" + member_pw + "'";
			
			//4. 통로이용 sql 실행 요청
			ResultSet rs = stmt.executeQuery(sql);
			
			//5. 
			if(rs.next()) {
				String grade = rs.getString("grade");
				return grade;
			}
			
			//6. 
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			System.out.println("[오류] 로그인");
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	// ## 내정보조회/ 회원상세조회 
	//	=> MemberService#getMember(아이디) : 반환값 - 회원
	//	=> MemberDao#selectOne(아이디) : 회원
	public Member selectOne(String member_id) {
		
		
		
		Connection conn;
		try {
			//2. db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			
			// 회원상세/내정보조회
			String sql = "select * from member where member_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// 3. 주의사항 : ?에 매핑되는 값을 설정
			stmt.setString(1, member_id);
			
			
			//4. 통로이용 sql 실행 요청
			// ★ 주의사항 : 실행시에 이미 전용통로로 개설되었으므로 sql 구문을 지정해서는 안된다.
			// ResultSet rs = stmt.executeQuery(sql); // error
			ResultSet rs = stmt.executeQuery();
			
			//5. 
			if(rs.next()) {
				//String memberId = rs.getString("member_id");
				
				String memberPw = rs.getString("member_pw");
				String name = rs.getString("name");
				String mobile = rs.getString("mobile");
				String email = rs.getString("email");
				String entryDate = rs.getString("entrydate");
				String grade = rs.getString("grade");
				int mileage = rs.getInt("mileage");
				String manager = rs.getString("manager");

				// select 결과로 가져온 회원의 
				Member dto = new Member(member_id, memberPw, name, mobile, email, entryDate, grade, mileage,  manager);
				return dto;
			}
			
			//6. 
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			System.out.println("[오류] 로그인");
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
//	## 전체회원조회 
//	=> MemberService#getMemberList() : ArrayList<회원>
//	=> MemberDao#selectList() : ArrayList<회원> 
	public ArrayList<Member> getMemberList() {
		
		ArrayList<Member> members = new ArrayList<Member>();
		Connection conn;
		try {
			//2. db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			
			// 회원상세/내정보조회
			String sql = "select * from member";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// 3. 안해도됨. sql문에 ? 없음.
			//stmt.setString(1, member_id);
			
			
			//4. 통로이용 sql 실행 요청
			ResultSet rs = stmt.executeQuery();
			
			
			//5. 
			while(rs.next()) {
				
				String memberId = rs.getString("member_id");
				String memberPw = rs.getString("member_pw");
				String name = rs.getString("name");
				String mobile = rs.getString("mobile");
				String email = rs.getString("email");
				String entryDate = rs.getString("entrydate");
				String grade = rs.getString("grade");
				int mileage = rs.getInt("mileage");
				String manager = rs.getString("manager");

				// select 결과로 가져온 회원의 
				Member dto = new Member(memberId , memberPw, name, mobile, email, entryDate, grade, mileage,  manager);
				members.add(dto);
				
			} 
			
			//6. 
			rs.close();
			stmt.close();
			conn.close();
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 로그인");
			e.printStackTrace();
		}
		
		
		return members;
	}

	//	##회원등급별 전체조회 
	public ArrayList<Member> getMemberList(String grade) {
		
		ArrayList<Member> members = new ArrayList<Member>();
		Connection conn;
		try {
			//2. db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			
			// 회원상세/내정보조회
			String sql = "select * from member where grade=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// 3. grade해줘야함.
			stmt.setString(1, grade);
			
			
			//4. 통로이용 sql 실행 요청
			ResultSet rs = stmt.executeQuery();
			
			
			//5. 
			while(rs.next()) {
				
				String memberId = rs.getString("member_id");
				String memberPw = rs.getString("member_pw");
				String name = rs.getString("name");
				String mobile = rs.getString("mobile");
				String email = rs.getString("email");
				String entryDate = rs.getString("entrydate");
				int mileage = rs.getInt("mileage");
				String manager = rs.getString("manager");

				// select 결과로 가져온 회원의 
				Member dto = new Member(memberId , memberPw, name, mobile, email, entryDate, grade, mileage,  manager);
				members.add(dto);
				
			} 
			
			//6. 
			rs.close();
			stmt.close();
			conn.close();
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 로그인");
			e.printStackTrace();
		}
		
		
		return members;
	}


	
//	## 아이디 찾기 : 휴대폰
//	=> MemberService#findId(휴대폰) : 아이디
//	=> MemberDao#findId(휴대폰) : 아이디
	public String findId(String mobile) {
	
		Connection conn;
		try {
			//2. db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			
			// 아이디 찾기 : 휴대폰
			String sql = "select member_id from member where mobile=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// 3. grade해줘야함.
			stmt.setString(1, mobile);
			
			
			//4. 통로이용 sql 실행 요청
			ResultSet rs = stmt.executeQuery();
			
			
			//5. 
			while(rs.next()) {
				
				String memberId = rs.getString("member_id");
				return memberId;
				
			} 
			
			//6. 
			rs.close();
			stmt.close();
			conn.close();
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 아이디 찾기");
			e.printStackTrace();
		}
		
		
		return null;
	}

	
//	## 비밀번호 찾기 : 아이디, 휴대폰
//	=> MemberService#findPw(아이디, 휴대폰) : 비밀번호
//	=> MemberDao#findPw(아이디, 휴대폰) : 비밀번호		
	public String findPw(String member_id, String mobile) {
		Connection conn;
		try {
			//2. db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			

			String sql = "select member_pw from member where mobile=? and member_id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// 3.
			stmt.setString(1, mobile);
			stmt.setString(2, member_id);
			
			//4. 통로이용 sql 실행 요청
			ResultSet rs = stmt.executeQuery();
			
			
			//5. 
			while(rs.next()) {
				
				String memberPw = rs.getString("member_pw");
				return memberPw;
				
			} 
			
			//6. 
			rs.close();
			stmt.close();
			conn.close();
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 비밀번호 찾기");
			e.printStackTrace();
		}
		
		
		return null;
	}

//	## 아이디 중복체크 
//	=> MemberService#doubleCheckId(아이디) : 아이디
//	=> MemberDao#doubleCheckId(아이디) : 아이디	
	public String doubleCheckId(String member_id) {
		Connection conn;
		try {
			//2. db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			
			// 
			String sql = "select member_id from member where member_id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// 3.
			stmt.setString(1, member_id);
			
			//4. 통로이용 sql 실행 요청
			ResultSet rs = stmt.executeQuery();
			
			
			//5. 
			while(rs.next()) {
				
				String memberid = rs.getString("member_id");
				return memberid;
				
			} 
			
			//6. 
			rs.close();
			stmt.close();
			conn.close();
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 아이디가 존재하지 않습니다.");
			e.printStackTrace();
		}
		
		
		return null;
	}

//	## 관리자가 우수회원 자동등업대상자 조회 (주의사항 : 등급이 'A'가 아니면 에러처리)
//	=> MemberService#autoUpGrade(등급) : 회원
//	=> MemberDao#autoUpGrade(등급) : 회원	
	public ArrayList<Member> autoUpGrade(String grade) {
		ArrayList<Member> members = new ArrayList<Member>();
		Connection conn;
		
		if(grade.equals("A")) {
			try {
				//2. db 서버 연결
				conn = DriverManager.getConnection(url, user, password);
				
				// 
				String sql = "select * from member where mileage >= 5000";
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				
				//4. 통로이용 sql 실행 요청
				ResultSet rs = stmt.executeQuery();
				
				
				//5. 
				while(rs.next()) {
					
					String memberId = rs.getString("member_id");
					String memberPw = rs.getString("member_pw");
					String name = rs.getString("name");
					String mobile = rs.getString("mobile");
					String email = rs.getString("email");
					String grade2 = rs.getString("grade");
					int mileage = rs.getInt("mileage");
					String entryDate = rs.getString("entrydate");
					String manager = rs.getString("manager");
	
					// select 결과로 가져온 회원의 
					Member dto = new Member(memberId , memberPw, name, mobile, email, entryDate, grade2, mileage,  manager);
					members.add(dto);
					
				} 
				
				//6. 
				rs.close();
				stmt.close();
				conn.close();
				
				//return members;
				
			} catch (SQLException e) {
				System.out.println("[오류] 조회 오류");
				e.printStackTrace();
			}
			return members;
		}
		System.out.println("[오류] 접근 권한이 없습니다.");
		return null;
	}
	
//	## 이메일 중복 체크 
//	=> MemberService#doubleCheckEmail(이메일) : 이메일
//	=> MemberDao#doubleCheckEmail(이메일) : 이메일
	public String doubleCheckEmail(String email) {
		Connection conn;
		try {
			//2. db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			
			// 
			String sql = "select email from member where email=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// 3.
			stmt.setString(1, email);
			
			//4. 통로이용 sql 실행 요청
			ResultSet rs = stmt.executeQuery();
			
			
			//5. 
			if(rs.next()) {
				
				email = rs.getString("email");
				return email;
				
			} 
			
			//6. 
			rs.close();
			stmt.close();
			conn.close();
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 이메일이 존재하지 않습니다.");
			e.printStackTrace();
		}
		
		
		return null;
	}

//	## 일정기간 1년이상된 회원 목록 조회 
//	=> MemberService#getOneYearMember(가입일) : 회원
//	=> MemberDao#getOneYearMember(가입일) : 회원	
	public ArrayList<Member> getOneYearMember() {
		ArrayList<Member> members = new ArrayList<Member>();
		Connection conn;
		
			try {
				//2. db 서버 연결
				conn = DriverManager.getConnection(url, user, password);
				
				// 
				String sql = "select * from member where (months_between(sysdate,entrydate)/12) >= 1";
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				
				//4. 통로이용 sql 실행 요청
				ResultSet rs = stmt.executeQuery();
				
				
				//5. 
				while(rs.next()) {
					
					String memberId = rs.getString("member_id");
					String memberPw = rs.getString("member_pw");
					String name = rs.getString("name");
					String mobile = rs.getString("mobile");
					String email = rs.getString("email");
					String getEntryDate = rs.getString("entrydate");
					String grade = rs.getString("grade");
					int mileage = rs.getInt("mileage");
					String manager = rs.getString("manager");
					
					
					// select 결과로 가져온 회원의 
					Member dto = new Member(memberId , memberPw, name, mobile, email, getEntryDate, grade, mileage,  manager);
					members.add(dto);
					
				} 
				
				//6. 
				rs.close();
				stmt.close();
				conn.close();
				
				//return members;
				
			} catch (SQLException e) {
				System.out.println("[오류] 조회 오류");
				e.printStackTrace();
			}
			return members;
	
	}

//	## 담당자가 담당하는 회원의 아이디, 이름 목록 조회
//	=> MemberService#getManagingId(담당자) : 아이디,이름
//	=> MemberDao#getManagingId(담당자) : 아이디,이름
	public void getManagingId(String manager) {
		Connection conn;
		
		
		try {
			//2. db 서버 연결
			conn = DriverManager.getConnection(url, user, password);
			
			// 
			String sql = "select member_id, name from member where manager=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// 3.
			stmt.setString(1, manager);
			
			//4. 통로이용 sql 실행 요청
			ResultSet rs = stmt.executeQuery();
			
			
			//5. 
			while(rs.next()) {
				
				String member_id = rs.getString("member_id");
				String name = rs.getString("name");
				
				System.out.print("담당자가 담당하는 회원의 아이디 : "+member_id);
				System.out.println(", 회원 이름 : "+name);
				
				if(member_id.isEmpty()) {
					System.out.println("담당 회원이 없습니다.");
				}
			} 
			
			//6. 
			rs.close();
			stmt.close();
			conn.close();
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 담당자가 존재하지 않습니다.");
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
}
