package com.ddv.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ddv.dao.AdminDAO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Resource(name="adminDao")
	private AdminDAO dao;
	
	@Override
	public HashMap<String, Object> goLogin(HashMap<String, Object> map) throws Exception {
		return dao.goLogin(map);
	}

	@Override
	public int countAllUser(HashMap<String, Object> txtSearch) throws Exception {
		return dao.countAllUser(txtSearch);
	}

	@Override
	public List<HashMap<String, Object>> selectAllUser(HashMap<String, Object> map, String txtSearch) throws Exception {
		map.put("txtSearch", txtSearch);
		return dao.selectAllUser(map);
	}
	
	@Override
	public int countAllAdmin(HashMap<String, Object> txtSearch) throws Exception {
		return dao.countAllAdmin(txtSearch);
	}

	@Override
	public List<HashMap<String, Object>> selectAllAdmin(HashMap<String, Object> map, String txtSearch) throws Exception {
		map.put("txtSearch", txtSearch);
		return dao.selectAllAdmin(map);
	}

	@Override
	public HashMap<String, Object> selectUserInfo(int m_idx) throws Exception {
		return dao.selectUserInfo(m_idx);
	}

	@Override
	public int countAllSurvey(HashMap<String, Object> txtSearch) throws Exception {
		return dao.countAllSurvey(txtSearch);
	}

	@Override
	public List<HashMap<String, Object>> selectAllSurvey(HashMap<String, Object> map, String txtSearch) throws Exception {
		map.put("txtSearch", txtSearch);
		return dao.selectAllSurvey(map);
	}

	@Override
	public List<HashMap<String, Object>> selectSurveyInfo(HashMap<String, Object> map) throws Exception {
		return dao.selectSurveyInfo(map);
	}
	
	@Override
	public List<HashMap<String, Object>> selectSurveyInfoAnswer(HashMap<String, Object> map) throws Exception {
		return dao.selectSurveyInfoAnswer(map);
	}
	
	@Override
	public HashMap<String, Object> selectPollInfo(HashMap<String, Object> map) throws Exception {
		return dao.selectPollInfo(map);
	}
	
	@Override
	public List<HashMap<String, Object>> selectPollInfoAnswer(HashMap<String, Object> map) throws Exception {
		return dao.selectPollInfoAnswer(map);
	}
	
	@Override
	public HashMap<String, Object> selectFAQInfo(HashMap<String, Object> map) throws Exception {
		return dao.selectFAQInfo(map);
	}
	
	@Override
	public void insertFAQ(HashMap<String, Object> map) throws Exception {
		dao.insertFAQ(map);
	}
	
	@Override
	public void updateFAQ(HashMap<String, Object> map) throws Exception {
		dao.updateFAQ(map);
	}
	
	@Override
	public void deleteFAQ(HashMap<String, Object> map) throws Exception {
		dao.deleteFAQ(map);
	}
	
	@Override
	public int countUserPointSum(HashMap<String, Object> map) throws Exception {
		return dao.countUserPointSum(map);
	}
	
	@Override
	public int countAllUserPoint(HashMap<String, Object> map) throws Exception {
		return dao.countAllUserPoint(map);
	}

	@Override
	public List<HashMap<String, Object>> selectUserPointList(HashMap<String, Object> map) throws Exception {
		return dao.selectUserPointList(map);
	}
	
	@Override
	public List<HashMap<String, Object>> selectUserSurveyAnswer(HashMap<String, Object> map) throws Exception {
		return dao.selectUserSurveyAnswer(map); 
	}

	@Override
	public int insertUserInfo(HashMap<String, Object> map) throws Exception {
		return dao.insertUserInfo(map);
	}

	@Override
	public int updateUserInfo(HashMap<String, Object> map) throws Exception {
		return dao.updateUserInfo(map);
	}

	@Override
	public int deleteUserInfo(HashMap<String, Object> map) throws Exception {
		return (dao.deleteUserInfo(map) + dao.deleteUserPoint(map));
	}

	@Override
	public HashMap<String, Object> selectAdminInfo(int m_idx) throws Exception {
		return dao.selectAdminInfo(m_idx);
	}

	@Override
	public int insertAdminInfo(HashMap<String, Object> map) throws Exception {
		return dao.insertAdminInfo(map);
	}

	@Override
	public int updateAdminInfo(HashMap<String, Object> map) throws Exception {
		return dao.updateAdminInfo(map);
	}

	@Override
	public int deleteAdminInfo(HashMap<String, Object> map) throws Exception {
		return dao.deleteAdminInfo(map);
	}

	@Override
	public void insertSurveyInfo(HashMap<String, Object> surveyInfo, 
		List<HashMap<String, Object>> questList,
		List<HashMap<String, Object>> answerList) throws Exception {
		int s_idx = dao.insertSurvey(surveyInfo);
		
		for (HashMap<String, Object> quest : questList) {
			quest.put("s_idx", s_idx);
			dao.insertSurveyQuestion(quest);
		}
		
		for (HashMap<String, Object> answer : answerList) {
			answer.put("s_idx", s_idx);
			HashMap<String, Object> temp = new HashMap<String, Object>();
			for (int i=1; i<=4; i++) {
				temp.put("a_idx", i);
				temp.put("s_idx", s_idx);
				temp.put("q_idx", answer.get("q_idx"));
				temp.put("text", answer.get("text"+i));
				dao.insertSurveyAnswer(temp);
				temp.clear();
			}
		}
	}

	@Override
	public void updateSurveyInfo(HashMap<String, Object> surveyInfo, 
		List<HashMap<String, Object>> questList,
		List<HashMap<String, Object>> answerList) throws Exception {
		dao.updateSurvey(surveyInfo);
		dao.deleteSurveyQueston(Integer.parseInt((String) surveyInfo.get("s_idx")));
		dao.deleteSurveyAnswer(Integer.parseInt((String) surveyInfo.get("s_idx")));
		
		for (HashMap<String, Object> quest : questList) {
			quest.put("s_idx", surveyInfo.get("s_idx"));
			dao.insertSurveyQuestion(quest);
		}

		for (HashMap<String, Object> answer : answerList) {
			HashMap<String, Object> temp = new HashMap<String, Object>();
			for (int i=1; i<=4; i++) {
				temp.put("a_idx", i);
				temp.put("s_idx", surveyInfo.get("s_idx"));
				temp.put("q_idx", answer.get("q_idx"));
				temp.put("text", answer.get("text"+i));
				dao.insertSurveyAnswer(temp);
				temp.clear();
			}
		}
	}
	
	@Override
	public void deleteSurveyInfo(int s_idx) throws Exception {
		dao.deleteSurvey(s_idx);
		dao.deleteSurveyQueston(s_idx);
		dao.deleteSurveyAnswer(s_idx);
	}

	@Override
	public List<HashMap<String, Object>> selectAllInquire(HashMap<String, Object> map, String txtSearch) throws Exception {
		map.put("txtSearch", txtSearch);
		return dao.selectAllInquire(map);
	}

	@Override
	public int countAllInquire(HashMap<String, Object> txtSearch) throws Exception {
		return dao.countAllInquire(txtSearch);
	}

	@Override
	public HashMap<String, Object> selectInquireAnswer(int i_idx) throws Exception {
		return dao.selectInquireAnswer(i_idx);
	}

	@Override
	public int updateInquireAnswer(HashMap<String, Object> map) throws Exception {
		return dao.updateInquireAnswer(map);
	}
	
	@Override
	public int countAllPoll(HashMap<String, Object> txtSearch) throws Exception {
		return dao.countAllPoll(txtSearch);
	}

	@Override
	public List<HashMap<String, Object>> selectAllPoll(HashMap<String, Object> map, String txtSearch) throws Exception {
		map.put("txtSearch", txtSearch);
		return dao.selectAllPoll(map);
	}
	
	@Override
	public int countAllFAQ(HashMap<String, Object> txtSearch) throws Exception {
		return dao.countAllFAQ(txtSearch);
	}

	@Override
	public List<HashMap<String, Object>> selectAllFAQ(HashMap<String, Object> map, String txtSearch) throws Exception {
		map.put("txtSearch", txtSearch);
		return dao.selectAllFAQ(map);
	}
	
	
	
	@Override
	public int insertPoll(HashMap<String, Object> pollInfo, HashMap<String, Object> answer) throws Exception {
		int p_idx = dao.insertPoll(pollInfo);
		int minus = 0;
		HashMap<String, Object> temp = new HashMap<String, Object>();
		for (int i=1; i<=5; i++) {
			if (!((String) answer.get("text"+i)).trim().isEmpty() && answer.get("text"+i) != null) {
				minus++;
				temp.put("text", answer.get("text"+i));
				temp.put("pa_idx", minus);
				temp.put("p_idx", p_idx);
				dao.insertPollAnswer(temp);
				temp.clear();
			}
		}
		return 0;
	}
	
	@Override
	public int updatePoll(HashMap<String, Object> pollInfo, HashMap<String, Object> answer) throws Exception {
		int result = dao.updatePoll(pollInfo);
		dao.deletePollAnswer(pollInfo);
		int minus = 0;
		HashMap<String, Object> temp = new HashMap<String, Object>();
		for (int i=1; i<=5; i++) {
			if (!((String) answer.get("text"+i)).trim().isEmpty() && answer.get("text"+i) != null) {
				minus++;
				temp.put("text", answer.get("text"+i));
				temp.put("pa_idx", minus);
				temp.put("p_idx", pollInfo.get("p_idx"));
				dao.insertPollAnswer(temp);
				temp.clear();
			}
		}
		return 0;
	}

	@Override
	public int deletePoll(HashMap<String, Object> map) throws Exception {
		dao.deletePoll(map);
		dao.deletePollAnswer(map);
		return 0;
	}

	@Override
	public List<HashMap<String, Object>> selectAnalyzeSurveyAll(HashMap<String, Object> map) throws Exception {
		return dao.selectAnalyzeSurveyAll(map);
	}
	
	@Override
	public List<HashMap<String, Object>> selectAnalyzeSurveyJoinPeople(HashMap<String, Object> map) throws Exception {
		return dao.selectAnalyzeSurveyJoinPeople(map);
	}
	
	@Override
	public List<HashMap<String, Object>> selectAnalyzeSurveyJoinResult(HashMap<String, Object> map) throws Exception {
		return dao.selectAnalyzeSurveyJoinResult(map);
	}

	@Override
	public List<HashMap<String, Object>> selectAnalyzePollAll(HashMap<String, Object> map) throws Exception {
		return dao.selectAnalyzePollAll(map);
	}

	@Override
	public List<HashMap<String, Object>> selectAnalyzePollJoinPeople(HashMap<String, Object> map) throws Exception {
		return dao.selectAnalyzePollJoinPeople(map);
	}
	
	@Override
	public List<HashMap<String, Object>> selectAnalyzePollJoinResult(HashMap<String, Object> map) throws Exception {
		return dao.selectAnalyzePollJoinResult(map);
	}

	@Override
	public int countAllAnalyzePoint() throws Exception {
		return dao.countAllAnalyzePoint();
	}

	@Override
	public List<HashMap<String, Object>> selectAnalyzePointList(HashMap<String, Object> map) throws Exception {
		return dao.selectAnalyzePointList(map);
	}

	
}





