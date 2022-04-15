<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="wrap_analyzeList">

	<div class="countTotal"> 
		<a onclick="javascript:history.back()"><img src="/img/btn_back_01.png"></a>
		no.${joinList[0].p_idx} : ${joinList[0].question} | 퀵폴 참여 결과
	</div>
	<div class="wrap_table">
	<table>
		<thead>
			<tr>
				<td class="idx" style="width:15%;"> 선택지 번호 </td>
				<td class="title" style="width:65%;"> 선택지 </td>
				<td>투표 인원</td>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach var="join" items="${joinList}" varStatus="status">
		<tr>
			<td class="idx"><span>${join.pa_idx}</span></td>
			<td class="title"><span>${join.text}</span></td>
			<td class="title"><span>${join.sum}</span></td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	
</div>



