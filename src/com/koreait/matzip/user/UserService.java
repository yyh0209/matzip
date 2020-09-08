package com.koreait.matzip.user;

import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.vo.UserVO;

public class UserService {
	private UserDAO dao;

	public UserService() {
		dao = new UserDAO();
	}

	// 로그인
	public int join(UserVO param) {
		String pw = param.getUser_pw();
		String salt = SecurityUtils.generateSalt();
		String encryptPw = SecurityUtils.getEncrypt(pw, salt);

		param.setUser_pw(encryptPw);
		param.setSalt(salt);

		return dao.join(param);
	}

//로직담당.에서 암호화 알고리즘을 짜라.
	// result 0:에러 1:로그인성공 2:아이디 없음 3:비밀번호 틀림.
	public int login(UserVO param) {
		int result = 0;

		UserVO dbResult = dao.selUser(param); // id값이 담겨져 있음.
		// 아이디 없음.
		// db의 비밀번호와 같다면 맞춘것
		if (dbResult.getI_user() == 0) {
			result = 2;
			// id가 없을때
		} else {
			String salt = dbResult.getSalt();
			String encryptPw = SecurityUtils.getEncrypt(param.getUser_pw(), salt);
			// 암호가 맞을 경우
			if (encryptPw.equals(dbResult.getUser_pw())) {
				result = 1;
			} else {
				// 비밀번호가 틀렸을때
				result = 3;
			}
		}
		return result;
	}
}
