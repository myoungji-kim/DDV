<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id = "wrap_memberList">
	<div class="countTotal"> 전체 문의 (${totalCount}) </div>
	<div class="wrap_table">
	<table>
		<thead>
			<tr>
				<td class="idx"> IDX </td>
				<td class="name" style="width: 60%;"> 내용 </td>
				<td class="point" style="width: 20%;"> 작성일 </td>
				<td class="joinDate"> 답변여부 </td>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="inquire" items="${inquireList}" varStatus="status">
		<tr>
			<td class="idx"> ${inquire.i_idx} </td>
			<td class="box_info">
				<a href="/admin/inquireAnswer?i_idx=${inquire.i_idx}">
					<span>${inquire.question}</span>
				</a>
			</td>
			<td class="point"> 
			<span> 
				${inquire.writedate}
			</span>
			</td>
			<td class="joinDate">
				<span <c:if test="${inquire.complete=='답변 대기'}"> style="color:#FF8A57;"</c:if>> ${inquire.complete} 
			</span></td>
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