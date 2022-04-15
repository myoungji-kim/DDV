package com.ddv.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("adminDao")
public class AdminDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> goLogin(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectOne("adminMapper.goLogin", map);
	}
	
	@SuppressWarnings("unchecked")
	public int countAllUser(HashMap<String, Object> txtSearch) throws Exception {
		return sqlSession.selectOne("adminMapper.countAllUser", txtSearch);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAllUser(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAllUser", map);
	}
	
	@SuppressWarnings("unchecked")
	public int countAllAdmin(HashMap<String, Object> txtSearch) throws Exception {
		return sqlSession.selectOne("adminMapper.countAllAdmin");
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAllAdmin(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAllAdmin", map);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectUserInfo(int m_idx) throws Exception {
		return sqlSession.selectOne("adminMapper.selectUserInfo", m_idx);
	}
	
	@SuppressWarnings("unchecked")
	public int countAllSurvey(HashMap<String, Object> txtSearch) throws Exception {
		return sqlSession.selectOne("adminMapper.countAllSurvey", txtSearch);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAllSurvey(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAllSurvey", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectSurveyInfo(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectSurveyInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectSurveyInfoAnswer(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectSurveyInfoAnswer", map);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectPollInfo(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectOne("adminMapper.selectPollInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectPollInfoAnswer(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectPollInfoAnswer", map);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectFAQInfo(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectOne("adminMapper.selectFAQInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public void insertFAQ(HashMap<String, Object> map) throws Exception {
		sqlSession.insert("adminMapper.insertFAQ", map);
	}
	
	@SuppressWarnings("unchecked")
	public void updateFAQ(HashMap<String, Object> map) throws Exception {
		sqlSession.update("adminMapper.updateFAQ", map);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteFAQ(HashMap<String, Object> map) throws Exception {
		sqlSession.delete("adminMapper.deleteFAQ", map);
	}
	
	@SuppressWarnings("unchecked")
	public int countUserPointSum(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectOne("adminMapper.countUserPointSum", map);
	}
	
	@SuppressWarnings("unchecked")
	public int countAllUserPoint(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectOne("adminMapper.countAllUserPoint", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectUserPointList(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectUserPointList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectUserSurveyAnswer(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectUserSurveyAnswer", map);
	}
	
	@SuppressWarnings("unchecked")
	public int insertUserInfo(HashMap<String, Object> map) throws Exception {
		return sqlSession.insert("adminMapper.insertUserInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public int updateUserInfo(HashMap<String, Object> map) throws Exception {
		return sqlSession.update("adminMapper.updateUserInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public int deleteUserInfo(HashMap<String, Object> map) throws Exception {
		return sqlSession.delete("adminMapper.deleteUserInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public int deleteUserPoint(HashMap<String, Object> map) throws Exception {
		return sqlSession.delete("adminMapper.deleteUserPoint", map);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectAdminInfo(int m_idx) throws Exception {
		return sqlSession.selectOne("adminMapper.selectAdminInfo", m_idx);
	}
	
	@SuppressWarnings("unchecked")
	public int insertAdminInfo(HashMap<String, Object> map) throws Exception {
		return sqlSession.insert("adminMapper.insertAdminInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public int updateAdminInfo(HashMap<String, Object> map) throws Exception {
		return sqlSession.update("adminMapper.updateAdminInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public int deleteAdminInfo(HashMap<String, Object> map) throws Exception {
		return sqlSession.delete("adminMapper.deleteAdminInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public int insertSurvey(HashMap<String, Object> surveyInfo) throws Exception {
		sqlSession.insert("adminMapper.insertSurvey", surveyInfo);
		int result = Integer.parseInt(String.valueOf(surveyInfo.get("s_idx")));
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int insertSurveyQuestion(HashMap<String, Object> quest) throws Exception {
		return sqlSession.insert("adminMapper.insertSurveyQuestion", quest);
	}
	
	@SuppressWarnings("unchecked")
	public int insertSurveyAnswer(HashMap<String, Object> answer) throws Exception {
		return sqlSession.insert("adminMapper.insertSurveyAnswer", answer);
	}
	
	@SuppressWarnings("unchecked")
	public int updateSurvey(HashMap<String, Object> surveyInfo) throws Exception {
		return sqlSession.update("adminMapper.updateSurvey", surveyInfo);
	}
	
	@SuppressWarnings("unchecked")
	public int deleteSurvey(int s_idx) throws Exception {
		return sqlSession.delete("adminMapper.deleteSurvey", s_idx);
	}
	
	@SuppressWarnings("unchecked")
	public int deleteSurveyQueston(int s_idx) throws Exception {
		return sqlSession.delete("adminMapper.deleteSurveyQueston", s_idx);
	}
	
	@SuppressWarnings("unchecked")
	public int deleteSurveyAnswer(int s_idx) throws Exception {
		return sqlSession.delete("adminMapper.deleteSurveyAnswer", s_idx);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAllInquire(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAllInquire", map);
	}
	
	@SuppressWarnings("unchecked")
	public int countAllInquire(HashMap<String, Object> txtSearch) throws Exception {
		return sqlSession.selectOne("adminMapper.countAllInquire", txtSearch);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectInquireAnswer(int i_idx) throws Exception {
		return sqlSession.selectOne("adminMapper.selectInquireAnswer", i_idx);
	}
	
	@SuppressWarnings("unchecked")
	public int updateInquireAnswer(HashMap<String, Object> map) throws Exception {
		return sqlSession.update("adminMapper.updateInquireAnswer", map);
	}
	
	@SuppressWarnings("unchecked")
	public int countAllPoll(HashMap<String, Object> txtSearch) throws Exception {
		return sqlSession.selectOne("adminMapper.countAllPoll", txtSearch);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAllPoll(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAllPoll", map);
	}
	
	@SuppressWarnings("unchecked")
	public int countAllFAQ(HashMap<String, Object> txtSearch) throws Exception {
		return sqlSession.selectOne("adminMapper.countAllFAQ", txtSearch);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAllFAQ(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAllFAQ", map);
	}
	
	@SuppressWarnings("unchecked")
	public int insertPoll(HashMap<String, Object> map) {
		sqlSession.insert("adminMapper.insertPoll", map);
		int result = Integer.parseInt(String.valueOf( map.get("p_idx")));
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int insertPollAnswer(HashMap<String, Object> map) {
		return sqlSession.insert("adminMapper.insertPollAnswer", map);
	}
	
	@SuppressWarnings("unchecked")
	public int updatePoll (HashMap<String, Object> map) {
		return sqlSession.update("adminMapper.updatePoll", map);
	}
	
	@SuppressWarnings("unchecked")
	public int deletePollAnswer (HashMap<String, Object> map) {
		return sqlSession.delete("adminMapper.deletePollAnswer", map);
	}
	
	@SuppressWarnings("unchecked")
	public int deletePoll (HashMap<String, Object> map) {
		return sqlSession.delete("adminMapper.deletePoll", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAnalyzeSurveyAll(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAnalyzeSurveyAll", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAnalyzeSurveyJoinPeople(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAnalyzeSurveyJoinPeople", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAnalyzeSurveyJoinResult(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAnalyzeSurveyJoinResult", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAnalyzePollAll(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAnalyzePollAll", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAnalyzePollJoinPeople(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAnalyzePollJoinPeople", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAnalyzePollJoinResult(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAnalyzePollJoinResult", map);
	}
	
	@SuppressWarnings("unchecked")
	public int countAllAnalyzePoint() throws Exception {
		return sqlSession.selectOne("adminMapper.countAllAnalyzePoint");
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAnalyzePointList(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectList("adminMapper.selectAnalyzePointList", map);
	}
}
 