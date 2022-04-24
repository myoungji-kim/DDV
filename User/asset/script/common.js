
var loginSession;
var APIURL = "http://ddingji.cafe24.com";

var loading = false; // 무한 로딩 방지 용
var scrollPage = 1; // 스크롤 페이지
var lastPage = false; // 마지막 페이지인지 확인
var nowPage = "survey";

$(document).ready(function(){
    surveyList(scrollPage);

    $(".beforeAfterLogin").html("");
    $("#ajax_beforeLogin").tmpl().appendTo("#aside .beforeAfterLogin");

    // Scroll
    $(window).scroll(function(){
        var scrollNow = $(window).scrollTop();

        if (nowPage == "survey") {
            if (scrollNow + $(window).height() + 100 >= $('#sv_wrap').height()){
                surveyList(scrollPage);
            }
        } else if (nowPage == "poll"){  
            if (scrollNow + $(window).height() + 100 >= $('#poll').height()){
                pollList(scrollPage);
            }
        } else if (nowPage == "mypoll"){
            if (scrollNow + $(window).height() + 100 >= $('#mypoll').height()){
                mypollList(scrollPage);
            }
        }
    })

    // 검색 폼 자동 submit 방지
    $('input[type="text"]').keydown(function() {
      if (event.keyCode === 13) {
        event.preventDefault();
      };
    });
})


// 검색하기
function go_search(){
    if (nowPage=="survey"){
        open_survey();
    } else if (nowPage=="poll"){
        open_poll();
    } else if (nowPage=="mypoll"){
        open_mypoll();
    }
}


//////////////// 서베이 관련

// 메인 화면 서베이 리스트
function surveyList(page){
    if (!loading){
        loading = true;
        $("#ajax_loading").tmpl().appendTo("#mainPage > #sv_wrap");
        $.ajax({
            url:APIURL+"/api/surveyList.do",
            type:"get",
            data:{  
                    "page":page,
                    "searchKey":$('input[name=searchKey]').val()
                },
            dataType:"json",
            success:function(res){
                $(".loading").remove();
                $("#ajax_survey").tmpl(res.surveyList).appendTo("#mainPage > #sv_wrap > .sv_ct");
                loading = false;
                scrollPage += 1;
            }
        })
    }
}

// 서베이 콘텐츠 눌렀을 때 (for 참여)
function surveyJoinStart(s_idx){
	$("#surveyJoinPage_Start > .survey_start").html("");
    $("#ajax_loading").tmpl().appendTo("#mainPage > #sv_wrap");
    $.ajax({
        url:APIURL+"/api/surveyJoin.do",
        type:"get",
        data:{"s_idx":s_idx},
        dataType:"json",
        success:function(res){
            $(".loading").remove();
            $("#ajax_surveyJoinStart").tmpl(res.quest).appendTo("#surveyJoinPage_Start > .survey_start");
        }
    })
    $.mobile.changePage("#surveyJoinPage_Start");
}

// 서베이 시작 화면에서 취소 눌렀을 때
function reset_surveyJoin(){
    $("#surveyJoinPage_Start > .survey_start").html("");
    history.back();
}




// 서베이 참여 질문 나오기 시작

var s_idx; // 서베이 고유번호
var questLength; // 총 질문 갯수
var sliderCtx; // bxSlider
var pickAnswer; // 사용자의 답변 모음

function surveyAlreadyJoinCheck(s_idx){
    if (loginSession == null){
        alert("로그인 후 이용해 주세요");
        return false;
    }

    $.ajax({
        url:APIURL+"/api/surveyAlreadyJoin.check",
        type:"get",
        data:{ 
                "s_idx":s_idx,
                "m_idx":loginSession.m_idx
            },
        dataType:"json",
        success:function(res){
            if (res.join != null){ 
                alert("해당 서베이는 이미 참여하셨습니다");
                return false;
            } else {
                surveyJoinIng(s_idx);
            }
        }
    })
}

