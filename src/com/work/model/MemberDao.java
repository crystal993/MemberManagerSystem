package com.work.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.work.util.Utility;

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

	Utility util = new Utility();
	
	private FactoryDao factory = FactoryDao.getInstance();
	
	// 3. private static 클래스이름 instance = new 클래스이름();
	private static MemberDao instance = new MemberDao();
	
	/**
	 * singleton pattern : 객체 생성 불가 제한
	 */
	private MemberDao()  { // 1. private 생성자

	
	}
	
	//2. 인스턴스 반환 메서드
	public static MemberDao getInstance() {
		return instance;
	}
	
	/**
	 * 동적 PreparedStatement 로그인2 - 하드코딩 
	 * @param member_id 아이디
	 * @param member_pw 비밀번호 
	 * @return 회원 등급, 미존재시 null
	 */
	public String login2(String member_id, String member_pw) {
		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			//2. db 서버 연결 : factory 공장에세 Connection 가져오기
//			conn = DriverManager.getConnection(url, user, password);
			conn = factory.getConnection();
			
			//3. 연결된 서버와 통로 개설
			String sql = "select Grade from member where member_ID = ? member_pw = ?";
			stmt = conn.prepareStatement(sql);
			
			// 3. 주의사항 : ?에 매핑되는 값을 설정
			stmt.setString(1, member_id);
			stmt.setString(2, member_pw);
			
			//4. 통로이용 sql 실행 요청
			rs = stmt.executeQuery();
			
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
		} finally {
			
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt, rs);
			
