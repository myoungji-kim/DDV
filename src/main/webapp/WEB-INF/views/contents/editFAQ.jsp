<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="wrap_editSurvey">
<form action="/admin/editFAQ.action" method="post" name="writeFAQForm">
<c:choose>
	<c:when test="${param.f_idx != null}">
		<c:set var="mode" value="update"/>
		<c:set var="f_idx" value="${param.f_idx}"/>
		<c:set var="title" value="${FAQInfo.title}"/>
		<c:set var="text" value="${FAQInfo.text}"/>
		<c:set var="userid" value="${FAQInfo.userid}"/>
		<c:set var="m_idx" value="${FAQInfo.m_idx}"/>
		<c:set var="writedate" value="${FAQInfo.writedate}"/>
	</c:when>
	<c:when test="${param.f_idx == null}">
		<c:set var="mode" value="insert"/>
		<c:set var="f_idx" value=""/>
		<c:set var="title" value=""/>
		<c:set var="text" value=""/>
		<c:set var="userid" value=""/>
		<c:set var="m_idx" value="${login.m_idx}"/>
		<c:set var="writedate" value=""/>
	</c:when>
</c:choose>
	<input type="hidden" name="mode" value="${mode}">
	<input type="hidden" name="m_idx" value="${m_idx}">
	<table id="main" class="main">
		<tr>
			<td colspan="3">
				<div class="top_title">
					<span>
						<c:if test="${mode =='insert'}">글 등록하기</c:if>
						<c:if test="${mode =='update'}">글 수정하기</c:if>
					</span>
				</div>
			</td>
		</tr>
		<c:if test="${mode == 'update'}">
		<tr class="tr_oneLine">
			<th><span>글 번호</span></th>
			<td colspan="2"><div class="bottom_line"><input readonly="readonly" id="f_idx" name="f_idx" value="${f_idx}"></div></td>
		</tr>
		</c:if>
		<tr class="tr_oneLine">
			<th><span>제목</span></th>
			<td colspan="2"><div class="bottom_line"><input type="text" autocomplete="off" id="title" name="title" placeholder="제목을 입력해주세요" value="${title}"></div></td>
		</tr>
		<tr class="tr_oneLine">
			<th><span>본문</span></th>
			<td class="item_box">
				<textarea name="text">${text}</textarea>
			</td> 
		</tr>
		<c:if test="${mode == 'update'}">
		<tr class="tr_oneLine">
			<th><span>작성자</span></th>
			<td colspan="2" class="writer">
				<div class="bottom_line"><input readonly="readonly" id="m_idx" name="m_idx" value="${m_idx}">
					<span>${userid}</span>
				</div>
			</td>
		</tr>
		<tr class="tr_oneLine">
			<th><span>최초 등록일</span></th>
			<td colspan="2"><div class="bottom_line"><input readonly="readonly" id="writedate" name="writedate" value="${writedate}"></div></td>
		</tr>
		</c:if>
	</table>
</form>

<div class="wrap_btn">
	<div class="clear"></div>
	<a class="submit_box" onclick="writeFAQForm_submit(writeFAQForm)">
		<c:if test="${mode =='insert'}">등록하기</c:if>
		<c:if test="${mode =='update'}">수정하기</c:if>
	</a>
	<c:if test="${mode =='update'}">
		<a class="delete_box" onclick="writeFAQForm_delete(writeFAQForm)"> FAQ 삭제하기 </a>
	</c:if>
</div>
</div>