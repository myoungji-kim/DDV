<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="./include/header.jsp" %>

<div id="loginPage">
<form action = "/admin/login.action" name="loginForm" method="post">
	<div class="wrap_loginBox">
		<div class="title"><span> DDV </span></div>
		<div class="subtitle"><span> Administration </span></div>
		<table class="loginBox">
			<tr>
				<td><span>ID</span></td>
				<td><input name="userid" placeholder="아이디를 입력하세요" autocomplete="off"></td>
			</tr>
			<tr>
				<td><span>PW</span></td>
				<td><input type="password" name="passwd" onkeypress="javascript:if(event.keyCode==13) {go_login(loginForm)}" placeholder="비밀번호를 입력하세요" autocomplete="off"></td>
			</tr>
		</table>
		<a class="btn_login" onclick="go_login(loginForm)"><span>Log In</span></a>
	</div>
</form>	
</div>
</body>
</html>
