package com.koreait.matzip.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.koreait.matzip.vo.UserVO;
import com.koreait.matzip.db.JdbcSelectInterface;
import com.koreait.matzip.db.JdbcTemplate;
import com.koreait.matzip.db.JdbcUpdateInterface;

public class UserDAO {
	// CRUD 담당.
	public int join(UserVO param) {
		// 정수값을 반환

		int result = 0;
		//
		String sql = " INSERT INTO t_user " 
			+ " (user_id, user_pw, salt, nm) " 
			+ " VALUES " 
			+ " (?, ?, ?, ?) ";
		// 자바는 메소드가 아닌 객체를 보내고 메소드를 구현해서 보내면 그 메소드를 호출하는걸 콜백이라한다.
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getUser_id());
				ps.setNString(2, param.getUser_pw());
				ps.setNString(3, param.getSalt());
				ps.setNString(4, param.getNm());
			}
		});
		// 자바는 메소드가 아닌 객체를 보내고 메소드를 구현해서 보내면 그 메소드를 호출하는걸 콜백이라한다.

	}

	public UserVO selUser(UserVO param) {
		UserVO result = new UserVO();

		String sql = " SELECT i_user, user_id, user_pw, nm,profile_img,r_dt,m_dt " 
		+ " FROM t_user WHERE ";
		//1을 보내면 성공 0을보내면 아이디 없음.
		//sql내의 문자열을 바꾸는 방법.
		if (param.getI_user() > 0) {
			sql += "i_user =" + param.getI_user();
		}else if (param.getUser_id() != null && !"".equals(param.getUser_id())) {
			sql += "user_id ='" + param.getUser_id() + "' ";
		}
		 JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				
			}
			@Override
			public void executeQuery(ResultSet rs) throws SQLException {
				if(rs.next()) {
					result.setI_user(rs.getInt("i_user"));
					result.setUser_id(rs.getNString("user_id"));
					result.setUser_pw(rs.getNString("user_pw"));
					result.setSalt(rs.getNString("Salt"));
					result.setNm(rs.getNString("nm"));
					result.setProfile_img(rs.getNString("Profile_img"));
					result.setR_dt(rs.getNString("r_dt"));
				}
			}
		});
		return result;
	}
}
