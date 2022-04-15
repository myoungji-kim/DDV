
// DatePicker
$.datepicker.setDefaults({
    dateFormat: 'yymmdd',
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    yearSuffix: '년',

});

$(function() {
	$("#enddate").datepicker();
	$("#startdate").datepicker();
} );

// 로그인하기 (+ 유효성 검사)
function go_login(f){
	var mesg="";
    var userid = f.userid.value;
    var passwd = f.passwd.value;
	
	if (passwd == "") { mesg = "비밀번호, "+mesg; }
    if (userid == "") { mesg = "아이디, "+mesg; }
    

	if (mesg == ""){
		$('[name=loginForm]').submit();
	} else {
		mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
	}
}

// 문자 구분 객체
var letterCheck = {
    'checkNum': /[0-9]/,
    'checkEngA': /[A-Z]/,
    'checkEnga': /[a-z]/,
    'checkEngAll': /[a-zA-Z]/,
    'checkSpc': /[~!@#$%^&*()_+|<>?:{}]/,
    'checkKor': /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/
}

// 회원 정보 등록 -> 아이디 유효성 검사
function userid_check(){
    var userid = $('input[name=userid]').val();
    $('input[name=chk_id]').val(false);

    // false : 입력값 X
    if (userid == "") {
        $('#id_check').text("아이디를 입력해주세요");
        

    // false : 6자 미만 입력
    } else if (userid.length < 6 || userid.length > 15) {
        $('#id_check').text("6자 이상, 15자 이하로 입력해주세요");
    

    // false : 영어 소문자 및 숫자만 가능
    } else if (letterCheck.checkSpc.test(userid) || letterCheck.checkKor.test(userid)) {
        $('#id_check').text("영어 소문자 및 숫자의 조합만 가능합니다");


    // ajax : 아이디 중복 검사
    } else {
        $.ajax({
            url:"/api/dupeId.check",
            type:"get",
            data:{"userid":userid},
            dataType:"json",
            success:function(res){
                var result = res.result;
                if (result == null){
                    $('#id_check').text("해당 아이디는 사용 가능합니다");
                    $('input[name=chk_id]').val(true);
                } else {
                    $('#id_check').text("해당 아이디는 이미 존재합니다");
                }
            }
        })
    }
}

// 회원가입 폼 -> 유효성 검사 -> Insert & Update
function editUserForm_submit(f){
    var mesg = "";
	
	var mode = f.mode.value;
	var username = f.username.value;
	var gender = f.gender.value;
	var userid = f.userid.value;
	var chk_id = f.chk_id.value;
	var passwd = f.passwd.value;
	var phone1 = f.phone1.value;
    var phone2 = f.phone2.value;
    var phone3 = f.phone3.value;
    var email1 = f.email1.value;
    var email2 = f.email2.value;
    

    // 이메일
    if (email1=="" || email2=="") { mesg = "이메일, "+mesg; }

    // 전화번호
    if ((phone2.length+phone3.length > 8) || (phone2=="" || phone3=="") 
        || letterCheck.checkEngAll.test(phone2+phone3) || letterCheck.checkSpc.test(phone2+phone3) 
        || letterCheck.checkKor.test(phone2+phone3)) {
        mesg = "전화번호, "+mesg;
    } 

    // 아이디
    if (chk_id=="false"){ mesg = "아이디, "+mesg; }

    // 성별
    if (gender==""){ mesg = "성별, "+mesg; }

    // 이름
    if (username=="" || letterCheck.checkSpc.test(username)){ mesg = "이름, "+mesg; }

    // 최종 결과
	
    if (mesg==""){
        f.submit();
		if (mode=="insert")	alert("회원 정보가 등록되었습니다");
		else if (mode=="update") alert("회원 정보가 수정되었습니다");
		
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}

function editUserForm_delete(f){
	$('input[name=mode]').val("delete");
	f.submit();
	alert("회원 정보가 삭제되었습니다");
}

// 회원가입(관리자) 폼 -> 유효성 검사 -> Insert & Update
function editAdminForm_submit(f){
    var mesg = "";
	
	var mode = f.mode.value;
	var username = f.username.value;
	var gender = f.gender.value;
	var userid = f.userid.value;
	var chk_id = f.chk_id.value;
    var email1 = f.email1.value;
    var email2 = f.email2.value;  

    // 이메일
    if (email1=="" || email2=="") { mesg = "이메일, "+mesg; }

    // 아이디
    if (chk_id=="false"){ mesg = "아이디, "+mesg; }

	// 아이디 길이 체크
	if (userid.length > 20) {mesg ="아이디 (20자 이하)";}

    // 성별
    if (gender==""){ mesg = "성별, "+mesg; }

    // 이름
    if (username=="" || letterCheck.checkSpc.test(username)){ mesg = "이름, "+mesg; }

	// 이름 길이 체크
	if (username.length > 10) {mesg ="이름 (10자 이하)";}

    // 최종 결과
    if (mesg==""){
        f.submit();
		if (mode=="insert")	alert("관리자 정보가 등록되었습니다");
		else if (mode=="update") alert("관리자 정보가 수정되었습니다");
		
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}

function editAdminForm_delete(f){
	$('input[name=mode]').val("delete");
	f.submit();
	alert("관리자 정보가 삭제되었습니다");
}

// 서베이 등록
function writeForm_submit(f){
	var mesg = "";
	
	var mode = f.mode.value;
	var title = f.title.value;
	var subtitle = f.subtitle.value;
	var enddate = f.enddate.value;
	var time = f.time.value;
    var point = f.point.value;

	////// 서베이 최소 질문 2개 이상 등록
	var countQuest = Number($('input[name=countQuest]').val());
	if (countQuest < 2) {
		alert("질문은 최소 2개 이상 등록해주세요");
		return false;
	}
	
	///// 서베이 질문 입력 + 선택지 최소 2개 이상 등록
	var temp = 1;
	var boolTemp = true;
	$("table.main").each(function(){
		var _this = $(this);
		if (temp > 1){
			_this.find("input").each(function(){
				if (f.mode.value == "insert") {
					if($(this).context.value == ""){ boolTemp = false; }
				} else if (f.mode.value == "update") {
					if($(this).context.value == "" && $(this).prop('tagName') != "INPUT"){ boolTemp = false; }
				}
			})
		}
		temp++;
	})
	if(!boolTemp){ alert("질문, 이미지, 선택지를 모두 입력해주세요"); return false; }

	////// 서베이 기본 정보 입력
    // 포인트
    if (point=="" || !letterCheck.checkNum.test(point)){ mesg = "포인트, "+mesg; }

	// 포인트 최대 1000
	if (point > 1000) {mesg ="포인트 (1000p이하), ";}

	// 소요시간
    if (time=="" || !letterCheck.checkNum.test(time)){ mesg = "소요시간, "+mesg; }
	
	// 종료일
    if (enddate==""){ mesg = "종료일, "+mesg; }

    // 소제목
    if (subtitle==""){ mesg = "소제목, "+mesg; }

	// 소제목 길이 체크
	if (subtitle.length > 50) {mesg ="소제목 (50자 이하), ";}
    
	// 제목
    if (title=="") { mesg = "제목, "+mesg; }

	// 제목 길이 체크
	if (title.length > 50) {mesg ="제목 (50자 이하), ";}

    // 최종 결과
    if (mesg==""){
        f.submit();
		if (mode=="insert")	alert("서베이 등록 완료했습니다");
		else if (mode=="update") alert("설문조사 정보를 수정했습니다");
		
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}

// 서베이 삭제
function writeForm_delete(f){
	$('input[name=mode]').val("delete");
	f.submit();
	alert("설문 조사가 삭제되었습니다");
}

// 서베이 질문 추가하기
function add_tmpl_quest(){
	var num = Number($('input[name=num]').val())+1;
	$('input[name=num]').val(num);
	$('input[name=countQuest]').val(Number($('input[name=countQuest]').val())+1);
	$("#tmpl_quest").tmpl({"num":num}).appendTo("#wrap_editSurvey form");
}

// 서베이 질문 삭제하기
function del_quest(quest_num){
	$('input[name=countQuest]').val(Number($('input[name=countQuest]').val())-1);
	var id = '#table'+quest_num;
	$(id).html("");
}

// 1:1 문의 답변 등록 폼
function editInquireForm_submit(f){
	var mesg = "";
	
	var answer = f.answer.value;
	if (answer == ""){ mesg = "답변, "+mesg; } 
	
	// 답변 길이 체크
	if (answer.length > 50) {mesg ="답변 (50자 이하)";}

	// 최종 결과
    if (mesg==""){
        f.submit();
		alert("답변이 등록되었습니다");
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}

// My Poll 등록하기
function writePollForm_submit(f){
    var mesg  = "";
    var question = f.question.value;
    var image = f.image.value;
    var text1 = f.text1.value;
    var text2 = f.text2.value;
	var text3 = f.text3.value;
	var text4 = f.text4.value;
	var text5 = f.text5.value;
    var option1 = f.option1.value;
    var enddate = f.enddate.value;

    // 종료일
    if (enddate==""){ mesg = "종료일, "+mesg; }

    // 옵션1
    if (option1==""){ mesg = "전체 공개 여부, "+mesg; }

    // 선택지
    if (text2==""){ mesg = "선택지2, "+mesg; }
    if (text1==""){ mesg = "선택지1, "+mesg; }

	// 선택지 길이 체크
	if (text1.length > 50) {mesg ="선택지1 (50자 이하)";}
	if (text2.length > 50) {mesg ="선택지2 (50자 이하)";}
	if (text3.length > 50) {mesg ="선택지3 (50자 이하)";}
	if (text4.length > 50) {mesg ="선택지4 (50자 이하)";}
	if (text5.length > 50) {mesg ="선택지5 (50자 이하)";}

    // 이미지
    if (image=="" && f.mode.value=="insert"){ mesg = "이미지, "+mesg; }

    // 질문
    if (question==""){ mesg = "질문, "+mesg; }

	// 질문 길이 체크
	if (question.length > 50) {mesg ="질문 (50자 이하)";}

    // 최종 결과 
    if (mesg==""){ 
        f.submit();
		alert("퀵폴 정보를 저장했습니다");
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}

// 퀵폴 삭제
function writePollForm_delete(f){
    f.mode.value = "delete";
	f.submit();
	alert("퀵폴이 삭제되었습니다");
}


// FAQ 등록 및 수정하기
function writeFAQForm_submit(f){
    var mesg  = "";
    var title = f.title.value;
    var text = f.text.value;

    // 제목
    if (title==""){ mesg = "제목, "+mesg; }

	// 제목 길이 초과 (30 byte)
	if (title.length > 30) {mesg ="제목 (30자 이하)";}
 
	// 본문
    if (text==""){ mesg = "본문, "+mesg; }

	// 본문 길이 초과 (1500 byte)
	if (title.length > 1500) {mesg ="본문 (1500자 이하)";}

    // 최종 결과 
    if (mesg==""){ 
        f.submit();
		alert("FAQ 정보를 저장했습니다");
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}

// FAQ 삭제
function writeFAQForm_delete(f){
    f.mode.value = "delete";
	f.submit();
	alert("FAQ가 삭제되었습니다");
}

// 검색하기
function adminSearch(f){
	var cate = f.category.value;

	if (cate == "member") {
		f.action = "/admin/userList";
	} else if (cate == "admin") {
		f.action = "/admin/adminList";
	} else if (cate == "inquire") {
		f.action = "/admin/inquireList";
	} else if (cate == "survey") {
		f.action = "/admin/surveyList";
	} else if (cate == "poll") {
		f.action = "/admin/pollList";
	} else if (cate == "faq") {
		f.action = "/admin/FAQList";
	}
	
	f.submit();
}

// 날짜별 서베이/퀵폴 검색
function searchAnalyze(f){
	var cate = f.category.value;
	var startdate = f.startdate.value;
	var enddate = f.enddate.value;
	
	if (startdate == "" || enddate == ""){
		alert("시작일과 종료일 모두 선택해주세요");
		return false;
	}
	
	if (cate == "survey") {
		f.action = "/admin/surveyAnalyze";
	} else if (cate == "poll") {
		f.action = "/admin/pollAnalyze";
	}
	f.submit();
}
