package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.user.UserController;

public class HandlerMapper {
	
	private UserController userCon; //싱글톤
	//딱한번 객체화.
	
	public HandlerMapper() {
		userCon = new UserController();
	}
	//기본 생성자는 무조건 실행이 된다. user패키지 내부의 UserController를 새로 객체생성해 서로연결한다. 
	public String nav(HttpServletRequest request) {
		//주소값 분석
		String[] uriArr = request.getRequestURI().split("/"); // 슬러시 기준으로 잘라라.
		
		if(uriArr.length < 3) {
			return "405"; //길이 값이 2일때 null을 반환.
		}
		//아작스의 문자열과 비교했을때 user와 같다면?
		if("ajax".equals(uriArr[1])) {
			return "ajax";
			//ajax를 반환한다.
		}
		//split으로 자르면 배열로 사용가능
		//이중 스위치문 1번은 폴더 두번은 주소값.
		switch(uriArr[1]) {
		case ViewRef.URI_USER://컨트롤러분기 /유저/로그인,조인,조인프록을 적었을때.
			switch(uriArr[2]) { // 2번방엔 로그인이 적혀있다면 
			case "login":
				return userCon.login(request);
			case "loginProc":
				return userCon.loginProc(request);
			case "join":
				return userCon.join(request);
			case "joinProc":
				return userCon.joinProc(request);
			case "":
				return userCon.ajaxIdChk(request);
			}
		}
		return "404";
	}
}
