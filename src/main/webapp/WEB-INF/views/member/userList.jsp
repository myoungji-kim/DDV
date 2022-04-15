<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id = "wrap_memberList">
	<div class="countTotal"> 전체 멤버 (${totalCount}) </div>
	<div class="wrap_table">
	<table>
		<thead>
			<tr>
				<td class="idx"> IDX </td>
				<td class="name"> 이름/아이디 </td>
				<td class="point"> 포인트 </td>
				<td class="joinDate"> 가입일 </td>
				<td class="email"> 이메일 주소 </td>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="user" items="${userList}" varStatus="status">
		<tr>
			<td class="idx"> ${user.m_idx} </td>
			<td class="box_info">
				<a href="/admin/editUserInfo?m_idx=${user.m_idx}">
					<span>${user.username}</span>
					<span>${user.userid}</span> 
				</a>
			</td>
			<td class="point">
				<c:if test="${user.getpoint == null}"> 
					<span style="border-bottom: 0px;">0 P</span>
				</c:if> 
				<c:if test="${user.getpoint != null}"> 
					<a href="/admin/userList/userPointList?m_idx=${user.m_idx}"><span>${user.getpoint} P</span></a>
				</c:if>
			</td>
			<td class="joinDate"> <span> ${user.joindate} </span> </td>
			<td class="email"> <span> ${user.email1}@${user.email2} </span> </td>
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
	
	<a class="ico_create" href="/admin/editUserInfo"> <img src = "/img/btn_create_03.png"> </a>
</div>