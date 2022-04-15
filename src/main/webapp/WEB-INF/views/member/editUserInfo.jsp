<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script type="text/javascript">
//선택한 이메일 자동 insert
function f_emailSelect(obj){
	$('[name=email2]').val(obj.value);
}
</script>

<c:choose>
	<c:when test="${not empty userInfo}">
		<c:set var="mode" value="update"/>
		<c:set var="m_idx" value="${userInfo.m_idx}"/>
		<c:set var="userid" value="${userInfo.userid}"/>
		<c:set var="passwd" value="${userInfo.passwd}"/>
		<c:set var="username" value="${userInfo.username}"/>
		<c:set var="gender" value="${userInfo.gender}"/>
		<c:set var="phone1" value="${userInfo.phone1}"/>
		<c:set var="phone2" value="${userInfo.phone2}"/>
		<c:set var="phone3" value="${userInfo.phone3}"/>
		<c:set var="email1" value="${userInfo.email1}"/>
		<c:set var="email2" value="${userInfo.email2}"/>
		<c:set var="joindate" value="${userInfo.joindate}"/>
	</c:when>
	<c:when test="${empty userInfo}">
		<c:set var="mode" value="insert"/>
		<c:set var="m_idx" value=""/>
		<c:set var="userid" value=""/>
		<c:set var="passwd" value=""/>
		<c:set var="username" value=""/>
		<c:set var="gender" value=""/>
		<c:set var="phone1" value=""/>
		<c:set var="phone2" value=""/>
		<c:set var="phone3" value=""/>
		<c:set var="email1" value=""/>
		<c:set var="email2" value=""/>
		<c:set var="joindate" value=""/>
	</c:when>
</c:choose>

<div id="wrap_editUser">
	<div id="main">
		<div class="title">
			<span> 
				<c:if test="${mode =='insert'}">사용자 등록</c:if>
				<c:if test="${mode =='update'}">사용자 정보 수정</c:if>	
			</span>
		</div>
		<form action="/admin/editUserInfo.action" id="editUser_form" name="editUserForm" method="post">
			<input type="hidden" name="mode" value="${mode}">
			<table class="editUser_table">
				<c:if test="${mode =='update'}">
				<tr>
					<td class="item_th">
						<div><span>IDX</span></div>
					</td>
					<td class="item_box">
						<div><input readonly="readonly" id="m_idx" name="m_idx" value="${m_idx}"></div>
					</td>
				</tr>
				</c:if>	
				<tr>
					<td class="item_th">
						<div><span>이름</span></div>
					</td>
					<td class="item_box">
						<div><input type="text" id="username" name="username" value="${username}"></div>
					</td>
				</tr>
				<tr>
					<td class="item_th">
						<div><span>성별</span></div>
					</td>
					<td class="item_box">
						<div class="radio"><input type="radio" name="gender" value="M" data-role="none" <c:if test="${gender == 'M'}">checked</c:if>><span>남</span></div>
						<div class="radio"><input type="radio" name="gender" value="W" data-role="none" <c:if test="${gender == 'W'}">checked</c:if>><span>여</span></div>
					</td> 
				</tr>
				<tr>
					<td class="item_th">
						<div><span>아이디</span></div>
					</td>
					<td class="item_box">
						<div><input type="text" id="userid" name="userid" onkeyup="userid_check()" value="${userid}" placeholder="아이디를 입력하세요" autocomplete="off"></div>
					</td>
				</tr>
				<tr>
					<c:if test="${mode == 'insert'}">
						<td><input type="hidden" id="chk_id" name="chk_id" value="false"></td>
					</c:if>
					<c:if test="${mode == 'update'}">
						<td><input type="hidden" id="chk_id" name="chk_id" value="true"></td>
					</c:if>
					<td><span id="id_check" class="id_check"><c:if test="${mode == 'insert'}">아이디를 입력해주세요</c:if></span></td>
				</tr>
				<tr>
					<td class="item_th">
						<div><span>비밀번호</span></div>
					</td>
					<td class="item_box">
						<div><input type="text" id="passwd" name="passwd" value="${passwd}"></div>
					</td>
				</tr>
				<tr>
					<td class="item_th"><div>전화번호</div></td>
					<td class="td_phone">
						<select name="phone1">
						  <option value="010">010</option>
						  <option value="011">011</option>
						</select>
						<span class="link">-</span>
						<input type="text" name="phone2" value="${phone2 }" autocomplete="off">
						<span class="link">-</span>
						<input type="text" name="phone3" value="${phone3 }" autocomplete="off">
					</td>
				</tr>
				<tr>
					<td class="item_th"><div>이메일</div></td>
					<td class="td_email">
						<input type="text" name="email1" id="email1" value="${email1}" autocomplete="off">
						<span class="link">@</span>
					    <input type="text" name="email2" id="email2" value="${email2}" autocomplete="off">
					    <select id="emailSelect" onchange="f_emailSelect(this)">
					    	<option value="">직접입력</option>
							<option value="daum.net">daum.net</option>
					        <option value="naver.com">naver.com</option>
					    </select>
					</td>
				</tr>
				<c:if test="${mode =='update'}">
				<tr>
					<td class="item_th">
						<div><span>가입일</span></div>
					</td>
					<td class="item_box">
						<div><input readonly="readonly" id="joindate" name="joindate" value="${joindate }"></div>
					</td>
				</tr>
				</c:if>
			</table>
			<div class="wrap_btn">
				<a class="submit_box" onclick="editUserForm_submit(editUserForm)">
					<c:if test="${mode=='insert'}">등록하기</c:if>
					<c:if test="${mode=='update'}">수정하기</c:if>
				</a>
				<c:if test="${mode=='update'}">
					<a class="delete_box" onclick="editUserForm_delete(editUserForm)"> 회원 삭제하기 </a>
				</c:if>
			</div>
		</form>
	</div>
</div>