function surveyJoinIng(s_idx){
    $("#surveyJoinPage_Ing #sv_ing .survey_slider").html("");
    $.mobile.changePage("#surveyJoinPage_Ing");
    $("#ajax_loading").tmpl().appendTo("#surveyJoinPage_Ing > .survey_slider");
    $.ajax({
        url:APIURL+"/api/surveyJoinIng.do",
        type:"get",
        data:{ "s_idx": s_idx },
        dataType:"json",
        success:function(res){
            s_idx = res.quest[0].s_idx;
            questLength = res.quest.length;
            pickAnswer = new Array(questLength);
            $(".loading").remove();

            $("#surveyJoinPage_Ing #sv_ing .num_wrap").html(`
                            <span>1</span>
                            <span>/</span>
                            <span>`+questLength+`</span>`);

            $("#ajax_SurveyJoinIng").tmpl(res.quest).appendTo("#surveyJoinPage_Ing #sv_ing .survey_slider");


            $('.slider-next-btn').unbind("click"); // 이전 이벤트 삭제 (이벤트 중복 실행 방지)
            $('.slider-prev-btn').unbind("click"); 

            // 제출 버튼 css 초기화 (추후 보완 예정)
            $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn').css("background-color", "#FFA178");
            $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn').css("color", "#fff");
            $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn').css("border", "none");
            $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn span').text("다음");


            setTimeout(function(){
                if (sliderCtx) sliderCtx.destroySlider();
                sliderCtx = $('.survey_slider').bxSlider({

                    mode: 'horizontal',  // 슬라이드 방향 설정
                    speed: 500,          // 이동 속도
                    pager: false,        // 현재 위치 페이징 표시 여부
                    moveSlides: 1,       // 슬라이드 이동 개수
                    auto: false,         // 자동 실행 여부
                    controls: true,      // 이전 다음 버튼 노출 여부
                    infiniteLoop: false,
                    touchEnabled: false,
                    responsive: false,
                    useCSS: false,

                    // 슬라이드 전환 되기 직전에 실행하는 메서드
                    onSlideBefore: function($slideElement, oldIndex, newIndex){
                        var current_index = parseInt(newIndex + 1);
                        var total = sliderCtx.getSlideCount();  
                        $("#sv_ing .num_wrap").html(`
                            <span>`+current_index+`</span>
                            <span>/</span>
                            <span>`+total+`</span>
                        `);
                    },
                    onSliderLoad: function(){
                        $(".survey_slider").css("visibility", "visible").animate({
                            opacity:1
                        }); 
                    }
                });
            

            // 아래는 이미지버튼을 클릭했을 때 이미지를 넘어가게 하기 위한 이벤트
            $('.slider-next-btn').click(function(){  // 다음이미지 클릭 시
                if (sliderCtx.getCurrentSlide() == sliderCtx.getSlideCount()-2){
                    // 마지막 페이지로 넘어갈 때
                    $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn').css("background-color", "#fff");
                    $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn').css("color", "red");
                    $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn').css("border", "2px solid red");
                    $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn span').text("제출");
                } else if (sliderCtx.getCurrentSlide() == (sliderCtx.getSlideCount()-1)){
                    var allcheck = true;
                    for (var i=1; i<=questLength; i++){
                        if ($('#surveyJoinPage_Ing input[name=checking'+i+']').val() == "false"){
                            allcheck = false;
                        }
                    }
                    if (allcheck){
                        submit_FinishSurvey(s_idx);
                    } else {
                        alert("모든 문항에 응답해주세요");
                    }
                }
                $('.bx-next').trigger("click");
              });
            $('.slider-prev-btn').click(function(){ // 이전 이미지 클릭 시
                if (sliderCtx.getCurrentSlide() == 0) {
                    // 취소
                    $("#surveyJoinPage_Ing #sv_ing .survey_slider").html("");
                    history.back();
                } else if (sliderCtx.getCurrentSlide() == sliderCtx.getSlideCount()) {
                    $('#surveyJoinPage_Ing .btn_wrap').html(`
                            <a class="slider-prev-btn"><span>이전</span></a>
                            <a class="slider-next-btn"><span>다음</span></a>
                            <div class="reset"></div>
                    `);
                    $('.bx-prev').trigger("click");
                } else {
                    $('.bx-prev').trigger("click");
                }

                $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn').css("background-color", "#FFA178");
                $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn').css("color", "#fff");
                $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn').css("border", "none");
                $('#surveyJoinPage_Ing .btn_wrap .slider-next-btn span').text("다음");
            }); 

            }, 500);
        }
    })
}

// 서베이 선택지 저장
function surveyPickAnswer(q_idx, a_idx){
    for (var i=1; i<=4; i++){
        $("#surveyJoinPage_Ing #sv_ing .survey_slider li:nth-of-type("+q_idx+") .select_box div:nth-of-type("+i+") a").css("border", "1px solid #5C5C5C");
        $("#surveyJoinPage_Ing #sv_ing .survey_slider li:nth-of-type("+q_idx+") .select_box div:nth-of-type("+i+") a span").css("color", "#5C5C5C");
    }
    pickAnswer[q_idx-1] = a_idx;

    var aTag = "#surveyJoinPage_Ing #sv_ing .survey_slider li:nth-of-type("+q_idx+") .select_box div:nth-of-type("+a_idx+") a";

    $(aTag).css("border","1px solid #FF8A57");
    $(aTag+" span").css("color", "#FF8A57");

    $('#surveyJoinPage_Ing input[name=checking'+q_idx+']').val(true);
}


// 서베이 참여 완료 - 1
function submit_FinishSurvey(s_idx){
    $.ajax({
        url:APIURL+"/api/surveyJoin.do",
        type:"get",
        data:{ "s_idx": s_idx },
        dataType:"json",
        success:function(res){
            $("#surveyJoinPage_End #sv_end .surveyEndInfo").html("");
            $("#ajax_SurveyJoinEnd").tmpl(res.quest).appendTo("#surveyJoinPage_End #sv_end .surveyEndInfo");
            submit_SubmitSurvey(s_idx, res.quest.POINT);
        }
    })
}

// 서베이 참여 완료 - 2 (post)
function submit_SubmitSurvey(s_idx, point){
    $.ajax({
        url:APIURL+"/api/surveySubmit.action",
        type:"post",
        traditional: true,
        data:{ 
                "s_idx": s_idx,
                "pickAnswer": pickAnswer,
                "m_idx": loginSession.m_idx,
                "point": point
            },
        success:function(res){
            $.mobile.changePage("#surveyJoinPage_End");
        }
    })
}




//////////////// Member 관련

