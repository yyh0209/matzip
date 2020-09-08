package com.koreait.matzip.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbManager {

	public static Connection getCon() throws Exception {
		String url = "jdbc:mysql://localhost:3306/matzip";
		String user = "root";
		String pw = "koreait2020";
		String className = "com.mysql.cj.jdbc.Driver"; // mysql 연동.

		Class.forName(className);
		Connection con = DriverManager.getConnection(url, user, pw);
		System.out.println("DB 연결 완료!");
		return con;
		// 여기는 jdbc 에 접속하는 루트다. sql연동을 여기서 다하는데 현재 mySQL에 연동을 한 상태.
		// Class.forName(className):classname엔 mySQL에 연동하는 변수를 설정해 forName함수에 인자값을 받아
		// Connection 클래스의 인스턴스 con은 드라이브 매니저에서 변수로 지정한 url,user,pw를 sql에 연동한다.
		// con을 반환한다.
	}

	public static void close(Connection con, PreparedStatement ps) {
		close(con, ps, null);
	}
	//
	public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
		if(rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace();} }
		if(ps != null) { try { ps.close(); } catch (SQLException e) { e.printStackTrace();} }
		if(con != null) { try { con.close(); } catch (SQLException e) { e.printStackTrace();} }
	}
}
