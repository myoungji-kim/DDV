package com.ddv.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ddv.dao.HomeDAO;

@Service("service")
public class HomeServiceImpl implements HomeService {
	
	@Resource(name="dao")
	private HomeDAO dao;
	
	@Override
	public int countAllSurvey(String searchKey) {
		return dao.countAllSurvey(searchKey);
	}
	
	@Override
	public int countAllOpenPoll(HashMap<String, Object> map) throws Exception {
		return dao.countAllOpenPoll(map);
	}
	
	@Override
	public int countAllOpenMyPoll(HashMap<String, Object> map) throws Exception {
		return dao.countAllOpenMyPoll(map);
	}

	@Override
	public List<HashMap<String, Object>> selectAllSurvey(HashMap<String, Object> boardInfo) {
		return dao.selectAllSurvey(boardInfo);
	}

	@Override
	public HashMap<String, Object> selectOneSurvey(HashMap<String, Object> map) {
		return dao.selectOneSurvey(map);
	}
	
	@Override
	public HashMap<String, Object> surveyAlreadyJoin(HashMap<String, Object> map) throws Exception {
		return dao.surveyAlreadyJoin(map);
	}
	
	@Override
	public void insertUserSurveyAnswer(HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> temp = new HashMap<String, Object>();
		int q_idx = 1;
		for (String answer : (List<String>) map.get("answerList")) {
			temp.put("s_idx", map.get("s_idx"));
			temp.put("m_idx", map.get("m_idx"));
			temp.put("point", map.get("point"));
			temp.put("q_idx", q_idx);
			temp.put("a_idx", answer);
			
			dao.insertUserSurveyAnswer(temp);
			q_idx += 1;
		}
	}
	
	@Override
	public void insertUserSurveyJoinPoint(HashMap<String, Object> map) throws Exception {
		dao.insertUserSurveyJoinPoint(map);
	}

	@Override
	public String dupeIdCheck(String userid) {
		return dao.dupeIdCheck(userid);
	}

	@Override
	public int insertMember(HashMap<String, Object> map) {
		return dao.insertMember(map);
	}
	
	@Override
	public HashMap<String, Object> findID(HashMap<String, Object> map) {
		return dao.findID(map);
	}

	@Override
	public HashMap<String, Object> login(HashMap<String, Object> map) {
		return dao.login(map);
	}

	@Override
	public HashMap<String, Object> selectMyInfo(HashMap<String, Object> map) {
		return dao.selectMyInfo(map);
	}

	@Override
	public int updateMember(HashMap<String, Object> map) {
		return dao.updateMember(map);
	}

	@Override
	public int countQuestions(HashMap<String, Object> map) {
		return dao.countQuestions(map);
	}

	@Override
	public List<HashMap<String, Object>> selectQuestionList(HashMap<String, Object> map) {
		List<HashMap<String, Object>> questList = dao.selectQuestionList(map);
		
		HashMap<String, Object> temp = new HashMap<String, Object>();
		for(HashMap<String, Object> quest : questList) {
			temp.put("s_idx", quest.get("s_idx"));
			temp.put("q_idx", quest.get("q_idx"));
			quest.put("answerList", dao.selectAnswerList(temp));
		}
		return questList;
	}

	@Override
	public List<HashMap<String, Object>> selectMyPoints(HashMap<String, Object> map) {
		return dao.selectMyPoints(map);
	}

	@Override
	public int sumMyPoints(HashMap<String, Object> map) throws Exception {
		return dao.sumMyPoints(map);
	}

	@Override
	public List<HashMap<String, Object>> selectMyInquireList(HashMap<String, Object> map) {
		return dao.selectMyInquireList(map);
	}

	@Override
	public HashMap<String, Object> selectOneInquireInfo(HashMap<String, Object> map) {
		return dao.selectOneInquireInfo(map);
	}

	@Override
	public int insertInquire(HashMap<String, Object> map) {
		return dao.insertInquire(map);
	}

	@Override
	public int insertPoll(HashMap<String, Object> map) {
		int p_idx = dao.insertPoll(map);
		int minus = 0;
		for (int i=1; i<=5; i++) {
			if (!((String) map.get("text"+i)).trim().isEmpty() && map.get("text"+i) != null) {
				HashMap<String, Object> temp = new HashMap<String, Object>();
				temp.put("text", map.get("text"+i));
				temp.put("pa_idx", i-minus);
				temp.put("p_idx", p_idx);
				dao.insertPollAnswer(temp);
			} else {
				minus += 1;
			}
		}
		return p_idx;
	}
	
	@Override
	public int updatePoll(HashMap<String, Object> map) {
		int result = dao.updatePoll(map);
		dao.deletePollAnswer(map);
		int minus = 0;
		for (int i=1; i<=5; i++) {
			if (!((String) map.get("text"+i)).trim().isEmpty() && map.get("text"+i) != null) {
				HashMap<String, Object> temp = new HashMap<String, Object>();
				temp.put("text", map.get("text"+i));
				temp.put("pa_idx", i-minus);
				temp.put("p_idx", map.get("p_idx"));
				dao.insertPollAnswer(temp);
			} else {
				minus += 1;
			}
		}
		return result;
	}

	@Override
	public int deletePoll(HashMap<String, Object> map) {
		dao.deletePoll(map);
		dao.deletePollAnswer(map);
		return 0;
	}

	@Override
	public List<HashMap<String, Object>> selectMyPollList(HashMap<String, Object> map) {
		List<HashMap<String, Object>> list = dao.selectMyPollList(map);
		
		HashMap<String, Object> temp = new HashMap<String, Object>();
		for (HashMap<String, Object> poll : list) {
//		temp.put("p_idx", poll.get("P_IDX")); // Oracle
			temp.put("p_idx", poll.get("p_idx"));
			poll.put("countAttends", dao.countMyPollAttends(temp));
			poll.put("answerList", dao.selectMyPollAnswer(temp));
			temp.clear();
		}
		return list;
	}

	@Override
	public HashMap<String, Object> selectMyPoll(HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> info = new HashMap<String, Object>();
		info.put("poll", dao.selectMyPollInfo(map));
		info.put("answers", dao.selectMyPollAnswer(map));
		return info;
	}

	@Override
	public List<HashMap<String, Object>> selectPollList(HashMap<String, Object> boardInfo) throws Exception {
		List<HashMap<String, Object>> list = dao.selectPollList(boardInfo);
		
		HashMap<String, Object> temp = new HashMap<String, Object>();
		for (HashMap<String, Object> poll : list) {
//		temp.put("p_idx", poll.get("P_IDX")); // Oracle
			temp.put("p_idx", poll.get("p_idx"));
			poll.put("countAttends", dao.countPollAttends(temp));
			poll.put("answerList", dao.selectPollAnswer(temp));
			poll.put("joinMember", dao.selectPollJoinMember(temp));
			temp.clear(); 
		}
		return list;
	}

	@Override
	public int votePoll(HashMap<String, Object> map) throws Exception {
		return dao.votePoll(map);
	}

	@Override
	public List<HashMap<String, Object>> selectFAQList() {
		return dao.selectFAQList();
	}

	@Override
	public HashMap<String, Object> selectOneFAQInfo(HashMap<String, Object> map) {
		return dao.selectOneFAQInfo(map);
	}

}
