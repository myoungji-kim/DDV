package com.ddv.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("dao")
public class HomeDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@SuppressWarnings("unchecked")
	public int countAllSurvey(String searchKey) {
		return sqlSession.selectOne("homeMapper.countAllSurvey", searchKey);
	}
	
	@SuppressWarnings("unchecked")
	public int countAllOpenPoll(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectOne("homeMapper.countAllOpenPoll", map);
	}
	
	@SuppressWarnings("unchecked")
	public int countAllOpenMyPoll(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectOne("homeMapper.countAllOpenMyPoll", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAllSurvey(HashMap<String, Object> boardInfo) {
		return sqlSession.selectList("homeMapper.selectAllSurvey", boardInfo);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectOneSurvey(HashMap<String, Object> map) {
		return sqlSession.selectOne("homeMapper.selectOneSurvey", map);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> surveyAlreadyJoin(HashMap<String, Object> map) throws Exception {
		return sqlSession.selectOne("homeMapper.surveyAlreadyJoin", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public void insertUserSurveyAnswer(HashMap<String, Object> temp) throws Exception {
		sqlSession.insert("homeMapper.insertUserSurveyAnswer", temp);
	}
	
	@SuppressWarnings("unchecked")
	public void insertUserSurveyJoinPoint(HashMap<String, Object> map) throws Exception {
		sqlSession.insert("homeMapper.insertUserSurveyJoinPoint", map);
	}
	
	@SuppressWarnings("unchecked")
	public String dupeIdCheck(String userid) {
		return sqlSession.selectOne("homeMapper.dupeIdCheck", userid);
	}
	
	@SuppressWarnings("unchecked")
	public int insertMember(HashMap<String, Object> map) {
		return sqlSession.insert("homeMapper.insertMember", map);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> login (HashMap<String, Object> map) {
		return sqlSession.selectOne("homeMapper.login", map);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> findID (HashMap<String, Object> map) {
		return sqlSession.selectOne("homeMapper.findID", map);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectMyInfo (HashMap<String, Object> map) {
		return sqlSession.selectOne("homeMapper.selectMyInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public int updateMember (HashMap<String, Object> map) {
		return sqlSession.update("homeMapper.updateMember", map);
	}
	
	@SuppressWarnings("unchecked")
	public int countQuestions (HashMap<String, Object> map) {
		return sqlSession.selectOne("homeMapper.countQuestions", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectQuestionList (HashMap<String, Object> map) {
		return sqlSession.selectList("homeMapper.selectQuestionList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectAnswerList (HashMap<String, Object> map) {
		return sqlSession.selectList("homeMapper.selectAnswerList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectMyPoints (HashMap<String, Object> map) {
		return sqlSession.selectList("homeMapper.selectMyPoints", map);
	}
	
	@SuppressWarnings("unchecked")
	public int sumMyPoints (HashMap<String, Object> map) {
		System.out.println("DAO-map: "+map);
		return sqlSession.selectOne("homeMapper.sumMyPoints", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectMyInquireList (HashMap<String, Object> map) {
		return sqlSession.selectList("homeMapper.selectMyInquireList", map);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectOneInquireInfo (HashMap<String, Object> map) {
		return sqlSession.selectOne("homeMapper.selectOneInquireInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public int insertInquire(HashMap<String, Object> map) {
		return sqlSession.insert("homeMapper.insertInquire", map);
	}
	
	@SuppressWarnings("unchecked")
	public int insertPoll(HashMap<String, Object> map) {
		sqlSession.insert("homeMapper.insertPoll", map);
		int result = Integer.parseInt((String) map.get("p_idx"));
		return result;
	}

	@SuppressWarnings("unchecked")
	public int insertPollAnswer(HashMap<String, Object> map) {
		return sqlSession.insert("homeMapper.insertPollAnswer", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectMyPollList (HashMap<String, Object> map) {
		return sqlSession.selectList("homeMapper.selectMyPollList", map);
	}
	
	@SuppressWarnings("unchecked")
	public int countMyPollAttends (HashMap<String, Object> temp) {
		return sqlSession.selectOne("homeMapper.countMyPollAttends", temp);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectMyPollAnswer (HashMap<String, Object> map) {
		return sqlSession.selectList("homeMapper.selectMyPollAnswer", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectMyPollInfo (HashMap<String, Object> map)  throws Exception {
		return sqlSession.selectOne("homeMapper.selectMyPollInfo", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public int updatePoll (HashMap<String, Object> map) {
		return sqlSession.update("homeMapper.updatePoll", map);
	}
	
	@SuppressWarnings("unchecked")
	public int deletePollAnswer (HashMap<String, Object> map) {
		return sqlSession.delete("homeMapper.deletePollAnswer", map);
	}
	
	@SuppressWarnings("unchecked")
	public int deletePoll (HashMap<String, Object> map) {
		return sqlSession.delete("homeMapper.deletePoll", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectPollList (HashMap<String, Object> boardInfo){
		return sqlSession.selectList("homeMapper.selectPollList", boardInfo);
	}
	
	
	@SuppressWarnings("unchecked")
	public int countPollAttends (HashMap<String, Object> temp) throws Exception {
		return sqlSession.selectOne("homeMapper.countPollAttends", temp);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectPollAnswer (HashMap<String, Object> map) {
		return sqlSession.selectList("homeMapper.selectPollAnswer", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> selectPollJoinMember (HashMap<String, Object> map) {
		return sqlSession.selectList("homeMapper.selectPollJoinMember", map);
	}
	
	@SuppressWarnings("unchecked")
	public int votePoll (HashMap<String, Object> map) {
		return sqlSession.insert("homeMapper.votePoll", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> selectFAQList () {
		return sqlSession.selectList("homeMapper.selectFAQList");
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectOneFAQInfo (HashMap<String, Object> map) {
		return sqlSession.selectOne("homeMapper.selectOneFAQInfo", map);
	}
	
}
 