package com.ddv.service;

import java.util.HashMap;
import java.util.List;

public interface HomeService {
	public int countAllSurvey(String searchKey);
	public int countAllOpenPoll(HashMap<String, Object> map) throws Exception;
	public int countAllOpenMyPoll(HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectAllSurvey(HashMap<String, Object> boardInfo);
	public HashMap<String, Object> selectOneSurvey(HashMap<String, Object> map);
	public HashMap<String, Object> surveyAlreadyJoin(HashMap<String, Object> map) throws Exception;
	public void insertUserSurveyAnswer(HashMap<String, Object> map) throws Exception;
	public void insertUserSurveyJoinPoint(HashMap<String, Object> map) throws Exception;
	public String dupeIdCheck(String userid);
	public int insertMember(HashMap<String, Object> map);
	public HashMap<String, Object> findID (HashMap<String, Object> map);
	public HashMap<String, Object> login(HashMap<String, Object> map);
	public HashMap<String, Object> selectMyInfo (HashMap<String, Object> map);
	public int updateMember(HashMap<String, Object> map);
	public int countQuestions (HashMap<String, Object> map);
	public List<HashMap<String, Object>> selectQuestionList (HashMap<String, Object> map);
	public List<HashMap<String, Object>> selectMyPoints (HashMap<String, Object> map);
	public int sumMyPoints (HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectMyInquireList (HashMap<String, Object> map);
	public HashMap<String, Object> selectOneInquireInfo (HashMap<String, Object> map);
	public int insertInquire(HashMap<String, Object> map);
	public int insertPoll(HashMap<String, Object> map);
	public int updatePoll (HashMap<String, Object> map);
	public int deletePoll (HashMap<String, Object> map);
	public List<HashMap<String, Object>> selectMyPollList (HashMap<String, Object> map);
	public HashMap<String, Object> selectMyPoll (HashMap<String, Object> map) throws Exception;	
	public List<HashMap<String, Object>> selectPollList (HashMap<String, Object> boardInfo) throws Exception;
	public int votePoll (HashMap<String, Object> map) throws Exception;
	public List<HashMap<String, Object>> selectFAQList ();
	public HashMap<String, Object> selectOneFAQInfo (HashMap<String, Object> map);
		
}
