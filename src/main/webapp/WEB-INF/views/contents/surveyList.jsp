<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id = "wrap_surveyList">
	<div class="countTotal"> 전체 서베이 (${totalCount}) </div>
	<div class="wrap_table">
	<table>
		<thead>
			<tr>
				<td class="idx"> IDX </td>
				<td class="mainimg" style="width: 15%;"> 썸네일 </td>
				<td class="title" style="width: 45%;"> 제목 </td>
				<td class="writedate"> 업로드일 </td>
				<td class="enddate"> 종료일 </td>
				<td class="point"> 포인트 </td>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="survey" items="${surveyList}" varStatus="status">
		<tr>
			<td class="idx"> ${survey.s_idx} </td>
			<td class="mainimg"><img src="${imgUrl}/${survey.mainimg}"/></td>
			<td class="box_info">
				<a href="/admin/editSurvey?s_idx=${survey.s_idx}">
					<span>${survey.title}</span>
					<span>${survey.subtitle}</span> 
				</a>
			</td>
			<td class="writedate"><span> ${survey.writedate} </span></td>
			<td class="enddate"><span> ${survey.enddate} </span></td>
			<td class="point"><span> ${survey.point} P </span></td>
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
	
	<a class="ico_create" href = "/admin/editSurvey"> <img src = "/img/btn_create_03.png"> </a>
</div>