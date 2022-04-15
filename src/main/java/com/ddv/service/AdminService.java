package com.ddv.service;

import java.util.HashMap;
import java.util.List;

public interface AdminService {
	public HashMap<String, Object> goLogin(HashMap<String, Object> map) throws Exception;
	public int countAllUser(HashMap<String, Object> txtSearch) throws Exception;
	public List<HashMap<String, Object>> selectAllUser(HashMap<String, Object> map, String txtSearch) throws Exception;
	public int countAllAdmin(HashMap<String, Object> txtSearch) throws Exception;
	public List<HashMap<String, Object>> selectAllAdmin(HashMap<String, Object> map, String txtSearch) throws Exception;
	public HashMap<String, Object> selectUserInfo(int m_idx) throws Exception;
	public int countAllSurvey(HashMap<String, Object> txtSearch) throws Exception;
	public List<HashMap<String, Object>> selectAllSurvey(HashMap<String, Object> map, String txtSearch) throws Exception;
	public List<HashMap<String, Object>> selectSurveyInfo(HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectSurveyInfoAnswer(HashMap<String, Object> map) throws Exception;
	
	public HashMap<String, Object> selectPollInfo(HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectPollInfoAnswer(HashMap<String, Object> map) throws Exception;
	
	public HashMap<String, Object> selectFAQInfo(HashMap<String, Object> map) throws Exception;
	public void insertFAQ(HashMap<String, Object> map) throws Exception;
	public void updateFAQ(HashMap<String, Object> map) throws Exception;
	public void deleteFAQ(HashMap<String, Object> map) throws Exception;
	
	
	public int countUserPointSum(HashMap<String, Object> map) throws Exception;
	public int countAllUserPoint(HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectUserPointList(HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectUserSurveyAnswer(HashMap<String, Object> map) throws Exception;
		
	public int insertUserInfo(HashMap<String, Object> map) throws Exception;
	public int updateUserInfo(HashMap<String, Object> map) throws Exception;
	public int deleteUserInfo(HashMap<String, Object> map) throws Exception;
	public HashMap<String, Object> selectAdminInfo(int m_idx) throws Exception;
	public int insertAdminInfo(HashMap<String, Object> map) throws Exception;
	public int updateAdminInfo(HashMap<String, Object> map) throws Exception;
	public int deleteAdminInfo(HashMap<String, Object> map) throws Exception;

	public void insertSurveyInfo(HashMap<String, Object> surveyInfo, List<HashMap<String, Object>> questList,
			List<HashMap<String, Object>> answerList) throws Exception;
	public void deleteSurveyInfo(int s_idx) throws Exception;
	void updateSurveyInfo(HashMap<String, Object> surveyInfo, List<HashMap<String, Object>> questList,
			List<HashMap<String, Object>> answerList) throws Exception;

	public int countAllInquire(HashMap<String, Object> txtSearch) throws Exception;
	public List<HashMap<String, Object>> selectAllInquire(HashMap<String, Object> map, String txtSearch) throws Exception;
	public HashMap<String, Object> selectInquireAnswer(int m_idx) throws Exception;

	public int updateInquireAnswer(HashMap<String, Object> map) throws Exception;
	
	
	public int countAllPoll(HashMap<String, Object> txtSearch) throws Exception;
	public List<HashMap<String, Object>> selectAllPoll(HashMap<String, Object> map, String txtSearch) throws Exception;

	public int countAllFAQ(HashMap<String, Object> txtSearch) throws Exception;
	public List<HashMap<String, Object>> selectAllFAQ(HashMap<String, Object> map, String txtSearch) throws Exception;
	int insertPoll(HashMap<String, Object> pollInfo, HashMap<String, Object> answer) throws Exception;
	int updatePoll(HashMap<String, Object> pollInfo, HashMap<String, Object> answer) throws Exception;
	int deletePoll(HashMap<String, Object> map) throws Exception;

	public List<HashMap<String, Object>> selectAnalyzeSurveyAll(HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectAnalyzeSurveyJoinPeople(HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectAnalyzeSurveyJoinResult(HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectAnalyzePollAll(HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectAnalyzePollJoinPeople(HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectAnalyzePollJoinResult(HashMap<String, Object> map) throws Exception;
	
	public int countAllAnalyzePoint() throws Exception;
	public List<HashMap<String, Object>> selectAnalyzePointList(HashMap<String, Object> map) throws Exception;
		

}