// 문자 구분 객체
var letterCheck = {
    'checkNum': /[0-9]/,
    'checkEngA': /[A-Z]/,
    'checkEnga': /[a-z]/,
    'checkEngAll': /[a-zA-Z]/,
    'checkSpc': /[~!@#$%^&*()_+|<>?:{}]/,
    'checkKor': /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/,
    'emogi': /(?:[\u2700-\u27bf]|(?:\ud83c[\udde6-\uddff]){2}|[\ud800-\udbff][\udc00-\udfff]|[\u0023-\u0039]\ufe0f?\u20e3|\u3299|\u3297|\u303d|\u3030|\u24c2|\ud83c[\udd70-\udd71]|\ud83c[\udd7e-\udd7f]|\ud83c\udd8e|\ud83c[\udd91-\udd9a]|\ud83c[\udde6-\uddff]|\ud83c[\ude01-\ude02]|\ud83c\ude1a|\ud83c\ude2f|\ud83c[\ude32-\ude3a]|\ud83c[\ude50-\ude51]|\u203c|\u2049|[\u25aa-\u25ab]|\u25b6|\u25c0|[\u25fb-\u25fe]|\u00a9|\u00ae|\u2122|\u2139|\ud83c\udc04|[\u2600-\u26FF]|\u2b05|\u2b06|\u2b07|\u2b1b|\u2b1c|\u2b50|\u2b55|\u231a|\u231b|\u2328|\u23cf|[\u23e9-\u23f3]|[\u23f8-\u23fa]|\ud83c\udccf|\u2934|\u2935|[\u2190-\u21ff])/g
}


// 회원가입 -> 아이디 유효성 검사
function userid_check(){
    var userid = $('#memberJoinPage input[name=userid]').val();
    $('#memberJoinPage input[name=chk_id]').val(false);

    // false : 입력값 X
    if (userid == "") {
        $('#memberJoinPage #id_check').text("아이디를 입력해주세요");
        

    // false : 6자 미만 입력
    } else if (userid.length < 6 || userid.length > 15) {
        $('#memberJoinPage #id_check').text("6자 이상, 15자 이하로 입력해주세요");
    

    // false : 영어 소문자 및 숫자만 가능
    } else if (letterCheck.checkSpc.test(userid) || letterCheck.checkKor.test(userid)) {
        $('#memberJoinPage #id_check').text("영어 소문자 및 숫자의 조합만 가능합니다");

    // false : 이모지 입력 방지
    } else if (letterCheck.emogi.test(userid)) {
        $('#memberJoinPage #id_check').text("특수문자는 입력할 수 없습니다");

    // ajax : 아이디 중복 검사
    } else {
        $.ajax({
            url:APIURL+"/api/dupeId.check",
            type:"get",
            data:{"userid": userid},
            dataType:"json",
            success:function(res){
                var result = res.result;
                if (result == null){
                    $('#memberJoinPage #id_check').text("해당 아이디는 사용 가능합니다");
                    $('#memberJoinPage input[name=chk_id]').val(true);
                } else {
                    $('#memberJoinPage #id_check').text("해당 아이디는 이미 존재합니다");
                }
            }
        })
    }
}


// 회원가입 -> 비밀번호 재확인
function passwd_check(pageId){

	// 회원가입
	var passwd1 = $('#'+pageId+' input[name=passwd1]').val();
    var passwd2 = $('#'+pageId+' input[name=passwd2]').val();
    $('#'+pageId+' input[name=chk_pw]').val(false);

    if (passwd1 != passwd2){
        $('#'+pageId+' .pw_check').text("비밀번호가 일치하지 않습니다");
    } else if (passwd1.length < 6 || passwd1.length > 15) {
        $('#'+pageId+' .pw_check').text("6자 이상, 15자 이하로 입력해주세요");
    } else if (letterCheck.checkKor.test(passwd1)) {
        $('#'+pageId+' .pw_check').text("비밀번호에는 한글을 입력할 수 없습니다");
    } else if (letterCheck.emogi.test(passwd1)) {
        $('#'+pageId+' .pw_check').text("특수문자는 입력할 수 없습니다");
    } else {
        $('#'+pageId+' .pw_check').text("비밀번호 일치 및 사용 가능합니다");
        $('#'+pageId+' input[name=chk_pw]').val(true);
    }

}

// 회원가입 폼 -> 유효성 검사 -> Insert
function member_join(f){
    var mesg = "";

    var email1 = f.email1.value;
    var email2 = f.email2.value;
    var phone1 = f.phone1.value;
    var phone2 = f.phone2.value;
    var phone3 = f.phone3.value;
    var chk_pw = f.chk_pw.value;
    var passwd = f.passwd1.value;
    var chk_id = f.chk_id.value;
    var userid = f.userid.value;
    var gender = f.gender.value;
    var username = f.username.value;

    // 이메일
    if (email1=="" || email2=="") { mesg = "이메일, "+mesg; }

    // 전화번호
    if ((phone2=="" || phone3=="") 
        || letterCheck.checkEngAll.test(phone2+phone3) 
        || letterCheck.checkSpc.test(phone2+phone3) 
        || letterCheck.checkKor.test(phone2+phone3)) {
        mesg = "전화번호, "+mesg;
    } 

    // 비밀번호
    if (chk_pw=="false"){ mesg = "비밀번호, "+mesg; }

    // 아이디
    if (chk_id=="false"){ mesg = "아이디, "+mesg; }

    // 성별
    if (gender==""){ mesg = "성별, "+mesg; }

    // 이름
    if (username=="" || letterCheck.checkSpc.test(username)){ mesg = "이름, "+mesg; }

    // 최종 결과 
    if (mesg==""){ 
        $.ajax({ 
            url:APIURL+"/api/memberInsert.action", 
            type:"post", 
            data: $("[name=memberJoinForm]").serialize(), 
            success:function(res){ 
                alert("회원가입에 성공했습니다. 만나서 반가워요!"); 
                $.mobile.changePage("#loginPage");
                $('#member_findIDForm')[0].reset();
            } 
        }) 
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}


// 아이디 찾기
function find_id(f){
    var mesg = "";
    var phone1 = f.phone1.value;
    var phone2 = f.phone2.value;
    var phone3 = f.phone3.value;
    var username = f.username.value;

    // 전화번호
    if ((phone2=="" || phone3=="") 
        || letterCheck.checkEngAll.test(phone2+phone3) || letterCheck.checkSpc.test(phone2+phone3) 
        || letterCheck.checkKor.test(phone2+phone3) || letterCheck.emogi.test(phone2+phone3)) {
        mesg = "전화번호, "+mesg;
    } 

    // 이름
    if (username=="" || letterCheck.checkSpc.test(username)){ mesg = "이름, "+mesg; }
 
    // 최종 결과 
    if (mesg==""){ 
        $.ajax({ 
            url:APIURL+"/api/findID.action", 
            type:"post", 
            data: $("[name=findIDForm]").serialize(), 
            dataType: "json",
            success:function(res){ 
                if (res.userid != null) {
                    alert("회원님의 아이디는 "+res.userid.userid+" 입니다."); 
                } else {
                    alert("일치하는 정보가 없습니다.");
                }
            } 
        }) 
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}

// 로그인
function login(f){
    var mesg = "";
    var userid = f.userid.value;
    var passwd = f.passwd.value;

    if (passwd == "") { mesg = "비밀번호, "+mesg; }
    if (userid == "") { mesg = "아이디, "+mesg; }

    // 최종 결과
    if (mesg==""){
        $.ajax({
            url:APIURL+"/api/login.action",
            type:"post",
            data:{  
                    "userid":userid,  
                    "passwd":passwd,   
                },
            success:function(res){
                if (res.login != null){
                    loginSession = res.login; // 전역변수에 로그인 값 저장
                    open_menu();
                    $.mobile.changePage("#mainPage");
                } else {
                    alert("아이디와 비밀번호가 올바르지 않습니다");
                }
            } 
        })
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}

// 마이페이지
function open_myPage(){
	$("#myInfoPage").html("");
	if (!loginSession) {
		// 로그인 X
		$.mobile.changePage("#loginPage");
	} else {
		// 로그인 O
		$.ajax({
            url:APIURL+"/api/myInfoPage.do",
            type:"get",
            data:{ "m_idx":loginSession.m_idx },
            dataType:"json",
            success:function(res){
				$("#ajax_myInfoPage").tmpl(res.myInfo).appendTo("#myInfoPage");
                $(":radio[name='gender']").each(function() {
                    var $this = $(this);
                    if($this.val() == res.myInfo.GENDER)
                    $this.attr('checked', true);
                });

				// $("#page_join input:radio[name='gender'][value='"+res.myInfo.GENDER+"']").attr("checked", true);
				$.mobile.changePage("#myInfoPage");
            } 
        })
	}
}

// 내 정보 유효성 검사 및 수정
function myInfopage_save(f){
	var mesg = "";

	var m_idx = f.m_idx.value;
    var email1 = f.email1.value;
    var email2 = f.email2.value;
    var phone1 = f.phone1.value;
    var phone2 = f.phone2.value;
    var phone3 = f.phone3.value;
    var chk_pw = f.chk_pw.value;
    var passwd = f.passwd1.value;
    var userid = f.userid.value;
    var gender = f.gender.value;
    var username = f.username.value;

    // 이메일
    if (email1=="" || email2=="") { mesg = "이메일, "+mesg; }

    // 전화번호
    if ((phone2.length+phone3.length > 8) || (phone2=="" || phone3=="") 
        || letterCheck.checkEngAll.test(phone2+phone3) 
        || letterCheck.checkSpc.test(phone2+phone3) 
        || letterCheck.checkKor.test(phone2+phone3)) {
        mesg = "전화번호, "+mesg;
    } 

    // 비밀번호
    if (chk_pw=="false"){ mesg = "비밀번호, "+mesg; }

    // 성별
    if (gender==""){ mesg = "성별, "+mesg; }

    // 이름
    if (username=="" || letterCheck.checkSpc.test(username)){ mesg = "이름, "+mesg; }


    // 최종 결과
    if (mesg==""){
        $.ajax({
            url:APIURL+"/api/memberUpdate.action",
            type:"post",
            data:{	"m_idx":m_idx,
                    "passwd":passwd,
                    "gender":gender,
                    "phone1":phone1,
                    "phone2":phone2,
                    "phone3":phone3,
                    "email1":email1,
                    "email2":email2,
                    "username":username   },
            success:function(res){
                alert("회원정보 수정 완료");
            } 
        })
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}

// 내 포인트 기록 보기 (마이포인트)
function open_pointPage(){
	if (!loginSession) {
		// 로그인 X
		$.mobile.changePage("#loginPage");
	} else {
		// 로그인 O 
		$.ajax({
            url:APIURL+"/api/pointPage.do",
            type:"get",
            data:{ "m_idx": loginSession.m_idx },
            dataType:"json",
            success:function(res){
            	$("#pointPage > #point > .point_wrap").html("");
            	$("#pointPage > #point > .record_wrap > .ct_wrap").html("");

                $("#ajax_sumPoint").tmpl({"sumPoints":res.sumPoints}).appendTo("#pointPage #point .point_wrap");
				$("#ajax_pointList").tmpl(res.myPoint).appendTo("#pointPage > #point > .record_wrap > .ct_wrap");
				
				$.mobile.changePage("#pointPage");
            }
        })
	}
}

// FAQ 페이지 리스트 보기
function open_FAQPage(){
    $.ajax({
        url:APIURL+"/api/FAQPage.do",
        type:"get",
        dataType:"json",
        success:function(res){
            $("#FAQPage > #FAQ > .record_wrap > .ct_wrap").html("");
            $("#ajax_FAQList").tmpl(res.FAQList).appendTo("#FAQPage > #FAQ > .record_wrap > .ct_wrap");
            
            $.mobile.changePage("#FAQPage");
        }
    })
}

// FAQ 자세히 보기
function open_oneFAQ(f_idx){
    $.ajax({
        url:APIURL+"/api/selectOneFAQ.do",
        type:"get",
        data:{ "f_idx": f_idx },
        dataType:"json",
        success:function(res){
            $("#FAQOnePage #FAQ").html("");
            $("#ajax_FAQOne").tmpl(res.FAQInfo).appendTo("#FAQOnePage #FAQ");
            
            $.mobile.changePage("#FAQOnePage");
        }
    })
}

// 내 1:1 문의 리스트 보기
function open_inquirePage(){
    if (!loginSession) {
        // 로그인 X
        $.mobile.changePage("#loginPage");
    } else {
        // 로그인 O 
        $.ajax({
            url:APIURL+"/api/inquirePage.do",
            type:"get",
            data:{ "m_idx": loginSession.m_idx },
            dataType:"json",
            success:function(res){
                $("#inquirePage > #inquire > .record_wrap > .ct_wrap").html("");
                $("#ajax_inquireList").tmpl(res.inquireList).appendTo("#inquirePage #inquire > .record_wrap > .ct_wrap");
                
                $.mobile.changePage("#inquirePage");
            }
        })
    }
}


// 내 1:1 문의 내역 자세히 보기
function open_oneInquire(i_idx){
    if (!loginSession) {
        // 로그인 X
        $.mobile.changePage("#loginPage");
    } else {
        // 로그인 O 
        $.ajax({
            url:APIURL+"/api/selectOneInquire.do",
            type:"get",
            data:{ "i_idx": i_idx },
            dataType:"json",
            success:function(res){
                $("#inquireOnePage #inquire").html("");
                $("#ajax_inquireOne").tmpl(res.inquireInfo).appendTo("#inquireOnePage > #inquire");
                
                $.mobile.changePage("#inquireOnePage");
            }
        })
    }
}


// 내 1:1 문의 질문 등록하기
function upload_inquire(f){
    var mesg = "";
    var question = f.question.value;

    // 질문
    if (question==""){ mesg = "질문, "+mesg; }

    // 질문 이모지 여부
    if (letterCheck.emogi.test(question)) {mesg ="질문 (이모지 사용 불가), ";}

    // 최종 결과
    if (mesg==""){
        $.ajax({
            url:APIURL+"/api/uploadInquire.action",
            type:"post",
            data:{  
                    "question": question,  
                    "m_idx": loginSession.m_idx   
                },
            success:function(res){
                alert("질문이 정상적으로 등록되었습니다");
                open_inquirePage();
            } 
        })
    } else {
        mesg = mesg.slice(0, -2);
        alert(mesg + "을(를) 확인해주세요");
        return false;
    }
}


// My Poll 등록하기
function upload_poll(f){

    var mesg  = "";
    var p_idx = f.p_idx.value;
    var question = f.question.value;
    var o_mainimg = f.o_mainimg.value;
    var image = f.image.value;
    var text1 = f.text1.value;
    var text2 = f.text2.value;
    var text3 = f.text3.value;
    var text4 = f.text4.value;
    var text5 = f.text5.value;
    var option1 = f.option1.value;
    var enddate = f.enddate.value;

    var formData = new FormData(); 
    formData.append("p_idx", p_idx); 
    formData.append("question", question);
    formData.append("o_mainimg", o_mainimg); 
    formData.append("image", image);
    formData.append("text1", text1);
    formData.append("text2", text2);
    formData.append("text3", text3);
    formData.append("text4", text4);
    formData.append("text5", text5);
    formData.append("option1", option1);
    formData.append("enddate", enddate);
    formData.append("file", f.file.files[0]);
    formData.append("m_idx", loginSession.m_idx);

    // 종료일
    if (enddate==""){ mesg = "종료일, "+mesg; }

    // 옵션1
    if (option1==""){ mesg = "전체 공개 여부, "+mesg; }

    // 선택지
    if (text2==""){ mesg = "선택지2, "+mesg; }
    if (text1==""){ mesg = "선택지1, "+mesg; }

    // 선택지 이모지 여부
    if (letterCheck.emogi.test(text1)) {mesg ="선택지1 (이모지 사용 불가), ";}
    if (letterCheck.emogi.test(text2)) {mesg ="선택지2 (이모지 사용 불가), ";}
    if (letterCheck.emogi.test(text3)) {mesg ="선택지3 (이모지 사용 불가), ";}
    if (letterCheck.emogi.test(text4)) {mesg ="선택지4 (이모지 사용 불가), ";}
    if (letterCheck.emogi.test(text5)) {mesg ="선택지5 (이모지 사용 불가), ";}

    // 이미지
    if (image==""){ mesg = "이미지, "+mesg; }

    // 질문
    if (question==""){ mesg = "질문, "+mesg; }

    // 질문 이모지 여부
    if (letterCheck.emogi.test(question)) {mesg ="질문 (이모지 사용 불가), ";}

    // 최종 결과 
    if (f.alreadySubmit.value=="no"){
        if (mesg==""){ 
            f.alreadySubmit.value="yes";
            $.ajax({ 
                url:APIURL+"/api/uploadPoll.action", 
                type:"post", 
                data: formData, 
                enctype: 'multipart/form-data', // for file-upload 
                processData: false, // for file-upload 
                contentType: false, // for file-upload 
                success:function(res){
                    alert("퀵폴이 정상적으로 등록되었습니다");
                    $.mobile.changePage("#mainPage");
                    open_mypoll();
                } 
            })
        } else {
            mesg = mesg.slice(0, -2);
            alert(mesg + "을(를) 확인해주세요");
            return false;
        }
    } else {
        alert("등록 중입니다. 잠시만 기다려주세요.");
        return false;
    }
    
}


function delete_poll(p_idx){
    $.ajax({
        url:APIURL+"/api/deletePoll.action",
        type:"post",
        data:{ "p_idx": p_idx },
        success:function(res){
            alert("퀵폴이 삭제되었습니다");
            $.mobile.changePage("#mainPage");
            open_mypoll();
        } 
    })
}

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
  $("#mypoll_fromDate").datepicker();
  $("#mypoll_toDate").datepicker();
  $("#refund_fromDate").datepicker();
  $("#refund_toDate").datepicker();
  $("#refund_endSurvey").datepicker();
  $("#poll_enddate").datepicker();
} );

var menu = false;
function open_menu(){

	// css 효과
	if (!menu){

        // 로그인 유무에 따라 메뉴에 정보 변경
        $(".beforeAfterLogin").html("");
        $("#aside .beforeAfterLogout").html("");
        if (!loginSession){
            // 로그인 X
            $("#ajax_beforeLogin").tmpl().appendTo("#aside .beforeAfterLogin");
        } else {
            // 로그인 O
            $("#ajax_afterLogin").tmpl(loginSession).appendTo("#aside .beforeAfterLogin");
            $("#ajax_logout").tmpl().appendTo("#aside .beforeAfterLogout");
        }
        
		$("#aside-black").css("display", "block");
		$("#aside-black ").fadeTo('500', "50%");
		$("#aside").animate({
			"left" : "0px"
		}, 400);
		menu = true;
	} else {
		$("#aside-black ").fadeTo('slow', "0%");
		$("#aside-black ").css("display", "none");
		$("#aside").animate({
			"left" : "-1000px"
		}, 400);
		menu = false;
	}
}

function close_main(){
    startPoll = 1;
    startMypoll = 1;

	$("#sv_wrap").css("display", "none");
	$("#poll").css("display", "none");
	$("#mypoll").css("display", "none");
	$("#nav_main li a").css("color","#B5B5B5");
	$("#nav_main li .nav_select_bar").css("visibility", "hidden");
}


// survey 열기
function open_survey(){
    $('#mainPage > #sv_wrap > .sv_ct').html("");

    nowPage = "survey";
    loading = false; // 무한 로딩 방지 용
    scrollPage = 1; // 스크롤 페이지

    surveyList(scrollPage);

	close_main();
	$('#sv_wrap').fadeIn(500);
	$("#sv_wrap").css("display", "block");
	$("#nav_main li:nth-child(1) a").css("color","#FF8A57");
	$("#nav_main li:nth-child(1) .nav_select_bar").css("visibility", "visible");
}


// poll 열기
function open_poll(){
    $('#poll .wrap_poll_list').html("");
	
    nowPage = "poll";
	loading = false; // 무한 로딩 방지 용
    scrollPage = 1; // 스크롤 페이지

    pollList(scrollPage);

    close_main();
    $('#poll').fadeIn(500);
    $("#poll").css("display", "block");
    $("#nav_main li:nth-child(2) a").css("color","#FF8A57");
    $("#nav_main li:nth-child(2) .nav_select_bar").css("visibility", "visible");
}

var startPoll = 1;
function pollList(page){

    if(!loading){
        loading = true;
        if (!lastPage) { $("#ajax_loading").tmpl().appendTo("#poll"); }
        $.ajax({
            url:APIURL+"/api/selectPollList.do",
            type:"get",
            data:{
                    "page":page, 
                    "searchKey":$('input[name=searchKey]').val()
                },
            dataType:"json",
            success:function(res){

                $(".loading").remove();
                if (res.pollList.length == 0) { lastPage = true; }
                $("#ajax_poll").tmpl(res.pollList).appendTo("#poll .wrap_poll_list");
                
                var num = 0;
                for (var i=startPoll; i<(startPoll+res.pollList.length); i++){
                    var temp1 = '#poll form:nth-of-type('+i+') .poll_wrap:nth-child(2) ';
                    var temp2 = '#poll form:nth-of-type('+i+') .poll_wrap:nth-child(3) ';   


                    //// 질문 poll_wrap

                    // 참여 가능 VS 참여 완료
                    if(loginSession != null && res.pollList[num].joinMember.includes(loginSession.m_idx)){
                        // 참여 완료
                        $(temp1+'.can_vote').css("border", "0.5px solid #717171");
                        $(temp1+'.can_vote span').css("color", "#6A6A6A");
                        $(temp1+'.can_vote span').text("참여 완료");
                        $(temp2+'.can_vote').css("border", "0.5px solid #717171");
                        $(temp2+'.can_vote span').css("color", "#6A6A6A");
                        $(temp2+'.can_vote span').text("참여 완료");

                        $(temp1+'.question div').css("background-color", "#717171");
                        $(temp1+'.question div span').text("완료");
                        $(temp2+'.vote a').css("display", "none");
                    }


                    //// 선택지 poll_wrap

                    // 종료일 여부 체크
                    var txt_enddate = $(temp2+'.end_date').text().substring(6, 17);
                    var enddate = new Date(txt_enddate);
                    if (getEndDate(enddate) < getTodayDate()){
                        $(temp2+'.can_vote').css("border", "0.5px solid #6A6A6A");
                        $(temp2+'.can_vote span').css("color", "#6A6A6A");
                        $(temp1+'.can_vote').css("border", "0.5px solid #717171");
                        $(temp1+'.can_vote span').css("color", "#6A6A6A");
                        $(temp1+'.can_vote span').text("설문 종료");
                        $(temp2+'.can_vote span').text("설문 종료");

                        $(temp1+'.question div').css("background-color", "#717171");
                        $(temp1+'.question div span').text("종료");
                        $(temp2+'.vote a').css("display", "none");
                    }

                    // 선택지 개수에 따라 보여주기
                    var length = res.pollList[num].answerList.length;
                    for (var j=1; j<=5; j++){
                        if (j <= length){
                            $(temp2+'.options div:nth-of-type('+j+') input').val(j);
                            $(temp2+'.options div:nth-of-type('+j+') span:nth-of-type(1)').text(res.pollList[num].answerList[j-1].text);
                        } else {
                            $(temp2+'.options div:nth-of-type('+j+')').css("display","none");
                        }
                    }
                    num += 1;
                }
                startPoll = startPoll+res.pollList.length;

                loading = false;
                scrollPage += 1;
            }
        })
    }
}


// Poll 투표하기
function poll_vote(f){
    if (loginSession == null) {
        alert("로그인 후 이용해 주세요");
        return false;
    }

    var poll_num = f.p_idx.value;
    var poll_pick = $('form[name=pollForm_'+f.p_idx.value+'] input[name=answer]:checked').val();
    if (poll_pick) {
        // 선택지 고르고 투표
        $.ajax({
            url:APIURL+"/api/votePoll.action",
            type:"post",
            data:{  
                    "p_idx": poll_num,
                    "m_idx": loginSession.m_idx,
                    "pa_idx": poll_pick
                },
            success:function(res){
                alert("투표 완료");
                open_poll();
            } 
        })
    } else {
        // 선택지 안 고르고 투표
        alert("보기 중 하나를 골라주세요");
    }
}

// Mypoll 열기
function open_mypoll(){
    $("#mypoll").html("");

    nowPage = "mypoll";
    loading = false; // 무한 로딩 방지 용
    scrollPage = 1; // 스크롤 페이지
	

	if (!loginSession) {
		// 로그인 X
		$("#mypoll_beforeLogin").tmpl().appendTo("#mypoll");
	} else {
		// 로그인 O
		$("#mypoll_afterLogin").tmpl().appendTo("#mypoll");
        mypollList(scrollPage);
	}

	close_main();
	$('#mypoll').fadeIn(500);
	$("#mypoll").css("display", "block");
	$("#nav_main li:nth-child(3) a").css("color","#FF8A57");
	$("#nav_main li:nth-child(3) .nav_select_bar").css("visibility", "visible");
}

var startMypoll = 1;
function mypollList(page){
    if(!loading){
        loading = true;
        if (!lastPage) { $("#ajax_loading").tmpl().appendTo("#mypoll"); }
        $.ajax({
            url:APIURL+"/api/selectMyPollList.do",
            type:"get",
            data:{ 
                    "m_idx": loginSession.m_idx,
                    "page": page,
                    "searchKey": $('input[name=searchKey]').val()
                },
            dataType:"json",
            success:function(res){
                $(".loading").remove();
                if (res.mypollList.length == 0) { lastPage = true; }
                $("#ajax_mypoll_list").tmpl(res.mypollList).appendTo("#mypoll .wrap_mypoll_list");

                var num = 0;
                for (var i=startMypoll; i<(startMypoll+res.mypollList.length); i++){
                    var temp1 = '#mypoll form:nth-of-type('+i+') .poll_wrap:nth-child(1) ';
                    var temp2 = '#mypoll form:nth-of-type('+i+') .poll_wrap:nth-child(2) ';   


                    //// 질문 poll_wrap

                    // 전체 공개 VS 일부 공개
                    if ($(temp1+'.can_vote span').text() == '일부 공개'){
                        $(temp1+'.can_vote').css("border", "0.5px solid #6A6A6A");
                        $(temp1+'.can_vote span').css("color", "#6A6A6A");
                        $(temp2+'.can_vote').css("border", "0.5px solid #6A6A6A");
                        $(temp2+'.can_vote span').css("color", "#6A6A6A");
                    } 

                    // 수정하기 버튼 display 여부
                    if ($(temp1+'.info:nth-child(3) span').text() != "0"){
                        $(temp2+'.vote').css("display", "none");
                    } 


                    //// 선택지 poll_wrap

                     // 종료일 여부 체크
                    var txt_enddate = $(temp2+'.end_date').text().substring(6, 17);
                    var enddate = new Date(txt_enddate);
                    if (getEndDate(enddate) < getTodayDate()){
                        $(temp1+'.can_vote span').css("color", "#6A6A6A");
                        $(temp1+'.can_vote span').text("설문 종료");
                        $(temp1+'.can_vote').css("border", "0.5px solid #6A6A6A");
                        $(temp2+'.can_vote span').css("color", "#6A6A6A");
                        $(temp2+'.can_vote span').text("설문 종료");
                        $(temp2+'.can_vote').css("border", "0.5px solid #6A6A6A");
                    } else {
                        $(temp2+'.can_vote span').text("기간 남음");
                    }


                    // 선택지 개수에 따라 보여주기
                    var length = res.mypollList[num].answerList.length;
                    for (var j=1; j<=5; j++){
                        if (j <= length){
                            $(temp2+'.options div:nth-of-type('+j+') input').val(j);
                            $(temp2+'.options div:nth-of-type('+j+') span:nth-of-type(1)').text(res.mypollList[num].answerList[j-1].text);
                            $(temp2+'.options div:nth-of-type('+j+') span:nth-of-type(2)').text(res.mypollList[num].answerList[j-1].sum+"명");
                        } else {
                            $(temp2+'.options div:nth-of-type('+j+')').css("display","none");
                        }
                    }
                    num += 1;
                }
                startMypoll = startMypoll+res.mypollList.length;

                loading = false;
                scrollPage += 1;
            }
        })  
    }
}



// upload Poll
function open_uploadPoll(){
    $('#uploadPoll').html("");
    $("#ajax_uploadPoll").tmpl().appendTo("#uploadPoll");
    $("#poll_enddate").datepicker();

    $("#uploadPoll .btn_upload_wrap .btn_upload:nth-child(2)").css("display", "none");

    $.mobile.changePage("#uploadPoll");
}

// update Poll (페이지 열기)
function open_updatePoll(p_idx){
    $.ajax({
        url:APIURL+"/api/updatePoll.do",
        type:"get",
        data:{ "p_idx": p_idx },
        dataType:"json",
        success:function(res){
            $('#uploadPoll').html("");
            $("#ajax_uploadPoll").tmpl(res.myPoll).appendTo("#uploadPoll");
            for (var i=1; i<=res.myPoll.answers.length; i++){
                $('#uploadPoll .select_wrap input:nth-child('+i+')').val(res.myPoll.answers[i-1].text);
            }
            if (res.myPoll.poll.option1=="전체"){
                $('#uploadPoll input:radio[value=\'전체\']').attr("checked", true); 
            } else {
                $('#uploadPoll input:radio[value=\'일부\']').attr("checked", true); 
            }

            $("#poll_enddate").datepicker();
            $.mobile.changePage("#uploadPoll");
        } 
    })
}


// 로그아웃
function logout(){
	loginSession = undefined;
	open_menu();
}

// 퀵폴 열기
function poll_open(){
	$(event.target).parents('.poll_wrap').next('.poll_wrap').css("display", "block");
}

// 퀵폴 접기
function poll_close(){
	$(event.target).parents('.poll_wrap').css("display", "none");
}

// 오늘 날짜 구하기
function getTodayDate(){
    var date = new Date();
    return date.getFullYear()+"-"+("0"+(date.getMonth()+1)).slice(-2)+"-"+("0"+(date.getDate())).slice(-2)
}

// 종료일 구하기
function getEndDate(ymd){
    var date = new Date(ymd);
    return date.getFullYear()+"-"+("0"+(date.getMonth()+1)).slice(-2)+"-"+("0"+(date.getDate())).slice(-2)
}


// 첨부파일 사이즈 체크
function imageSizeCheck(v, filetag, fileName){
    var maxSize = 5 * 1024 * 1024;
    var fileSize = $("#"+filetag)[0].files[0].size;
    
    if(fileSize > maxSize){
        alert("첨부파일 사이즈는 5MB 이내로 등록 가능합니다.");
        $("#"+fileName).val('');
        return false;
    } else {
        pollUpload(v);
    }
}

// my poll 사진 업로드
function pollUpload(v){
    $('input[name=image]').val(v);
}

// 로그인 페이지 열기
function loginPage(f){
    f.reset();
    $.mobile.changePage("#loginPage");
}

// 아이디찾기 페이지 열기
function open_findIDPage(f){
    f.reset();
    $.mobile.changePage("#findIDPage");
}

// 회원가입 페이지 열기
function open_memberJoinPage(f){
    f.reset();
    $.mobile.changePage("#memberJoinPage");
}

// 1:1문의 작성 페이지 열기
function open_uploadInquire(f){
    f.reset();
    $.mobile.changePage("#uploadInquire");
}

// 메뉴 사용하다가 메인화면으로
function go_main(){
    $.mobile.changePage("#mainPage");
}
