<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="wrap_analyzeList">
	<div class="countTotal"> 서베이별 참여 인원 </div>
	<form name="searchAnalyzeForm" method="get" style="display: inline-block;">
		<input type="hidden" name="category" value="survey"> 
		<div class="inputDate">
			<input type="text" id="startdate" class="date" placeholder="시작일" name="startdate" value="${startdate}" autocomplete="off">
			<span> ~ </span>
			<input type="text" id="enddate" class="date" placeholder="종료일" name="enddate" value="${enddate}" autocomplete="off">
			<a class="btnSearchDate" onclick="searchAnalyze(searchAnalyzeForm)"><span>조회</span></a>
		</div>
	</form>
	<div class="wrap_table">
	<table>
		<thead>
			<tr>
				<td class="idx" style="width:8%;"> IDX </td>
				<td class="idx" style="width:70%;"> 문항별 선택 결과 </td>
				<td class="title" style="width:20%;"> 참여 인원 </td>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach var="survey" items="${surveyList}" varStatus="status">
		<tr>
			<td class="idx">${survey.s_idx}</td>
			<td class="title"><a href="/admin/surveyAnalyze/detailResult?s_idx=${survey.s_idx}" style="border-bottom: 1px solid #888;">결과보기</a></td>
			<td class="title"><a href="/admin/surveyAnalyze/detailPeople?s_idx=${survey.s_idx}" style="border-bottom: 1px solid #888;">${survey.sum}</a></td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</div>



