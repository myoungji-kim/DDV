<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="wrap_editUser">
	<div id="main">
		<div class="title">
			<span> 
				1:1 문의 답변 등록하기
			</span>
		</div>
		<form action="/admin/inquireAnswer.action" id="editUser_form" name="editInquireForm" method="post">
			<table class="editUser_table">
				<tr>
					<td class="item_th">
						<div><span>회원 번호</span></div>
					</td>
					<td class="item_box">
						<div><input readonly="readonly" id="i_idx" name="i_idx" value="${inquireAnswer.i_idx}"></div>
					</td>
				</tr>
				<tr>
					<td class="item_th">
						<div><span>질문 등록일</span></div>
					</td>
					<td class="item_box">
						<div><input readonly="readonly" id="writedate" name="writedate" value="${inquireAnswer.writedate}"></div>
					</td>
				</tr>
				<tr>
					<td class="item_th">
						<div><span>질문</span></div>
					</td>
					<td class="item_box">
						<textarea name="question">${inquireAnswer.question}</textarea>
					</td> 
				</tr>
				<tr>
					<td class="item_th">
						<div><span>답변 여부</span></div>
					</td>
					<td class="item_box">
						<div><input readonly="readonly" id="complete" name="complete" value="${inquireAnswer.complete}"></div>
					</td>
				</tr>
				<c:if test="${inquireAnswer.complete=='답변 완료'}">
				<tr>
					<td class="item_th">
						<div><span>최근 답변일</span></div>
					</td>
					<td class="item_box">
						<div><input readonly="readonly" id="answerdate" name="answerdate" value="${inquireAnswer.answerdate}"></div>
					</td>
				</tr>
				<tr>
					<td class="item_th">
						<div><span>답변자 번호</span></div>
					</td>
					<td class="item_box">
						<div><input readonly="readonly" id="admin_idx" name="admin_idx" value="${inquireAnswer.admin_idx}"></div>
					</td>
				</tr>
				</c:if>
				<c:if test="${inquireAnswer.complete=='답변 대기'}">
					<input type="hidden" id="admin_idx" name="admin_idx" value="${login.m_idx}">
				</c:if>
				<tr>
					<td class="item_th">
						<div><span>답변</span></div>
					</td>
					<td class="item_box">
						<textarea name="answer">${inquireAnswer.answer}</textarea>
					</td> 
				</tr>
			</table>
			<div class="wrap_btn">
				<a class="submit_box" onclick="editInquireForm_submit(editInquireForm)">
					<c:if test="${inquireAnswer.complete=='답변 대기'}">등록하기</c:if>
					<c:if test="${inquireAnswer.complete=='답변 완료'}">수정하기</c:if>
				</a>
			</div>
		</form>
	</div>
</div>
