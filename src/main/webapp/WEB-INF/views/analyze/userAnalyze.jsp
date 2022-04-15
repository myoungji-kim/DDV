<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="wrap_analyzeList">
	<div class="countTotal"> 사용자 포인트 획득 순위 </div>
	<div class="wrap_table">
	<table>
		<thead>
			<tr>
				<td class="idx" style="width:12%;"> 회원 번호 </td>
				<td class="title" style="width:80%;"> 누적 포인트 </td>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach var="point" items="${pointList}" varStatus="status">
		<tr>
			<td class="idx"><a href="/admin/userList/userPointList?m_idx=${point.m_idx}" style="border-bottom: 1px solid #888;">${point.m_idx}</a></td>
			<td class="title"><span> ${point.point} </span></td>
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
			<a class="page_num" href="?no=${i}<c:if test="${txtSearch!=null}">&txtSearch=${txtSearch}</c:if>">
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




