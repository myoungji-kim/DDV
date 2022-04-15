<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id = "wrap_surveyList">
	<div class="countTotal"> 전체 퀵폴 (${totalCount}) </div>
	<div class="wrap_table">
	<table>
		<thead>
			<tr>
				<td class="idx"> IDX </td>
				<td class="mainimg"> 썸네일 </td>
				<td class="title" style="width:40%;"> 질문 </td>
				<td class="writedate"> 업로드일 </td>
				<td class="enddate"> 종료일 </td>
				<td class="point"> 공개여부 </td>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach var="poll" items="${pollList}" varStatus="status">
		<tr>
			<td class="idx"> ${poll.p_idx} </td>
			<td class="mainimg"><img src="${imgUrl}/${poll.image}"/></td>
			<td class="box_info">
				<a href="/admin/editPoll?p_idx=${poll.p_idx}">
					<span>${poll.question}</span>
				</a>
			</td>
			<td class="writedate"><span> ${poll.writedate} </span></td>
			<td class="enddate"><span> ${poll.enddate} </span></td>
			<td class="point"><span> ${poll.option1} 공개 </span></td>
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

	<a class="ico_create" href = "/admin/editPoll"> <img src = "/img/btn_create_03.png"> </a>
</div>



