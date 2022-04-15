package com.ddv.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ddv.common.FileUploadAction;
import com.ddv.common.PageInfo;
import com.ddv.common.SetFileName;
import com.ddv.service.HomeService;


@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	ServletContext context;
	
	@Resource(name="service")
	private HomeService service;
	
	@ResponseBody /* 자바 객체를 XML/JSON으로 변환 + maven에 라이브러리 추가 */
	@RequestMapping(value = "/api/surveyList.do", method = RequestMethod.GET, produces = "application/json")
	public HashMap<String, Object> home (Model model, HttpServletRequest request) {
		String page = request.getParameter("page");
		System.out.println("page: "+page);
		String searchKey = request.getParameter("searchKey");
		
		int totalCount = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<HashMap<String, Object>> surveyList = null;
		
		try {
			totalCount = service.countAllSurvey(searchKey);
			map.put("searchKey", searchKey);
			PageInfo pageInfo = new PageInfo(totalCount, page);
			
			map.put("pageInfo", pageInfo.getBoardInfo());
			surveyList = service.selectAllSurvey(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("surveyList", surveyList);
		System.out.println("TEST: "+json);
		System.out.println("TEST2: "+json.toString());
		// json 형태로 전달
		return json;
	}

	// 이미 참여한 서베이인지 체크
	@ResponseBody
	@RequestMapping(value = "/api/surveyAlreadyJoin.check", method = RequestMethod.GET)
	public HashMap<String, Object> surveyAlreadyJoin(Model model, HttpServletRequest request) {
		String s_idx = request.getParameter("s_idx");
		String m_idx = request.getParameter("m_idx");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("s_idx", Integer.parseInt(s_idx));
		map.put("m_idx", Integer.parseInt(m_idx));
		
		
		HashMap<String, Object> join = new HashMap<String, Object>();
		try {
			join = service.surveyAlreadyJoin(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("join", join);
		
		// json 형태로 전달
		return json;
	}
	
	
	// 서베이 참여(시작)
	@ResponseBody
	@RequestMapping(value = "/api/surveyJoin.do", method = RequestMethod.GET)
	public HashMap<String, Object> surveyJoin(Model model, HttpServletRequest request) {
		String s_idx = request.getParameter("s_idx");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("s_idx", Integer.parseInt(s_idx));
		HashMap<String, Object> survey = service.selectOneSurvey(map);
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("quest", survey);
		
		// json 형태로 전달
		return json;
	}
	
	// 서베이 문항들 가져오기
	@ResponseBody
	@RequestMapping(value = "/api/surveyJoinIng.do", method = RequestMethod.GET)
	public HashMap<String, Object> doSurveyJoin(Model model, HttpServletRequest request) {
		String s_idx = request.getParameter("s_idx");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("s_idx", Integer.parseInt(s_idx));
		List<HashMap<String, Object>> questionList = service.selectQuestionList(map);
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("quest", questionList);
		
		// json 형태로 전달
		return json;
	}
	
	// 서베이 사용자 참여 결과 저장하기 + 포인트 넣어주기
	@ResponseBody
	@RequestMapping(value = "/api/surveySubmit.action", method = RequestMethod.POST)
	public void surveySubmit (Model model, HttpServletRequest request, @RequestParam(value="pickAnswer") List<Object> pickAnswer) {
		String s_idx = request.getParameter("s_idx");
		String m_idx = request.getParameter("m_idx");
		String point = request.getParameter("point");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("s_idx", Integer.parseInt(s_idx));
		map.put("m_idx", Integer.parseInt(m_idx));
		map.put("point", Integer.parseInt(point));
		map.put("answerList", pickAnswer);
		
		try {
			service.insertUserSurveyAnswer(map);
			service.insertUserSurveyJoinPoint(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 아이디 중복 체크
	@ResponseBody
	@RequestMapping(value="/api/dupeId.check", method=RequestMethod.GET)
	public HashMap<String, Object> dupeIdCheck (Model model, HttpServletRequest request) {
		String userid = request.getParameter("userid");
		String result = service.dupeIdCheck(userid);
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("result", result);
		
		// json 형태로 전달
		return json;
	}
	
	// 회원가입
	@ResponseBody
	@RequestMapping(value="/api/memberInsert.action", method=RequestMethod.POST)
	public void insertMember (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> param) {
		param.put("passwd", param.get("passwd1"));
		int result = service.insertMember(param);
	}
	
	// 로그인
	@ResponseBody
	@RequestMapping(value="/api/login.action", method=RequestMethod.POST)
	public HashMap<String, Object> login (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> param) {
		HashMap<String, Object> member = service.login(param);
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("login", member);
		
		return json;
	}
	
	// 아이디 찾기
	@ResponseBody
	@RequestMapping(value="/api/findID.action", method=RequestMethod.POST)
	public HashMap<String, Object> findID (Model model, HttpServletRequest request, 
											@RequestParam HashMap<String, Object> param) {

		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("userid", service.findID(param));
		
		return json;
	}
	
	
	
	// 내 정보 페이지
	@ResponseBody
	@RequestMapping(value = "/api/myInfoPage.do", method = RequestMethod.GET)
	public HashMap<String, Object> myInfoPage (Model model, HttpServletRequest request) {
		String m_idx = request.getParameter("m_idx");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("m_idx", Integer.parseInt(m_idx));
		HashMap<String, Object> member = service.selectMyInfo(map);
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("myInfo", member);
		
		// json 형태로 전달
		return json;
	}
	
	// 
	// 포인트
	@ResponseBody
	@RequestMapping(value = "/api/pointPage.do", method = RequestMethod.GET)
	public HashMap<String, Object> pointPage (Model model, HttpServletRequest request) {
		String m_idx = request.getParameter("m_idx");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("m_idx", Integer.parseInt(m_idx));
		
		List<HashMap<String, Object>> pointList = null;
		int sumPoints = 0;
		try {
			pointList = service.selectMyPoints(map);
			sumPoints = service.sumMyPoints(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("myPoint", pointList);
		json.put("sumPoints", sumPoints);
		
		// json 형태로 전달
		return json;
	}
	
	
	
	// 회원 정보 수정 (내 정보)
	@ResponseBody
	@RequestMapping(value="/api/memberUpdate.action", method=RequestMethod.POST)
	public void updateMember (Model model, HttpServletRequest request) {
		String m_idx = request.getParameter("m_idx");
		String username = request.getParameter("username");
		String gender = request.getParameter("gender");
		String passwd = request.getParameter("passwd");
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String phone3 = request.getParameter("phone3");
		String email1 = request.getParameter("email1");
		String email2 = request.getParameter("email2");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("m_idx", m_idx);
		map.put("username", username);
		map.put("gender", gender);
		map.put("passwd", passwd);
		map.put("phone1", phone1);
		map.put("phone2", phone2);
		map.put("phone3", phone3);
		map.put("email1", email1);
		map.put("email2", email2);
		
		int result = service.updateMember(map);
	}
	
	
	// FAQ 페이지 (리스트)
	@ResponseBody
	@RequestMapping(value = "/api/FAQPage.do", method = RequestMethod.GET)
	public HashMap<String, Object> FAQPage (Model model, HttpServletRequest request) {
		
		List<HashMap<String, Object>> FAQList = null;
		try {
			FAQList = service.selectFAQList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("FAQList", FAQList);
		
		// json 형태로 전달
		return json;
	}
	
	// FAQ (1개 자세히 보기)
	@ResponseBody
	@RequestMapping(value = "/api/selectOneFAQ.do", method = RequestMethod.GET)
	public HashMap<String, Object> selectOneFAQ (Model model, HttpServletRequest request) {
		String f_idx = request.getParameter("f_idx");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("f_idx", Integer.parseInt(f_idx));
		
		HashMap<String, Object> FAQInfo = null;
		try {
			FAQInfo = service.selectOneFAQInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("FAQInfo", FAQInfo);
		
		// json 형태로 전달
		return json;
	}
	
	
	
	
	// 1:1 문의 목록(리스트)
	@ResponseBody
	@RequestMapping(value = "/api/inquirePage.do", method = RequestMethod.GET)
	public HashMap<String, Object> inquirePage (Model model, HttpServletRequest request) {
		String m_idx = request.getParameter("m_idx");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("m_idx", Integer.parseInt(m_idx));
		
		List<HashMap<String, Object>> inquireList = null;
		try {
			inquireList = service.selectMyInquireList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("inquireList", inquireList);
		
		// json 형태로 전달
		return json;
	}
	
	// 1:1 문의 (1개 자세히 보기)
	@ResponseBody
	@RequestMapping(value = "/api/selectOneInquire.do", method = RequestMethod.GET)
	public HashMap<String, Object> selectOneInquire (Model model, HttpServletRequest request) {
		String i_idx = request.getParameter("i_idx");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("i_idx", Integer.parseInt(i_idx));
		
		HashMap<String, Object> inquireInfo = null;
		try {
			inquireInfo = service.selectOneInquireInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("inquireInfo", inquireInfo);
		
		// json 형태로 전달
		return json;
	}
	
	
	// 회원 정보 수정 (내 정보)
	@ResponseBody
	@RequestMapping(value="/api/uploadInquire.action", method=RequestMethod.POST)
	public void uploadInquire (Model model, HttpServletRequest request) {
		String m_idx = request.getParameter("m_idx");
		String question = request.getParameter("question");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("m_idx", m_idx);
		map.put("question", question);
		
		int result = service.insertInquire(map);
	}
	
	
	// Poll 등록하기
	@ResponseBody
	@RequestMapping(value="/api/uploadPoll.action", method=RequestMethod.POST)
	public void uploadPoll (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> param) {
		FileUploadAction fua = new FileUploadAction(); // for fileupload
		String docPath = null; // for fileupload
		MultipartHttpServletRequest fileRequest = (MultipartHttpServletRequest) request; // for fileupload
		
		if (((String) param.get("p_idx")).trim().isEmpty()) { // insert
			param.put("image", fua.fileUpload(context, "insert", fileRequest.getFile("file"), request.getParameter("o_mainimg")));
			service.insertPoll(param);
		} else { // update
			if (fileRequest.getFile("file") == null) {
				param.put("image", request.getParameter("o_mainimg"));
			} else {
				param.put("image", fua.fileUpload(context, "update", fileRequest.getFile("file"), request.getParameter("o_mainimg")));
			}
			
			service.updatePoll(param);
		}
	}
	
	// Poll 삭제하기
	@ResponseBody
	@RequestMapping(value="/api/deletePoll.action", method=RequestMethod.POST)
	public void deletePoll (Model model, HttpServletRequest request) {
		String p_idx = request.getParameter("p_idx");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("p_idx", Integer.parseInt(p_idx)); 
		
		int result = service.deletePoll(map);
	}
	

	
	
	
	// MyPoll 수정하기
	@ResponseBody
	@RequestMapping(value = "/api/updatePoll.do", method = RequestMethod.GET)
	public HashMap<String, Object> updatePoll (Model model, HttpServletRequest request) {
		String p_idx = request.getParameter("p_idx");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("p_idx", Integer.parseInt(p_idx));
		
		HashMap<String, Object> myPoll = null;
		try {
			myPoll = service.selectMyPoll(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("myPoll", myPoll);
		
		// json 형태로 전달
		return json;
	}
	
	
	// MyPoll 리스트 가져오기
	@ResponseBody
	@RequestMapping(value = "/api/selectMyPollList.do", method = RequestMethod.GET)
	public HashMap<String, Object> selectMyPollList (Model model, HttpServletRequest request) {
		String m_idx = request.getParameter("m_idx");
		String page = request.getParameter("page");
		String searchKey = request.getParameter("searchKey");
		
		// 객체 생성
		int totalCount = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<HashMap<String, Object>> myPollList = null;
		
		try {
			map.put("m_idx", Integer.parseInt(m_idx));
			map.put("searchKey", searchKey);
			totalCount = service.countAllOpenMyPoll(map);
			
			PageInfo pageInfo = new PageInfo(totalCount, page);
			map.put("pageInfo", pageInfo.getBoardInfo());
			myPollList = service.selectMyPollList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("mypollList", myPollList);
		
		// json 형태로 전달
		return json;
	}
	
	
	// Poll 리스트 가져오기
	@ResponseBody
	@RequestMapping(value = "/api/selectPollList.do", method = RequestMethod.GET)
	public HashMap<String, Object> selectPollList (Model model, HttpServletRequest request) {
		String page = request.getParameter("page");
		String searchKey = request.getParameter("searchKey");
		
		
		// 객체 생성
		int totalCount = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("searchKey", searchKey);
		List<HashMap<String, Object>> pollList = null;
		
		try {
			totalCount = service.countAllOpenPoll(map);
			PageInfo pageInfo = new PageInfo(totalCount, page);
			map.put("pageInfo", pageInfo.getBoardInfo());
			pollList = service.selectPollList(map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// json으로 전달
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("pollList", pollList);

		// json 형태로 전달
		return json;
	}
	
	
	// Poll 투표하기
	@ResponseBody
	@RequestMapping(value = "/api/votePoll.action", method = RequestMethod.POST)
	public void votePoll (Model model, HttpServletRequest request, 
							@RequestParam HashMap<String, Object> param) {
		
		try {
			int num = service.votePoll(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
