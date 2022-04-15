<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<div id="wrap_analyzeList">
	<div class="countTotal"> 
		<a onclick="javascript:history.back()"><img src="/img/btn_back_01.png"></a>
		no.${joinList[0].s_idx} | 문항별 선택 결과
	</div>
	<div class="wrap_table">
	<table>
		<thead>
			<tr>
				<td class="idx" style="width:12%;"> 질문번호 </td>
				<td class="title" style="width:40%;"> 질문 </td>
				<td style="width:20%;">선택지</td>
				<td style="width:20%;">선택 인원</td>
			</tr>
		</thead>
	</table>
	
	<c:set var="q_num" value="${joinList[fn:length(joinList)-1].q_idx}"/>
	<c:set var="cnt" value="0"/>
	<c:forEach var="i" begin="0" end="${q_num-1}">
	<table>
		<tbody>
			<c:forEach var="j" begin="${(i*4)}" end="${(i*4)+3}">
		         <tr>
			            <c:if test="${j%4 == 0}">
			               <td class="idx" style="width:12%;" rowspan="100%"><span>${joinList[j].q_idx}</span></td>
			               <td class="title" style="width:40%;" rowspan="100%"><span> ${joinList[j].question} </span></td>
			            </c:if>
		               <td style="width:20%;"><span>${joinList[j].a_idx}. ${joinList[j].text}</span></td>
		               <td style="width:20%;"><span>${joinList[j].sum}</span></td>
		         </tr>
		      </c:forEach>
		</tbody>
	</table>
	</c:forEach>
	</div>
</div>



