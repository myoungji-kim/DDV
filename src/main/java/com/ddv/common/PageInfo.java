package com.ddv.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

public class PageInfo {
	public static final int contentNum = 5; // 한 페이지에 보여질 게시글 수
	public static final int blockNum = 5;   // 한 블록에 보여질 페이지 수

	private int totalCount; 	// 전체 게시글 수
	private String pageNum;		// 현재 페이지 (String)
	private int pageNow;		// 현재 페이지 (Int)
	private int totalPage;		// 전체 페이지 
	private int contentBegin;	// 한 페이지에서 시작하는 게시글 번호
	private int contentEnd;		// 한 페이지에서 끝나는 게시글 번호
	private int beginPage;		// 한 블럭에서 시작하는 페이지 번호
	private int endPage;		// 한 블럭에서 끝나는 페이지 번호
	private boolean btnPrev;	// 블록 이전 버튼
	private boolean btnNext;	// 블록 다음 버튼

	public PageInfo(int totalCount, String pageNum) {
		this.totalCount = totalCount;
		if (pageNum == null) this.pageNow = 1; 
		else this.pageNow = Integer.parseInt(pageNum);

		this.totalPage = getTotalPage(totalCount, contentNum);
		this.contentBegin = getContentBegin(pageNow, contentNum);
		this.contentEnd = getContentEnd(contentBegin, contentNum);
		this.beginPage = getBeginPage(pageNow, blockNum, totalPage);
		this.endPage = getEndPage(beginPage, blockNum, totalPage);
		this.btnPrev = getBtnPrev(beginPage);
		this.btnNext = getBtnNext(endPage, totalPage);
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPageNow() {
		return pageNow;
	}

	public int getTotalPage (int totalCount, int contentNum) {
		int totalPage = totalCount / contentNum;
		if (totalCount % contentNum > 0) totalPage++;
		return totalPage;
	}

	// 페이지 관련
	public int getContentBegin(int pageNow, int contentNum) { 
		// for mysql
		int contentBegin = ((pageNow-1)*contentNum);
		// for oracle
		// int contentBegin = ((pageNow-1)*contentNum) + 1;
		return contentBegin;
	}

	public int getContentEnd(int contentBegin, int contentNum) {
		// for mysql
		int contentEnd = contentBegin + contentNum;

		// for oracle
		// int contentEnd = contentBegin + contentNum - 1;
		return contentEnd;
	}

	// 블럭 관련
	public int getBeginPage (int pageNow, int blockNum, int totalPage) {
		int pg_value = (int) Math.ceil(pageNow/blockNum); // 위치값 //
		if (pageNow % blockNum != 0) pg_value++;
		int beginPage = (blockNum*(pg_value-1))+1; // 블럭 시작값 //
		return beginPage;
	}

	public int getEndPage(int beginPage, int blockNum, int totalPage) {
		int endPage = beginPage + blockNum - 1;
		if (endPage >= totalPage) endPage = totalPage;
		return endPage;
	}

	public HashMap<String, Object> getBoardInfo(){
		HashMap<String, Object> mapInfo = new HashMap<String, Object>() {{
			put("no", pageNow);
			put("contentBegin", contentBegin);
			put("contentEnd", contentEnd);
			put("contentNum", contentNum);
		}};
		return mapInfo;
	}

	public HashMap<String, Object> getPageInfo(){
		HashMap<String, Object> pageInfo = new HashMap<String, Object>() {{
			put("pageNow", pageNow);
			put("contentNum", contentNum);
			put("totalPage", totalPage);
			put("totalCount", totalCount);
			put("contentBegin", contentBegin);
			put("contentEnd", contentEnd);
			put("beginPage", beginPage);
			put("endPage", endPage);
			put("btnPrev", btnPrev);
			put("btnNext", btnNext);
		}};
		return pageInfo;
	}

	// Btn
	public boolean getBtnPrev(int beginPage) {
		if (beginPage == 1) return false;
		return true;
	}

	public boolean getBtnNext(int endPage, int totalPage) {
		if (endPage == totalPage) return false;
		return true;
	}
	
}
