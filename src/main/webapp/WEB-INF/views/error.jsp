<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="./include/header.jsp" %>

<div style="width: 400px; margin: 100px auto; text-align:center; color: #555;">
	<img style="width:100%; border-radius:30px; over-flow:hidden;" src="${imgSrc}/error.jpg">
	<div style="margin-top: 20px;">
		<span style="font-family: Jalnan; font-size: 27px; font-weight: 700; line-height:45px;">페이지에 문제가 있나봐요</span><br>
		<span style="font-family: NanumSquareRound; font-size: 20px; line-height:45px;">잠시만 기다려주세요!</span><br>
		<a href="javascript:history.back()" style="display: inline-block; margin-top: 30px; padding:15px 25px; border-radius:10px; background-color:#FF8A57;"><span style="color:#fff; font-size: 20px; font-family: NanumSquareRound; font-weight:500;">뒤로가기</span></a>
	</div>
</div>

</body>
</html>