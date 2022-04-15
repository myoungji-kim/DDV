<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="imgSrc" value="/resources/asset/images"> </c:set>

<!DOCTYPE html>
<html>
	<head>
	<title>Admin DDV</title>
	
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=1260">
		<meta name="google-site-verification" content="EBWVlt9UKAo5kb3JFlCqSdRF14f0cLXgws_kOjLW6gE" />
		<meta name="naver-site-verification" content="a954ae14ef5b21504685d492f4e3142a8c84efde" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta property="og:url" content="">
		
		<!-- 미리보기 관련 -->
		<meta property="og:type" content="website">
		<meta property="og:title" content="">
		<meta name="keywords" content="">
		<meta property="og:description" content="">
		<meta property="og:image" content="">
		<meta name="description" content="">
		
		<link rel="shortcut icon" href="#">
		<link rel="stylesheet" type="text/css" href="/resources/asset/css/common.css" />
		<link rel="preconnect" href="https://fonts.gstatic.com">
		<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;200;300;400;500;700;900&display=swap" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="/resources/asset/script/jquery-ui-1.12.1.custom/jquery-ui.css">
		<link rel="stylesheet" href="/resources/asset/script/jquery-nice-select-1.1.0/css/nice-select.css">
		
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>
		<script src="/resources/asset/script/jquery-nice-select-1.1.0/js/jquery.js"></script> 
		<script src="/resources/asset/script/jquery-nice-select-1.1.0/js/jquery.nice-select.js"></script>
		<script type="text/javascript" src="/resources/asset/script/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/resources/asset/script/jquery-ui-1.12.1.custom/jquery-ui.js"></script>
		<script type="text/javascript" src="/resources/asset/script/jquery-tmpl-1.4.2.js"> </script>
		<script type="text/javascript" src="/resources/asset/script/common.js"></script>
	</head>

	<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}" /> 
	<c:set var="imgUrl" value="/resources/upload"/>
	<c:if test="${url eq '/admin/login'}"> <body style="background-color:#444;"> </c:if>
	<c:if test="${url ne '/admin/login'}"> <body> </c:if>
	
	
	
	
	
	
	