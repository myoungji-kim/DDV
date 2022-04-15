<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id = "wrap_memberList">
	<div class="countTotal"> 
		<a onclick="javascript:history.back()"><img src="/img/btn_back_01.png"></a>
		${pointList[0].username}님의 포인트 (${totalPoint} P)  
	</div>
	<div class="wrap_table">
	<table>
		<thead>
			<tr>
				<td class="idx_point" style="width:12%;"> 설문 번호 </td>
				<td class="title_point" style="width:60%;"> 설문제목 </td>
				<td class="point"> 획득 포인트 </td>
				<td class="joinDate"> 참여일 </td>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="point" items="${pointList}" varStatus="status">
		<tr>
			<td class="idx"> ${point.s_idx} </td>
			<td class="box_info">
				<a href="/admin/userList/userPointList/answer?m_idx=${point.m_idx}&s_idx=${point.s_idx}"><span>${point.title}</span></a>
			</td>
			<td class="point"><span>${point.getpoint} P</span></td>
			<td class="joinDate"> <span> ${point.getdate} </span> </td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	
	<!-- 페이징 -->
	<div id="wrap_paging">
		<!-- prev 버튼 -->
		<c:if test="${page.btnPrev}">
			<a class="btn_prev" href="?no=${page.beginPage-1}"><span>이전</span></a>
		</c:if>
	
		<!-- 페이징 넘버 -->
		<c:forEach var="i" begin="${page.beginPage}" end="${page.endPage}">
			<a class="page_num" href="?no=${i}">
				<span
					<c:if test="${param.no==i || ((param.no=='' || param.no==null) && i==1)}">
						 style="color:#FF8A57; border-bottom: 1px solid #FF8A57;"
					</c:if>
				>${i}</span>
			</a>
		</c:forEach>
	
		<!-- next 버튼 -->
		<c:if test="${page.btnNext}">
			<a class="btn_next" href="?no=${page.endPage+1}"><span style="">다음</span></a>
		</c:if>
	</div>
</div>