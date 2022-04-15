package com.ddv.common;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class SetFileName {
	private File file;
	private String fNameOrig;
	private String[] fNames;
	private String ext_o;			// 기존 파일 확장명 (ex. .png)
	private String ext_n;			// 신규 파일 확장명 (ex. .png)
	private String tempName_o;		// 기존 파일 확장명 (이름)
	private String tempName_n;		// 신규 파일 파일명 (이름)
	private String tempFullName_o;	// 기존 파일 파일명 (이름+넘버)
	private String numBox_o;		// 기존 파일 넘버링 (ex. (1))
	private String num_o;			// 기존 파일 순수 넘버 (ex. 1)
	private String num_n;			// 신규 파일 순수 넘버 (ex. 3)
	
	public SetFileName() {
	}
	
	public SetFileName(File file, String fNameOrig) {
		this.file = file;
		this.fNameOrig = fNameOrig;
	}
	
	public String getFileNameOrig(File file, String fNameOrig) {
		fNames = file.list();
		Arrays.sort(fNames, Collections.reverseOrder());
		
		// New 파일명 분리
		ext_n = fNameOrig.substring(fNameOrig.lastIndexOf("."));
		tempName_n = fNameOrig.substring(0, fNameOrig.length()-ext_n.length());
		boolean isChange = false;
		
		// 중복 파일명 있는지 검사
		for (int i = 0; i < fNames.length; i++) {
			if (fNames[i].contains(tempName_n)) {
				// Original
				// 1 확장자만, 2 확장자 제외 파일명 (넘버 포함), 3 괄호+넘버, 4 확장자&넘버 제외 순수 파일명
				ext_o = fNames[i].substring(fNames[i].lastIndexOf("."));
				tempFullName_o = fNames[i].substring(0, fNames[i].length()-ext_o.length());
				numBox_o = tempFullName_o.substring(tempFullName_o.lastIndexOf("("));
				tempName_o = fNames[i].substring(0, fNames[i].length()-(ext_o.length()+numBox_o.length()));
				
				if (tempName_o.equals(tempName_n)) {
					// 5 넘버
					num_o = numBox_o.substring(1, numBox_o.length()-1);
					if (isNumeric(num_o)) {
						int num = Integer.parseInt(num_o);
						num++;
						num_n = Integer.toString(num);
						fNameOrig = tempName_n + "(" + num_n + ")" + ext_n;
						isChange = true;
					}
				}
				break;
			}
		}
		// 중복되는 파일이 아니라면 무조건 (1) 붙여서 서버에 업로드 시켜줌
		if (!isChange) fNameOrig = tempName_n + "(1)" + ext_n;
		return fNameOrig;
	}
	
	// 숫자 형태인지 체크
	public boolean isNumeric(String input) {
		try {
			Double.parseDouble(input);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
	
}