//			try {
//				if(rs != null) // 오류가 발생할 때 nullPointerException 예방 가능
//				rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			try {
//				if(stmt != null)
//				stmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			try {
//				if(conn != null)
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		
		
		return null;
	}
	
	
	/**
	 * 정적 PreparedStatement 로그인1 - 
	 * @param member_id 아이디
	 * @param member_pw 비밀번호 
	 * @return 회원 등급, 미존재시 null
	 * @throws SQLException 
	 */
	public String login(String member_id, String member_pw) {
		
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		try {
			//2. db 서버 연결
			conn = factory.getConnection();
			//conn = DriverManager.getConnection(url, user, password); //db연결시에 예외 발생
			
			//3. 연결된 서버와 통로 개설
			 stmt = conn.createStatement();
			
			//4. Statement 통로이용 sql 실행 요청 
			String sql = "select Grade from member where member_ID = '" + member_id + "' and member_pw = '" + member_pw + "'";
			
			//4. 통로이용 sql 실행 요청
			rs = stmt.executeQuery(sql);
			
			//5. 
			if(rs.next()) {
				String grade = rs.getString("grade");
				return grade;
			}
			
			
			
		} catch (SQLException e) {
			System.out.println("[오류] 로그인");
			e.printStackTrace();
		} finally {
			
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt, rs);
		}
		
		
		return null;
	}
	
	/**
	 * 내정보조회/ 회원상세조회 
	 * @param member_id 아이디
	 * @return 회원
	 */
	public Member selectOne(String member_id) {
		
		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			//2. db 서버 연결
			conn = factory.getConnection();

			
			// 회원상세/내정보조회
			String sql = "select * from member where member_ID = ?";
			 stmt = conn.prepareStatement(sql);
			
			// 3. 주의사항 : ?에 매핑되는 값을 설정
			stmt.setString(1, member_id);
			
			
			//4. 통로이용 sql 실행 요청
			 rs = stmt.executeQuery();
			
			//5. 
			if(rs.next()) {
				
				String memberPw = rs.getString("member_pw");
				String name = rs.getString("name");
				String mobile = rs.getString("mobile");
				String email = rs.getString("email");
				String entryDate = rs.getString("entrydate");
				String grade = rs.getString("grade");
				int mileage = rs.getInt("mileage");
				String manager = rs.getString("manager");

				Member dto = new Member(member_id, memberPw, name, mobile, email, entryDate, grade, mileage,  manager);
				return dto;
			}
			
			
		} catch (SQLException e) {
			System.out.println("[오류] 로그인");
			e.printStackTrace();
		} finally {
			
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt, rs);
		}
		
		
		return null;
	}
	
	
	/**
	 * 전체 회원 조회
	 * @return 전체 회원 ArrayList<Member>
	 */
	public ArrayList<Member> getMemberList() {
		
		ArrayList<Member> members = new ArrayList<Member>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			//2. db 서버 연결
			conn = factory.getConnection();

			
			String sql = "select * from member";
			stmt = conn.prepareStatement(sql);
			
			// 3. 안해도됨. sql문에 ? 없음.
			//stmt.setString(1, member_id);
			
			
			//4. 통로이용 sql 실행 요청
			 rs = stmt.executeQuery();
			
			
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
			
			
		} catch (SQLException e) {
			System.out.println("[오류] 로그인");
			e.printStackTrace();
		} finally {
			
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt, rs);
		}
		
		return members;
	}

	/**
	 * 회원등급별 전체 조회
	 * @param grade 등급
	 * @return 등급별 회원
	 */
	public ArrayList<Member> getMemberList(String grade) {
		
		ArrayList<Member> members = new ArrayList<Member>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			//2. db 서버 연결
			conn = factory.getConnection();

			
			// 회원상세/내정보조회
			String sql = "select * from member where grade=?";
			 stmt = conn.prepareStatement(sql);
			
			// 3. grade해줘야함.
			stmt.setString(1, grade);
			
			
			//4. 통로이용 sql 실행 요청
			 rs = stmt.executeQuery();
			
			
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
			

			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 로그인");
			e.printStackTrace();
		} finally {
			
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt, rs);
		}
		
		
		return members;
	}


	/**
	 * 아이디찾기 - 휴대폰
	 * @param mobile 휴대폰
	 * @return 아이디
	 */
	public String findId(String mobile) {
	
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			//2. db 서버 연결
			conn = factory.getConnection();

			
			// 아이디 찾기 : 휴대폰
			String sql = "select member_id from member where mobile=?";
			 stmt = conn.prepareStatement(sql);
			
			// 3. 
			stmt.setString(1, mobile);
			
			
			//4. 통로이용 sql 실행 요청
			 rs = stmt.executeQuery();
			
			
			//5. 
			while(rs.next()) {
				
				String memberId = rs.getString("member_id");
				return memberId;
				
			} 
			
		
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 아이디 찾기");
			e.printStackTrace();
		} finally {
			
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt, rs);
		}
		
		
		return null;
	}

	
	/**
	 * 비밀번호 찾기 : 아이디, 휴대폰
	 * @param member_id 아이디
	 * @param name 이름
	 * @param mobile 휴대폰
	 * @return memberPw 가져오면 1 반환, 아니면 0 반환
	 */
	public boolean findMemerPwByMobile(String member_id, String name, String mobile) {
		//1. 
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
				try {
					//2. db 서버 연결
					conn = factory.getConnection();

					

					String sql = "select member_pw from member where mobile=? and name=? and member_id=?";
					 stmt = conn.prepareStatement(sql);
					
					// 3.
					stmt.setString(1, mobile);
					stmt.setString(2, name);
					stmt.setString(3, member_id);
					
					//4. 통로이용 sql 실행 요청
					rs = stmt.executeQuery();
					
					
					//5. 
					while(rs.next()) {
						
						String memberPw = rs.getString("member_pw");
						return true;
						
					} 
					
					
					//return members;
					
				} catch (SQLException e) {
					System.out.println("[오류] 비밀번호 찾기");
					e.printStackTrace();
				} finally {
					
					//6. 자원해제 : finally 구문으로 변경 수정
					// 공장에게 위임
					factory.close(conn, stmt, rs);
				}
				
				
				return false;
	}
	
	
	
	/**
	 * 비밀번호 찾기 02 - 이메일
	 * @param member_id 아이디
	 * @param name 이름
	 * @param email 이메일 
	 * @return memberPw 가져오면 1 반환, 아니면 0 반환	
	 */
	public boolean findMemerPwByEmail(String member_id, String name, String email) {
		//1. 
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			//2. db 서버 연결
			conn = factory.getConnection();

			

			String sql = "select member_pw from member where email=? and name=? and member_id=?";
			stmt = conn.prepareStatement(sql);
			
			// 3.
			stmt.setString(1, email);
			stmt.setString(2, name);
			stmt.setString(3, member_id);
			
			//4. 통로이용 sql 실행 요청
			rs = stmt.executeQuery();
			
			
			//5. 
			while(rs.next()) {
				
				String memberPw = rs.getString("member_pw");
				return true;
				
			} 
			
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 비밀번호 찾기");
			e.printStackTrace();
		} finally {
			
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt, rs);
		}
		
		
		return false;
	}
	
	
	/**
	 * 비밀번호 변경 
	 * @param memberId 아이디 
	 * @param memberNewPw 새로운 비밀번호 
	 * @return 비밀번호 db에서 반환시 1, 아니면 0
	 */
	public boolean updateMemberPw(String memberId, String memberNewPw) {
		//1. 
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			//2. db 서버 연결
			conn = factory.getConnection();

			

			String sql = "update member set member_pw = ? where member_id=? ";
			stmt = conn.prepareStatement(sql);
			
			// 3.
			stmt.setString(1, memberNewPw);
			stmt.setString(2, memberId);

			
			//4. 통로이용 sql 실행 요청 - sql구문이 Read - Select일 경우에 사용
			// ResultSet rs = stmt.executeQuery();
			
			//4. 통로이용 sql 실행 요청 - sql구문이 C, U, D - insert, update, delete일 때 사용.			
			
			// 업데이트 되면 : 1 , 업데이트 되지 않으면 : 0이 되는데 
			// 업데이트된 행이 1개 이상이면 업데이트가 된것이므로 
			// if(rows > 0) 을 써줍니다.
			
			int rows = stmt.executeUpdate();
			if(rows > 0) {
				
				System.out.println(memberNewPw);
				return true;
			}
			
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 비밀번호(임시발급) 변경");
			e.printStackTrace();
		} finally {
			
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt);
		}
		return false;
	}


	/**
	 * 비밀번호 변경 
	 * @param memberId 아이디 
	 * @param memberPw 비밀번호
	 * @param memberNewPw 새로운 비밀번호
	 * @return
	 */
	public int updateMemberPw(String memberId,String memberPw, String memberNewPw) {
		//1. 
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			//2. db 서버 연결
			conn = factory.getConnection();

			String sql = "update member set member_pw = ? where member_id=? and member_pw=?";
			stmt = conn.prepareStatement(sql);
			
			// 3.
			stmt.setString(1, memberNewPw);
			stmt.setString(2, memberId);
			stmt.setString(3, memberPw);
			
			//4. 통로이용 sql 실행 요청 - sql구문이 Read - Select일 경우에 사용
//			ResultSet rs = stmt.executeQuery();
			
			//4. 통로이용 sql 실행 요청 - sql구문이 C, U, D - insert, update, delete일 때 사용.
			// 반환 타입이 int이므로 업데이트되면 1행이 추가된 것이므로 1을 반환하게 되어있고, 아니면 0을 반환함 
			// 밑에 처럼 바로 쓸 수 있다.
			return stmt.executeUpdate();
			
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 비밀번호(임시발급) 변경");
			e.printStackTrace();
		} finally {
			
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt);
		}
		return 0;
	}
	

	/**
	 * 아이디 중복 체크
	 * @param member_id 아이디
	 * @return 아이디 db에서 반환시 1, 아니면 0
	 */
	public String doubleCheckId(String member_id) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			//2. db 서버 연결
			conn = factory.getConnection();

			
			// 
			String sql = "select member_id from member where member_id=?";
			 stmt = conn.prepareStatement(sql);
			
			// 3.
			stmt.setString(1, member_id);
			
			//4. 통로이용 sql 실행 요청
			rs = stmt.executeQuery();
			
			
			//5. 
			while(rs.next()) {
				
				String memberid = rs.getString("member_id");
				return memberid;
				
			} 
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 아이디가 존재하지 않습니다.");
			e.printStackTrace();
		} finally {
			
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt, rs);
		}
		
		
		return null;
	}

	
	
	/**
	 * 관리자가 우수회원 자동등업대상자 조회
	 * @param grade 등급
	 * @return 등급에 맞는 회원 조회
	 */
	public ArrayList<Member> autoUpGrade(String grade) {
		ArrayList<Member> members = new ArrayList<Member>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		
		if(grade.equals("A")) {
			try {
				//2. db 서버 연결
				conn = factory.getConnection();

				
				// 
				String sql = "select * from member where mileage >= 5000";
				stmt = conn.prepareStatement(sql);
				
				
				//4. 통로이용 sql 실행 요청
				rs = stmt.executeQuery();
				
				
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
	
					Member dto = new Member(memberId , memberPw, name, mobile, email, entryDate, grade2, mileage,  manager);
					members.add(dto);
					
				} 
				
				return members;
				
			} catch (SQLException e) {
				System.out.println("[오류] 조회 오류");
				e.printStackTrace();
			} finally {
				
				//6. 자원해제 : finally 구문으로 변경 수정
				// 공장에게 위임
				factory.close(conn, stmt, rs);
			}
		}
		System.out.println("[오류] 접근 권한이 없습니다.");
		return null;
	}
	
	/**
	 * 이메일 중복 체크
	 * @param email 이메일
	 * @return 이메일
	 */
	public String doubleCheckEmail(String email) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			//2. db 서버 연결
			conn = factory.getConnection();

			
			// 
			String sql = "select email from member where email=?";
			stmt = conn.prepareStatement(sql);
			
			// 3.
			stmt.setString(1, email);
			
			//4. 통로이용 sql 실행 요청
			 rs = stmt.executeQuery();
			
			
			//5. 
			if(rs.next()) {
				
				email = rs.getString("email");
				return email;
				
			} 
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 이메일이 존재하지 않습니다.");
			e.printStackTrace();
		} finally {
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt, rs);
		}
		
		
		return null;
	}


	/**
	 * 일정기간 1년 이상된 회원 목록 조회
	 * @return 회원
	 */
	public ArrayList<Member> getOneYearMember() {
		ArrayList<Member> members = new ArrayList<Member>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
			try {
				//2. db 서버 연결
				conn = factory.getConnection();

				
				// 
				String sql = "select * from member where (months_between(sysdate,entrydate)/12) >= 1";
				stmt = conn.prepareStatement(sql);
				
				
				//4. 통로이용 sql 실행 요청
				rs = stmt.executeQuery();
				
				
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
				
				//return members;
				
			} catch (SQLException e) {
				System.out.println("[오류] 조회 오류");
				e.printStackTrace();
			} finally {
				//6. 자원해제 : finally 구문으로 변경 수정
				// 공장에게 위임
				factory.close(conn, stmt, rs);
			}
			return members;
	
	}


	/**
	 * 담당자가 담당하는 회원의 아이디, 이름 목록 조회
	 * @param manager 담당자
	 */
	public boolean getManagingId(String manager) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		String sql = null;
		try {
			//2. db 서버 연결
			conn = factory.getConnection();
			
			// 2. 존재하면 1을 반환하도록 하는 구문
			sql = "select member_id, name from member where manager = ?";
			stmt = conn.prepareStatement(sql);
			
			// 3.
			stmt.setString(1, manager);
			
			//4. 통로이용 sql 실행 요청
			rs = stmt.executeQuery();
			
			
			//5. 
			while(rs.next()) {
				
				String member_id = rs.getString("member_id");
				String name = rs.getString("name");
				
				System.out.print("담당자가 담당하는 회원의 아이디 : "+member_id);
				System.out.println(", 회원 이름 : "+name);
				
				return  true;
			} 
			
			
			//return members;
			
		} catch (SQLException e) {
			System.out.println("[오류] 담당자가 존재하지 않습니다.");
			e.printStackTrace();
		} finally {
			//6. 자원해제 : finally 구문으로 변경 수정
			// 공장에게 위임
			factory.close(conn, stmt, rs);
		}
		
		return  false;
	}


	
	
	
	
	
}
