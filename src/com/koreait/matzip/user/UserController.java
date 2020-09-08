package com.koreait.matzip.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.vo.UserVO;

public class UserController { // 이제 서블릿 역할을 클래스가 한다!

	private UserService service;

	public UserController() {
		service = new UserService();
	}

	// 화면 열때 필수
	// 보내는건 리퀘스트만한다.
	public String login(HttpServletRequest request) {
		String error = request.getParameter("error");

		if (error != null) {
			switch (error) {
			case "2":
				request.setAttribute("msg", "아이디를 확인해주세요.");
				break;
			case "3":
				request.setAttribute("msg", "비밀번호를 확인해주세요");
				break;
			}
		}
		// 열 파일과 열어야될 템플릿
		request.setAttribute(Const.TITLE, "로그인"); // 템플릿에서 인클루드할것.
		request.setAttribute(Const.VIEW, "user/login"); // 템플릿에서 인클루드할것.
		return ViewRef.TEMP_DEFAULT; // 스프링으론
		// 템플릿이란?:웹페이지 레이아웃 구성 자체를 jsp파일로 만들고 파일명으로 넣는다.
	}

//용도:

	public String joinProc(HttpServletRequest request) {
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String nm = request.getParameter("nm");

		UserVO param = new UserVO();
		param.setUser_id(user_id);
		param.setUser_pw(user_pw);
		param.setNm(nm);

		int result = service.join(param); // 결과값은 UserService 의 join 함수에서
		return "redirect:/user/login";
	}

	public String loginProc(HttpServletRequest request) {
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");

		UserVO param = new UserVO();
		// 컨트롤러의 역할.
		param.setUser_id(user_id);
		param.setUser_pw(user_pw);

		int result = service.login(param); // 결과값은 UserService 의 join 함수에서

		if (result == 1) {
			return "redirect:/restaurant/restMap";
		} else {
			// 에러가 터졌을때
			return "redirect:/user/login?user_id=" + user_id + "&error=" + result;
		}
	}

	public String join(HttpServletRequest request) {
		request.setAttribute(Const.TITLE, "회원가입");
		request.setAttribute(Const.VIEW, "user/join"); // 템플릿에서 인클루드할것.
		return ViewRef.TEMP_DEFAULT;
	}

	public String ajaxIdChk(HttpServletRequest request) {
		String user_id = request.getParameter("user_id");
		UserVO param = new UserVO();
		param.setUser_id(user_id);
		param.setUser_pw("user_pw");
		
		int result = service.login(param);

		return String.format("/ajax:/{\"result\":%s}", result);
	}
}
