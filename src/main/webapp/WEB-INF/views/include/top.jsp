<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="wrap_top">
<form action="" method="get" name="searchForm">	
	<div class="title">
		<span> DDV </span>
		<span> Administration </span>
	</div>
	
	<a class="btn_search" onclick="adminSearch(searchForm)"><span>검색</span></a>
    <input type="text" name="txtSearch" placeholder="내용을 입력하세요" value="${txtSearch}" onKeypress="javascript:if(event.keyCode==13) {adminSearch(searchForm)}" autocomplete="off">

    <select id="topOptions" name="category" class="top_options">
        <option class="o" value = "member" <c:if test="${fn:contains(url, '/admin/userList')}"> selected </c:if>> 사용자 </option>
        <option class="o" value = "admin" <c:if test="${fn:contains(url, '/admin/adminList')}"> selected </c:if>> 관리자 </option>
        <option class="o" value = "inquire" <c:if test="${fn:contains(url, '/admin/inquireList')}"> selected </c:if>> 문의 내역 </option>
        <option class="o" value = "survey" <c:if test="${fn:contains(url, '/admin/surveyList')}"> selected </c:if>> 서베이 (Survey) </option>
		<option class="o" value = "poll" <c:if test="${fn:contains(url, '/admin/pollList')}"> selected </c:if>> 퀵폴 (Poll) </option>
		<option class="o" value = "faq" <c:if test="${fn:contains(url, '/admin/FAQList')}"> selected </c:if>> 자주 묻는 질문 (FAQ) </option>
    </select>
</form>
</div>