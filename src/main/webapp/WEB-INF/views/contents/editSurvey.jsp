<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 
* J-query *
$(this).find("")
$(this).parent()
$(this).parents("")
-->

<script id="tmpl_quest" type="text/html">
	<table id="table<%="${num}"%>" class="main">
		<tr class="tr_questLine"><td colspan="2"><div></div></td></tr>
		<tr class="tr_oneLine">
			<th>질문<%="${num}"%></th>
			<td colspan="2"><div class="bottom_line"><input type="text" autocomplete="off" id="quest<%="${num}"%>" name="quest<%="${num}"%>" value="${quest.question}" placeholder="질문을 입력해 주세요"></div></td>
		</tr>
		<tr>
			<th class="tr_oneLine">이미지</th>
			<td colspan="2"><div class="bottom_line"><input type="file" autocomplete="off" id="image<%="${num}"%>" name="image<%="${num}"%>" value="${image1}">${image1}</div></td>
		</tr>
		<tr id="wrap_answer">
			<th class="tr_oneLine">선택지</th>
			<td colspan="2">
				<div class="input_answer"><input type="text" autocomplete="off" name="text1_<%="${num}"%>" value="${quest.text1}" placeholder="선택지1"></div>
				<div class="input_answer"><input type="text" autocomplete="off" name="text2_<%="${num}"%>" value="${quest.text2}" placeholder="선택지2"></div>
				<div class="input_answer"><input type="text" autocomplete="off" name="text3_<%="${num}"%>" value="${quest.text3}" placeholder="선택지3"></div>
				<div class="input_answer"><input type="text" autocomplete="off" name="text4_<%="${num}"%>" value="${quest.text4}" placeholder="선택지4"></div>
			</td>
		</tr>
		<tr class="tr_btnLine">
			<td colspan="2"><a class="delete_box" onclick="del_quest(<%="${num}"%>)"> 질문<%="${num}"%> 삭제하기 </a></td>
		</tr>
	<table>
</script>

<div id="wrap_editSurvey">
<form action="/admin/editSurvey.action" method="post" name="writeForm" enctype="multipart/form-data">
<c:choose>
	<c:when test="${param.s_idx != null}">
		<c:set var="mode" value="update"/>
		<c:set var="s_idx" value="${param.s_idx}"/>
		<c:set var="title" value="${surveyInfo[0].title}"/>
		<c:set var="subtitle" value="${surveyInfo[0].subtitle}"/>
		<c:set var="userid" value="${surveyInfo[0].userid}"/>
		<c:set var="m_idx" value="${surveyInfo[0].m_idx}"/>
		<c:set var="writedate" value="${surveyInfo[0].writedate}"/>
		<c:set var="enddate" value="${surveyInfo[0].enddate}"/>
		<c:set var="mainimg" value="${surveyInfo[0].mainimg}"/>
		<c:set var="time" value="${surveyInfo[0].time}"/>
		<c:set var="point" value="${surveyInfo[0].point}"/>
	</c:when>
	<c:when test="${param.s_idx == null}">
		<c:set var="mode" value="insert"/>
		<c:set var="s_idx" value=""/>
		<c:set var="title" value=""/>
		<c:set var="subtitle" value=""/> 
		<c:set var="userid" value=""/>
		<c:set var="m_idx" value="${login.m_idx}"/>
		<c:set var="writedate" value=""/>
		<c:set var="enddate" value=""/>
		<c:set var="mainimg" value=""/>
		<c:set var="time" value=""/>
		<c:set var="point" value=""/>
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
			<td colspan="2"><div class="bottom_line"><input readonly="readonly" id="s_idx" name="s_idx" value="${s_idx}"></div></td>
		</tr>
		</c:if>
		<tr class="tr_oneLine">
			<th><span>제목</span></th>
			<td colspan="2"><div class="bottom_line"><input type="text" autocomplete="off" id="title" name="title" placeholder="제목을 입력해주세요" value="${title}"></div></td>
		</tr>
		<tr class="tr_oneLine">
			<th><span>소제목</span></th>
			<td colspan="2"><div class="bottom_line"><input type="text" autocomplete="off" id="subtitle" name="subtitle" placeholder="소제목을 입력해주세요" value="${subtitle}"></div></td>
		</tr>
		<c:if test="${mode == 'update'}">
		<tr class="tr_oneLine">
			<th><span>작성자</span></th>
			<td colspan="2" class="writer">
				<div class="bottom_line"><input readonly="readonly" autocomplete="off" id="m_idx" name="m_idx" value="${m_idx}">
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
				<input type="file" id="mainimg" name="mainimg"  accept=".gif, .jpg, .png, .jpeg">
				<input type="hidden" name="o_mainimg" value="${mainimg}"> ${mainimg}
			</div></td>
		</tr>
		<tr class="tr_oneLine">
			<th><span>소요시간</span></th>
			<td colspan="2" class="timeLine"><div class="bottom_line">
				<input type="text" id="time" name="time" autocomplete="off" placeholder="예상 소요 시간(분)을 입력해주세요" value="${time}">
				<span>분</span>
			</div></td>
		</tr>
		<tr class="tr_oneLine">
			<th><span>포인트</span></th>
			<td colspan="2" class="timeLine"><div class="bottom_line">
				<input type="text" id="point" name="point" autocomplete="off" placeholder="포인트를 입력해주세요" value="${point}">
				<span>P</span>
			</div></td>
		</tr>
	</table>
	<c:forEach var="quest" items="${surveyInfo}" varStatus="quest_num">
	<c:set var="num" value="${quest_num.count}"/>
	<table class="main" id="table${num}">
		<tr class="tr_questLine">
			<td colspan="2"><div></div></td>
		</tr>
		<tr class="tr_oneLine">
			<th>질문${num}</th>
			<td colspan="2"><div class="bottom_line"><input type="text" autocomplete="off" id="quest${num}" name="quest${num}" value="${quest.question}" placeholder="질문을 입력해 주세요"></div></td>
		</tr>
		<tr>
			<th class="tr_oneLine">이미지</th>
			<td colspan="2"><div class="bottom_line">
				<input type="file" id="image${num}" name="image${num}"  accept=".gif, .jpg, .png, .jpeg">
				<input type="hidden" name="o_image${num}" value="${quest.image1}">
				${quest.image1}
			</div></td>
		</tr>
		<tr id="wrap_answer">
			<th class="tr_oneLine">선택지</th>
			<td colspan="2">
				<c:forEach begin="1" end="4" var="a_num">
				<div class="input_answer">
					<input type="text" autocomplete="off" name="text${a_num}_${num}" value="${quest.answerList[a_num-1].text}" 
						placeholder="선택지${a_num}">
				</div>
				</c:forEach>
			</td>
		</tr>
		<tr class="tr_btnLine">
			<td colspan="2"><a class="delete_box" onclick="del_quest(${num})"> 질문${num} 삭제하기 </a></td>
		</tr>
	</table>
	</c:forEach>
	<input type="hidden" name="num" value="${num}">
	<input type="hidden" name="countQuest" value="${num}">
</form>

<div class="wrap_btn">
	<a class="add_box" onclick="add_tmpl_quest()"> 질문 추가하기 </a>
	<div class="clear"></div>
	<a class="submit_box" onclick="writeForm_submit(writeForm)">
		<c:if test="${mode =='insert'}">등록하기</c:if>
		<c:if test="${mode =='update'}">수정하기</c:if>
	</a>
	<c:if test="${mode =='update'}">
		<a class="delete_box" onclick="writeForm_delete(writeForm)"> 설문 삭제하기 (전체) </a>
	</c:if>
</div>
</div>