package com.koreait.matzip;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/") // 모든요청은 얘가 다 잡아냄.
public class Container extends HttpServlet { // 톰캣이 실행
	private static final long serialVersionUID = 1L;

	private HandlerMapper mapper; // 주소값.

	public Container() { // 기본생성자라서 실행됨.
		mapper = new HandlerMapper();
		// 기본 생성자내에서 HandlerMapper타입의 mapper를 새로 객체생성을한다.
	}

	// 호출하면 라이브러리.
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		proc(request, response); // 호출당하면 프레임워크
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		proc(request, response);
	}

	private void proc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String temp = mapper.nav(request);

		// 결과가 스트링이다.

		if (temp.indexOf(":") >= 0) {
			String prefix = temp.substring(0, temp.indexOf(":"));
			String value = temp.substring(temp.indexOf(":")+1);

			System.out.println("prefix : "+prefix);
			System.out.println("value : "+value);
			
			if ("redirect".equals(prefix)) {
				response.sendRedirect(value);
				return;
			} // 응답.
			else if ("ajax".equals(prefix)) {
				response.setCharacterEncoding("UTF-8"); // 한글이 안깨지는 용도.
				response.setContentType("application/json"); // 웹 페이지에서 보낼때 제이슨이라고 인식하게 만든다.
				PrintWriter out = response.getWriter();
				out.print(value); // 제이슨식 선언."{\"name\"}: 1" 감싸는건 쌍따옴표.
				// 제이슨 형태로 보냈다..
				return;
			}
		}
		switch (temp) {
		case "405": // /가 없는경우
			temp = "/WEB-INF/view/error.jsp";
			break;
		case "404": // /가 있는데 문자열이 서로 맞지않는다면. 경로가 잘못됐다면
			temp = "/WEB-INF/view/notFound.jsp";
			break;
		}
		// redirect:/user/login인 경우 0이상
		request.getRequestDispatcher(temp).forward(request, response);
	}
}
