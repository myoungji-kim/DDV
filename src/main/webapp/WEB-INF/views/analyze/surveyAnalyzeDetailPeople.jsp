<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="wrap_analyzeList">

	<div class="countTotal"> 
		<a onclick="javascript:history.back()"><img src="/img/btn_back_01.png"></a>
		no.${joinList[0].s_idx} : ${joinList[0].title} | 설문 참여 현황 
	</div>
	<div class="wrap_table">
	<table>
		<thead>
			<tr>
				<td class="idx" style="width:8%;"> 회원번호 </td>
				<td class="title" style="width:40%;"> 아이디 </td>
				<td>참여일</td>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach var="join" items="${joinList}" varStatus="status">
		<tr>
			<td class="idx"><span>${join.m_idx}</span></td>
			<td class="title"><span> ${join.userid} </span></td>
			<td class="title"><span> ${join.joindate} </span></td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	
</div>



