<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sectionContainerCenter">
	<div>
	<div class="msg">${msg}</div>
		<form class="frm" action="/user/loginProc" method="post"> <!-- form 태그로 post로 날림 날림. -->
			<div><input type="text" name="user_id" placeholder="아이디"></div>
			<div><input type="password" name="user_pw" placeholder="비밀번호"></div>
			<div><input type="submit" value="로그인"></div>
		</form>
		<div><a href="/user/join">회원가입</a></div>
	</div>
</div>