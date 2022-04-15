<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}" />

<div id="wrap_sideMenu">
	<table>
		<tr>
			<td class="box_userinfo">
				<span> ${login.username} 님, 환영합니다! </span>
			</td>
			<td class="box">
				<span class="txt_cate"> 회원 관리 </span>
				<a href="/admin/userList" class="txt_name <c:if test="${not fn:contains(url, '/admin/userList')}">no</c:if>select"> 사용자 </a>
				<a href="/admin/adminList" class="txt_name <c:if test="${not fn:contains(url, '/admin/adminList')}">no</c:if>select"> 관리자 </a>
				<a href="/admin/inquireList" class="txt_name <c:if test="${not fn:contains(url, '/admin/inquireList')}">no</c:if>select""> 문의 내역 </a>
			</td>
			<td class="box">
				<span class="txt_cate"> 콘텐츠 관리 </span>
				<a href="/admin/surveyList" class="txt_name <c:if test="${not fn:contains(url, '/admin/surveyList')}">no</c:if>select"> 서베이 (Survey) </a>
				<a href="/admin/pollList" class="txt_name <c:if test="${not fn:contains(url, '/admin/pollList')}">no</c:if>select"> 퀵폴 (Poll) </a>
				<a href="/admin/FAQList" class="txt_name <c:if test="${not fn:contains(url, '/admin/FAQ')}">no</c:if>select"> 자주 묻는 질문 (FAQ) </a>
			</td>
			<td class="box">
				<span class="txt_cate"> 인사이트 분석 </span>
				<a href="/admin/surveyAnalyze" class="txt_name <c:if test="${not fn:contains(url, '/admin/surveyAnalyze')}">no</c:if>select"> 서베이 참여 횟수 </a>
				<a href="/admin/pollAnalyze" class="txt_name <c:if test="${not fn:contains(url, '/admin/pollAnalyze')}">no</c:if>select"> 퀵폴 참여 횟수 </a>
				<a href="/admin/userAnalyze" class="txt_name <c:if test="${not fn:contains(url, '/admin/userAnalyze')}">no</c:if>select"> 사용자 포인트 획득 순위 </a>
			</td>
			<td class = "box">
				<a class = "txt_cate" href="/admin/logout"> 로그아웃 하기 </a>
			</td>
		</tr>
	</table>
</div>

