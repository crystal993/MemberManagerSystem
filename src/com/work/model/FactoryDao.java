package com.work.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 모든 dao 클래스에서 사용하기 위한
 * -- Connection 반환
 * -- close() 자원해제를 담당하는 기능으로만 분리설계
 * 
 * -- 모든 dao 클래스에서 getConnection, close(conn, stmt, rs) : 호출 사용
 * 
 * -- singleton pattern
 * 1. private 생성자
 * 2. private static 클래스이름 instance = new 클래스이름();
 * 3. public static 클래스이름 getInstance() {return instance;}
 *
 * @author 김수정
 * @version ver.1.0
 * @since jdk1.8
 */
public class FactoryDao {

	// ## JDBC(자바와 db 연동) 관련 property : driver, url db벤더에서 결정
		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
		private String user = "scott";
		private String password = "tiger";
	
	private static FactoryDao instance = new FactoryDao();
	
	private FactoryDao() {
		try {
			Class.forName(driver);
			System.out.println("[생성] 드라이버 로딩");
		} catch (ClassNotFoundException e) {
			System.out.println("[오류] 드라이버 로딩 오류");
			e.printStackTrace();
		}
	}

	public static FactoryDao getInstance() {
				return instance;
	}
	
	//DB 연결 Connection 반환 메서드
	/**
	 * DB 연결 Connection 반환 메서드
	 * @return Connection
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 자원해제 : SELECT 수행에 대한 자원
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public void close(Connection conn, Statement stmt, ResultSet rs) {

		try {
			if(rs != null) // 오류가 발생할 때 nullPointerException 예방 가능
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(stmt != null)
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null)
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
	
	/**
	 * 자원해제 : CUD 수행에 대한 자원
	 * @param conn
	 * @param stmt
	 */
	public void close(Connection conn, Statement stmt) {
		
		close(conn, stmt, null);
	}
	
}
