<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id = "wrap_memberList">
	<div class="countTotal"> 
		<a onclick="javascript:history.back()"><img src="/img/btn_back_01.png"></a>
		${answerList[0].username}님의 답변 
	</div>
	<div class="wrap_table">
	<table>
		<thead>
			<tr>
				<td class="idx_point" style="width:12%;"> 질문 번호 </td>
				<td class="title_point" style="width:50%;"> 질문 </td>
				<td class="point" style="width:30%"> 선택한 문항 </td>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="answer" items="${answerList}" varStatus="status">
		<tr>
			<td class="idx">${answer.q_idx}</td>
			<td class="box_info"><span>${answer.question}</span></td>
			<td class="answer" style="text-align: center">
				<span>${answer.a_idx} : ${answer.text}</span>
			</td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</div>