package com.ddv.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.ddv.service.AdminService;


@Controller
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	ServletContext context;
	
	@Resource(name="adminService")
	private AdminService service;
	
	@RequestMapping(value = "/admin/login.session", method = RequestMethod.GET)
	public String noSession (Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String temp = request.getParameter("s");
		String next = "";
		
		if (temp == null || temp.equals("n")) next = "member/noSession";
		else if (temp.equals("y")) next = "member/hasSession";
		
		return next;
	}
	
	@RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	public String adminLogin (Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return "/login";
	}
	
	@RequestMapping(value = "/admin/login.action", method = RequestMethod.POST)
	public String adminLoginAction (Model model, HttpServletRequest request) {
		String next = "";
		String userid = request.getParameter("userid");
		String passwd = request.getParameter("passwd");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("passwd", passwd);
		try {
			HashMap<String, Object> member = service.goLogin(map);
			if (member != null) {
				HttpSession session = request.getSession(false);
				session.setAttribute("login", member);
				next = "redirect:/admin/userList?page=1";
			} else {
				next = "/fail/loginFail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	@RequestMapping(value = "/admin/logout", method = RequestMethod.GET)
	public String adminLogout (Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "/member/logout";
	}
	
	@RequestMapping(value = "/admin/userList", method = RequestMethod.GET)
	public String adminUserList (Model model, HttpServletRequest request) {
		String next = "";
		String page = request.getParameter("no");
		
		// 검색용
		HashMap<String, Object> txtSearch = new HashMap<String, Object>();
		String search = request.getParameter("txtSearch");
		if (search == null) { txtSearch.put("txtSearch", ""); }
		else { txtSearch.put("txtSearch", search); }
		
		try {
			int totalCount = service.countAllUser(txtSearch);
			PageInfo pageInfo = new PageInfo(totalCount, page);
			List<HashMap<String, Object>> userList = service.selectAllUser(pageInfo.getBoardInfo(), search);
			model.addAttribute("txtSearch", search);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("userList", userList);
			model.addAttribute("page", pageInfo.getPageInfo());
			next = "/userList";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	@RequestMapping(value = "/admin/adminList", method = RequestMethod.GET)
	public String adminAdminList (Model model, HttpServletRequest request) {
		String next = "";
		String page = request.getParameter("no");
		
		// 검색용
		HashMap<String, Object> txtSearch = new HashMap<String, Object>();
		String search = request.getParameter("txtSearch");
		if (search == null) { txtSearch.put("txtSearch", ""); }
		else { txtSearch.put("txtSearch", search); }
		
		try {
			int totalCount = service.countAllAdmin(txtSearch);
			PageInfo pageInfo = new PageInfo(totalCount, page);
			List<HashMap<String, Object>> adminList = service.selectAllAdmin(pageInfo.getBoardInfo(), search);
			model.addAttribute("txtSearch", search);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("adminList", adminList);
			model.addAttribute("page", pageInfo.getPageInfo());
			next = "/adminList";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	@RequestMapping(value = "/admin/editUserInfo", method = RequestMethod.GET)
	public String adminEditUserInfo (Model model, HttpServletRequest request) {
		String next = "";
		String m_idx = request.getParameter("m_idx");
		
		if (m_idx != null) {
			try {
				HashMap<String, Object> userInfo = service.selectUserInfo(Integer.parseInt(m_idx));
				model.addAttribute("userInfo", userInfo);
				next = "/editUserInfo";
			} catch (Exception e) {
				e.printStackTrace();
				next = "/error";
			}
		} else {
			next = "/editUserInfo";
		}
		return next;
	}
	

	@RequestMapping(value = "/admin/editUserInfo.action", method = RequestMethod.POST)
	public String adminEditUserInfoAction (Model model, HttpServletRequest request) {
		String next = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		String mode = request.getParameter("mode");
		if (!mode.equals("insert")) { map.put("m_idx", request.getParameter("m_idx")); }
		map.put("username", request.getParameter("username"));
		map.put("gender"  , request.getParameter("gender"));
		map.put("userid"  , request.getParameter("userid"));
		map.put("passwd"  , request.getParameter("passwd"));
		map.put("phone1"  , request.getParameter("phone1"));
		map.put("phone2"  , request.getParameter("phone2"));
		map.put("phone3"  , request.getParameter("phone3"));
		map.put("email1"  , request.getParameter("email1"));
		map.put("email2"  , request.getParameter("email2"));
		
		try {
			if (mode.equals("update")) {
				int result = service.updateUserInfo(map);
			} else if (mode.equals("insert")) {
				int result = service.insertUserInfo(map);
			} else if (mode.equals("delete")) {
				int result = service.deleteUserInfo(map);
			}
			next = "redirect:/admin/userList";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	
	@RequestMapping(value = "/admin/surveyList", method = RequestMethod.GET)
	public String adminSurveyList (Model model, HttpServletRequest request) {
		String next = "";
		String page = request.getParameter("no");
		
		// 검색용
		HashMap<String, Object> txtSearch = new HashMap<String, Object>();
		String search = request.getParameter("txtSearch");
		if (search == null) { txtSearch.put("txtSearch", ""); }
		else { txtSearch.put("txtSearch", search); }
		
		try {
			int totalCount = service.countAllSurvey(txtSearch);
			PageInfo pageInfo = new PageInfo(totalCount, page);
			List<HashMap<String, Object>> surveyList = service.selectAllSurvey(pageInfo.getBoardInfo(), search);
			model.addAttribute("txtSearch", search);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("surveyList", surveyList);
			model.addAttribute("page", pageInfo.getPageInfo());
			next = "/surveyList";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	
	// 서베이 관련
	@RequestMapping(value = "/admin/editSurvey", method = RequestMethod.GET)
	public String adminEditSurvey (Model model, HttpServletRequest request) {
		String next = "";
		String s_idx = request.getParameter("s_idx");
		String mode = "";
		if (s_idx != null) {
			mode = "update";
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("s_idx", s_idx);
			try {
				List<HashMap<String, Object>> surveyInfo = service.selectSurveyInfo(map);
				for(HashMap<String, Object> survey: surveyInfo) {
					map.put("q_idx", survey.get("q_idx"));
					survey.put("answerList", service.selectSurveyInfoAnswer(survey));
				}
				model.addAttribute("surveyInfo", surveyInfo);
				next = "/editSurvey";
			} catch (Exception e) {
				e.printStackTrace();
				next = "/error";
			}
		} else {
			mode = "insert";
			next = "/editSurvey";
		}
		model.addAttribute("mode", mode);
		return next;
	}
	
	@RequestMapping(value = "/admin/editSurvey.action", method = RequestMethod.POST)
	public String adminEditSurveyAction (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> param) {
		String next = "";
		String mode = request.getParameter("mode");
		FileUploadAction fua = new FileUploadAction(); // for fileupload
		String docPath = null; // for fileupload
		MultipartHttpServletRequest fileRequest = (MultipartHttpServletRequest) request; // for fileupload
		
		// 서베이 삭제
		if (mode.equals("delete")) {
			try {
				service.deleteSurveyInfo(Integer.parseInt(request.getParameter("s_idx")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			next = "redirect:/admin/surveyList";
			return next;
		}
		
		// SurveyInfo Param 저장  
		HashMap<String, Object> surveyInfo = new HashMap<String, Object>();
		if (!mode.equals("insert")) { surveyInfo.put("s_idx", request.getParameter("s_idx")); }
		surveyInfo.put("title", request.getParameter("title"));
		surveyInfo.put("subtitle", request.getParameter("subtitle"));
		surveyInfo.put("point", request.getParameter("point"));
		surveyInfo.put("time", request.getParameter("time"));
		surveyInfo.put("enddate", request.getParameter("enddate"));
		surveyInfo.put("m_idx", request.getParameter("m_idx"));
		
		// 서베이 이미지 업로드
		surveyInfo.put("mainimg", fua.fileUpload(context, mode, fileRequest.getFile("mainimg"), request.getParameter("o_mainimg")));
		
		// Questions Param 저장
		String questCount = request.getParameter("num"); // 질문 수
		List<HashMap<String, Object>> questList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> answerList = new ArrayList<HashMap<String, Object>>();
		int minus = 0;
		for (int i=1; i<=Integer.parseInt(questCount); i++) {
			HashMap<String, Object> quest = new HashMap<String, Object>();
			String question = request.getParameter("quest"+i);
			if (question == null) {
				minus += 1;
			} else {
				quest.put("q_idx", i-minus);
				quest.put("question", request.getParameter("quest"+i));
				quest.put("image1", fua.fileUpload(context, mode, fileRequest.getFile("image"+i), request.getParameter("o_image"+i)));
				questList.add(quest);
				
				HashMap<String, Object> answer = new HashMap<String, Object>();
				answer.put("q_idx", i-minus);
				answer.put("text1", request.getParameter("text1_"+i));
				answer.put("text2", request.getParameter("text2_"+i));
				answer.put("text3", request.getParameter("text3_"+i));
				answer.put("text4", request.getParameter("text4_"+i));
				
				answerList.add(answer);
			}
		}
		
		try {
			if (mode.equals("insert")) service.insertSurveyInfo(surveyInfo, questList, answerList);
			else if (mode.equals("update")) service.updateSurveyInfo(surveyInfo, questList, answerList);
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		
		next = "redirect:/admin/surveyList";
		return next;
	}
	
	
	
	// 퀵폴 관련
	@RequestMapping(value = "/admin/editPoll", method = RequestMethod.GET)
	public String adminEditPoll (Model model, HttpServletRequest request) {
		String next = "";
		String p_idx = request.getParameter("p_idx");
		String mode = "";
		if (p_idx != null) {
			mode = "update";
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("p_idx", p_idx);
			try {
				model.addAttribute("pollInfo", service.selectPollInfo(map));
				model.addAttribute("answerList", service.selectPollInfoAnswer(map));
				next = "/editPoll";
			} catch (Exception e) {
				e.printStackTrace();
				next = "/error";
			}
		} else {
			mode = "insert";
			next = "/editPoll";
		}
		model.addAttribute("mode", mode);
		return next;
	}
	
	
	@RequestMapping(value = "/admin/editPoll.action", method = RequestMethod.POST)
	public String adminEditPollAction (Model model, HttpServletRequest request) throws Exception {
		String next = "";
		String mode = request.getParameter("mode");
		FileUploadAction fua = new FileUploadAction(); // for fileupload
		String docPath = null; // for fileupload
		MultipartHttpServletRequest fileRequest = (MultipartHttpServletRequest) request; // for fileupload
		
		HashMap<String, Object> pollInfo = new HashMap<String, Object>();
		if (!mode.equals("insert")) { pollInfo.put("p_idx", Integer.parseInt(request.getParameter("p_idx"))); }
		
		// 삭제
		if (mode.equals("delete")) {
			try {
				service.deletePoll(pollInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			next = "redirect:/admin/pollList";
			return next;
		}
		
		// survey - getParameter
		pollInfo.put("question", request.getParameter("question"));
		pollInfo.put("option1", request.getParameter("option1"));
		pollInfo.put("enddate", request.getParameter("enddate"));
		pollInfo.put("m_idx", request.getParameter("m_idx"));
		
		
		// 퀵폴 이미지 업로드
		pollInfo.put("image", fua.fileUpload(context, mode, fileRequest.getFile("image"), request.getParameter("o_mainimg")));
		
		// answerList : 선택지 관련
		HashMap<String, Object> answer = new HashMap<String, Object>();
		answer.put("text1", request.getParameter("text1"));
		answer.put("text2", request.getParameter("text2"));
		answer.put("text3", request.getParameter("text3"));
		answer.put("text4", request.getParameter("text4"));
		answer.put("text5", request.getParameter("text5"));
		
		try {
			if (mode.equals("insert")) service.insertPoll(pollInfo, answer);
			else if (mode.equals("update")) service.updatePoll(pollInfo, answer);
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		
		next = "redirect:/admin/pollList";
		return next;
	}
	
	// Poll 삭제하기
	@ResponseBody
	@RequestMapping(value="/admin/deletePoll.action", method=RequestMethod.POST)
	public void deletePoll (Model model, HttpServletRequest request) {
		String p_idx = request.getParameter("p_idx");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("p_idx", Integer.parseInt(p_idx)); 
		
		try {
			int result = service.deletePoll(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// FAQ 관련
	@RequestMapping(value = "/admin/editFAQ", method = RequestMethod.GET)
	public String adminEditFAQ (Model model, HttpServletRequest request) {
		String next = "";
		String f_idx = request.getParameter("f_idx");
		String mode = "";
		if (f_idx != null) {
			mode = "update";
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("f_idx", f_idx);
			try {
				model.addAttribute("FAQInfo", service.selectFAQInfo(map));
				next = "/editFAQ";
			} catch (Exception e) {
				e.printStackTrace();
				next = "/error";
			}
		} else {
			mode = "insert";
			next = "/editFAQ";
		}
		model.addAttribute("mode", mode);
		return next;
	}
	
	@RequestMapping(value = "/admin/editFAQ.action", method = RequestMethod.POST)
	public String adminEditFAQAction (Model model, HttpServletRequest request) {
		String next = "";
		HashMap<String, Object> FAQInfo = new HashMap<String, Object>();
		String mode = request.getParameter("mode");
		if (!mode.equals("insert")) { FAQInfo.put("f_idx", request.getParameter("f_idx")); }
		
		// 삭제
		if (mode.equals("delete")) {
			try {
				service.deleteFAQ(FAQInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			next = "redirect:/admin/FAQList";
			return next;
		}
		
		if (!mode.equals("insert")) { FAQInfo.put("f_idx", request.getParameter("f_idx")); }
		FAQInfo.put("m_idx", request.getParameter("m_idx"));
		FAQInfo.put("title", request.getParameter("title"));
		FAQInfo.put("text", request.getParameter("text"));
		
		try {
			if (mode.equals("update")) {
				service.updateFAQ(FAQInfo);
			} else if (mode.equals("insert")) {
				service.insertFAQ(FAQInfo);
			} 
			next = "redirect:/admin/FAQList";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	
	
	@RequestMapping(value = "/admin/userList/userPointList", method = RequestMethod.GET)
	public String adminUserPointList (Model model, HttpServletRequest request) {
		String next = "";
		String page = request.getParameter("no");
		String m_idx = request.getParameter("m_idx");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("m_idx", m_idx);
		
		try {
			int totalPoint = service.countUserPointSum(map);
			int totalCount = service.countAllUserPoint(map);
			PageInfo pageInfo = new PageInfo(totalCount, page);
			map.put("pageInfo", pageInfo.getBoardInfo());
			List<HashMap<String, Object>> pointList = service.selectUserPointList(map);
			
			model.addAttribute("totalPoint", totalPoint);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("pointList", pointList);
			model.addAttribute("page", pageInfo.getPageInfo());
			
			next = "/userPointList";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	@RequestMapping(value = "/admin/userList/userPointList/answer", method = RequestMethod.GET)
	public String adminUserSurveyAnswer (Model model, HttpServletRequest request) {
		String next = "";
		String m_idx = request.getParameter("m_idx");
		String s_idx = request.getParameter("s_idx");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("m_idx", m_idx);
		map.put("s_idx", s_idx);
		
		try {
			List<HashMap<String, Object>> answerList = service.selectUserSurveyAnswer(map);
			model.addAttribute("answerList", answerList);

			next = "/userPointSurveyAnswer";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	
	
	@RequestMapping(value = "/admin/editAdminInfo", method = RequestMethod.GET)
	public String adminEditAdminInfo (Model model, HttpServletRequest request) {
		String next = "";
		String m_idx = request.getParameter("m_idx");
		
		if (m_idx != null) {
			try {
				HashMap<String, Object> adminInfo = service.selectAdminInfo(Integer.parseInt(m_idx));
				model.addAttribute("adminInfo", adminInfo);
				next = "/editAdminInfo";
			} catch (Exception e) {
				e.printStackTrace();
				next = "/error";
			}
		} else {
			next = "/editAdminInfo";
		}
		return next;
	}
	

	@RequestMapping(value = "/admin/editAdminInfo.action", method = RequestMethod.POST)
	public String adminEditAdminInfoAction (Model model, HttpServletRequest request) {
		String next = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		String mode = request.getParameter("mode");
		if (!mode.equals("insert")) { map.put("m_idx", request.getParameter("m_idx")); }
		map.put("username", request.getParameter("username"));
		map.put("gender"  , request.getParameter("gender"));
		map.put("userid"  , request.getParameter("userid"));
		map.put("passwd"  , request.getParameter("passwd"));
		map.put("email1"  , request.getParameter("email1"));
		map.put("email2"  , request.getParameter("email2"));
		
		try {
			if (mode.equals("update")) {
				int result = service.updateAdminInfo(map);
			} else if (mode.equals("insert")) {
				int result = service.insertAdminInfo(map);
			} else if (mode.equals("delete")) {
				int result = service.deleteAdminInfo(map);
			}
			next = "redirect:/admin/adminList";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	@RequestMapping(value = "/admin/inquireList", method = RequestMethod.GET)
	public String adminInquireList (Model model, HttpServletRequest request) {
		String next = "";
		String page = request.getParameter("no");
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// 검색용
		HashMap<String, Object> txtSearch = new HashMap<String, Object>();
		String search = request.getParameter("txtSearch");
		if (search == null) { txtSearch.put("txtSearch", ""); }
		else { txtSearch.put("txtSearch", search); }
		
		try {
			int totalCount = service.countAllInquire(txtSearch);
			PageInfo pageInfo = new PageInfo(totalCount, page);
			List<HashMap<String, Object>> inquireList = service.selectAllInquire(pageInfo.getBoardInfo(), search);
			model.addAttribute("txtSearch", search);
			model.addAttribute("inquireList", inquireList);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("page", pageInfo.getPageInfo());
			
			next = "/inquireList";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	
	@RequestMapping(value = "/admin/inquireAnswer", method = RequestMethod.GET)
	public String adminInquireAnswer (Model model, HttpServletRequest request) {
		String next = "";
		String i_idx = request.getParameter("i_idx");
		try {
			HashMap<String, Object> inquireAnswer = service.selectInquireAnswer(Integer.parseInt(i_idx));
			model.addAttribute("inquireAnswer", inquireAnswer);
			next = "/inquireAnswer";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	
	@RequestMapping(value = "/admin/inquireAnswer.action", method = RequestMethod.POST)
	public String adminInquireAnswerAction (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> param) {
		String next = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		String complete = request.getParameter("complete");
		map.put("i_idx", request.getParameter("i_idx"));
		map.put("question", request.getParameter("question"));
		map.put("answer", request.getParameter("answer"));
		map.put("admin_idx", request.getParameter("admin_idx"));
		
		try {
			int result = service.updateInquireAnswer(map);
			next = "redirect:/admin/inquireList";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	
	@RequestMapping(value = "/admin/pollList", method = RequestMethod.GET)
	public String adminPollList (Model model, HttpServletRequest request) {
		String next = "";
		String page = request.getParameter("no");
		
		// 검색용
		HashMap<String, Object> txtSearch = new HashMap<String, Object>();
		String search = request.getParameter("txtSearch");
		if (search == null) { txtSearch.put("txtSearch", ""); }
		else { txtSearch.put("txtSearch", search); }
		
		try {
			int totalCount = service.countAllPoll(txtSearch);
			PageInfo pageInfo = new PageInfo(totalCount, page);
			List<HashMap<String, Object>> pollList = service.selectAllPoll(pageInfo.getBoardInfo(), search);
			model.addAttribute("txtSearch", search);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("pollList", pollList);
			model.addAttribute("page", pageInfo.getPageInfo());
			next = "/pollList";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	
	@RequestMapping(value = "/admin/FAQList", method = RequestMethod.GET)
	public String adminFAQList (Model model, HttpServletRequest request) {
		String next = "";
		String page = request.getParameter("no");
		
		// 검색용
		HashMap<String, Object> txtSearch = new HashMap<String, Object>();
		String search = request.getParameter("txtSearch");
		if (search == null) { txtSearch.put("txtSearch", ""); }
		else { txtSearch.put("txtSearch", search); }
		

		try {
			int totalCount = service.countAllFAQ(txtSearch);
			PageInfo pageInfo = new PageInfo(totalCount, page);
			List<HashMap<String, Object>> FAQList = service.selectAllFAQ(pageInfo.getBoardInfo(), search);
			model.addAttribute("txtSearch", search);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("FAQList", FAQList);
			model.addAttribute("page", pageInfo.getPageInfo());
			next = "/FAQList";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	
	
	// 서베이 이용 분석
	@RequestMapping(value = "/admin/surveyAnalyze", method = RequestMethod.GET)
	public String adminSurveyAnalyze (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> dateMap) {
		String next = "";
		
		try {
			List<HashMap<String, Object>> surveyList = service.selectAnalyzeSurveyAll(dateMap);
			model.addAttribute("startdate", dateMap.get("startdate"));
			model.addAttribute("enddate", dateMap.get("enddate"));
			model.addAttribute("surveyList", surveyList);
			
			next = "/surveyAnalyze";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	// 서베이 이용 분석 - 자세히보기 (참여자)
	@RequestMapping(value = "/admin/surveyAnalyze/detailPeople", method = RequestMethod.GET)
	public String adminSurveyAnalyzeDetailPeople (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> param) {
		String next = "";
		try {
			List<HashMap<String, Object>> joinList = service.selectAnalyzeSurveyJoinPeople(param);
			model.addAttribute("joinList", joinList);
			next = "/surveyAnalyzeDetailPeople";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	// 서베이 이용 분석 - 자세히보기 (투표 결과)
	@RequestMapping(value = "/admin/surveyAnalyze/detailResult", method = RequestMethod.GET)
	public String adminSurveyAnalyzeDetailResult (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> param) {
		String next = "";
		try {
			List<HashMap<String, Object>> joinList = service.selectAnalyzeSurveyJoinResult(param);
			model.addAttribute("joinList", joinList);
			next = "/surveyAnalyzeDetailResult";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	
	// 퀵폴 이용 분석
	@RequestMapping(value = "/admin/pollAnalyze", method = RequestMethod.GET)
	public String adminPollAnalyze (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> dateMap) {
		String next = "";

		try {
			List<HashMap<String, Object>> pollList = service.selectAnalyzePollAll(dateMap);
			model.addAttribute("startdate", dateMap.get("startdate"));
			model.addAttribute("enddate", dateMap.get("enddate"));
			model.addAttribute("pollList", pollList);
			
			next = "/pollAnalyze";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	// 퀵폴 이용 분석 - 자세히보기 (참여자)
	@RequestMapping(value = "/admin/pollAnalyze/detailPeople", method = RequestMethod.GET)
	public String adminPollAnalyzeDetailPeople (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> param) {
		String next = "";
		try {
			List<HashMap<String, Object>> joinList = service.selectAnalyzePollJoinPeople(param);
			model.addAttribute("joinList", joinList);
			next = "/pollAnalyzeDetailPeople";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
	// 퀵폴 이용 분석 - 자세히보기 (투표 결과)
	@RequestMapping(value = "/admin/pollAnalyze/detailResult", method = RequestMethod.GET)
	public String adminPollAnalyzeDetailResult (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> param) {
		String next = "";
		try {
			List<HashMap<String, Object>> joinList = service.selectAnalyzePollJoinResult(param);
			model.addAttribute("joinList", joinList);
			next = "/pollAnalyzeDetailResult";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
		
	
	// 사용자 이용 분석
	@RequestMapping(value = "/admin/userAnalyze", method = RequestMethod.GET)
	public String adminUserAnalyze (Model model, HttpServletRequest request, @RequestParam HashMap<String, Object> param) {
		String next = "";
		String page = request.getParameter("no");
			
		try {
			int totalCount = service.countAllAnalyzePoint();
			PageInfo pageInfo = new PageInfo(totalCount, page);
			List<HashMap<String, Object>> pointList = service.selectAnalyzePointList(pageInfo.getBoardInfo());
			model.addAttribute("pointList", pointList);
			model.addAttribute("page", pageInfo.getPageInfo());
			next = "/userAnalyze";
		} catch (Exception e) {
			e.printStackTrace();
			next = "/error";
		}
		return next;
	}
	
}
