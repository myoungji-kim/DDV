<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 
	* J-query *
	$(this).find("")
	$(this).parent()
	$(this).parents("")
-->

<div id="wrap_editSurvey">
<form action="/admin/editPoll.action" method="post" name="writePollForm" enctype="multipart/form-data">
<c:choose>
	<c:when test="${param.p_idx != null}">
		<c:set var="mode" value="update"/>
		<c:set var="p_idx" value="${param.p_idx}"/>
		<c:set var="question" value="${pollInfo.question}"/>
		<c:set var="image" value="${pollInfo.image}"/>
		<c:set var="option1" value="${pollInfo.option1}"/>
		<c:set var="writedate" value="${pollInfo.writedate}"/>
		<c:set var="enddate" value="${pollInfo.enddate}"/>
		<c:set var="m_idx" value="${pollInfo.m_idx}"/>
		<c:set var="userid" value="${pollInfo.userid}"/>
	</c:when>
	<c:when test="${param.s_idx == null}">
		<c:set var="mode" value="insert"/>
		<c:set var="p_idx" value=""/>
		<c:set var="question" value=""/>
		<c:set var="image" value=""/>
		<c:set var="option1" value=""/>
		<c:set var="writedate" value=""/>
		<c:set var="enddate" value=""/>
		<c:set var="m_idx" value="${login.m_idx}"/>
		<c:set var="userid" value=""/>
	</c:when>
</c:choose>
	<input type="hidden" name="mode" value="${mode}">
	<input type="hidden" name="m_idx" value="${m_idx}">
	
	<table id="main" class="main">
		<tr>
			<td colspan="3">
				<div class="top_title">
					<span>
						<c:if test="${mode =='insert'}">퀵폴 등록하기</c:if>
						<c:if test="${mode =='update'}">퀵폴 수정하기</c:if>
					</span>
				</div>
			</td>
		</tr>
		<c:if test="${mode == 'update'}">
		<tr class="tr_oneLine">
			<th><span>글 번호</span></th>
			<td colspan="2"><div class="bottom_line"><input readonly="readonly" id="s_idx" name="p_idx" value="${p_idx}"></div></td>
		</tr>
		</c:if>
		<tr class="tr_oneLine">
			<th><span>질문</span></th>
			<td colspan="2"><div class="bottom_line"><input type="text" autocomplete="off" id="title" name="question" placeholder="제목을 입력해주세요" value="${question}"></div></td>
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
		</c:if>
		<c:if test="${mode == 'update'}">
		<tr class="tr_oneLine">
			<th><span>최초 등록일</span></th>
			<td colspan="2"><div class="bottom_line"><input readonly="readonly" id="writedate" name="writedate" value="${writedate}"></div></td>
		</tr>
		</c:if>
		<tr class="tr_oneLine">
			<th>종료일</th>
			<td colspan="2"><div class="bottom_line"><input type="text" autocomplete="off" id="enddate" name="enddate" placeholder="종료일을 선택해주세요" value="${enddate}" autocomplete="off"></div></td>
		</tr>
		<tr class="tr_oneLine">
			<th>썸네일</th>
			<td colspan="2"><div class="bottom_line">
				<input type="file" id="mainimg" name="image">
				<input type="hidden" name="o_mainimg" value="${image}">
				${image}
			</div></td>
		</tr>
		<tr>
			<th>공개 여부</th>
			<td class="item_box">
				<div class="radio"><input type="radio" name="option1" value="전체" data-role="none" <c:if test="${option1 == '전체'}">checked</c:if>><span>전체 공개</span></div>
				<div class="radio"><input type="radio" name="option1" value="일부" data-role="none" <c:if test="${option1 == '일부'}">checked</c:if>><span>일부 공개</span></div>
			</td> 
		</tr>
	</table>
	
	
	<table class="main" id="table">
		<tr id="wrap_answer">
			<th class="tr_oneLine">선택지</th>
			<td colspan="2">
				<div class="select_wrap">
					<div class="input_wrap">
						
					</div>
				</div>
				<c:forEach begin="1" end="5" var="a_num">
				<div class="input_answer">
					<input type="text" autocomplete="off" name="text${a_num}" value="${answerList[a_num-1].text}" 
						placeholder="선택지${a_num}">
				</div>
				</c:forEach>
			</td>
		</tr>
	</table>
	
</form>

<div class="wrap_btn">
	<div class="clear"></div>
	<a class="submit_box" onclick="writePollForm_submit(writePollForm)">
		<c:if test="${mode =='insert'}">등록하기</c:if>
		<c:if test="${mode =='update'}">수정하기</c:if>
	</a>
	<c:if test="${mode =='update'}">
		<a class="delete_box" onclick="writePollForm_delete(writePollForm)"> 퀵폴 삭제하기 </a>
	</c:if>
</div>
</div>