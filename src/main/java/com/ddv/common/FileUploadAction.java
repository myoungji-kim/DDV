package com.ddv.common;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.ddv.controller.AdminController;

public class FileUploadAction {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	ServletContext context;
			
	public String fileUpload(ServletContext context, String mode, MultipartFile mf, String o_mainimg) {
			// 저장 경로 설정
			String docPath = null;
			docPath = context.getRealPath("/resources/upload") + "/";

			// 경로 존재 유무 확인 후 생성
			File file = new File(docPath);
			if (!file.exists()) {
				logger.info("uploadPath create");
				file.mkdirs();
			}

			// 원본 파일명 가져오기 
			String fileNameOrig = mf.getOriginalFilename();
			logger.info("filenameOrig(Before): {}", fileNameOrig);

			// 파일명이 있을 경우 (업로드한 파일이 있을 경우)
			if (!fileNameOrig.isEmpty()) {
				logger.info("upload start");
				
				// 중복 파일명 비교 및 변경
				SetFileName sfn = new SetFileName();
				fileNameOrig = sfn.getFileNameOrig(file, fileNameOrig);
				logger.info("filenameOrig(After): {}", fileNameOrig);

				// 저장경로 + 원본 파일명
				file = new File(docPath + fileNameOrig);
				try {
					mf.transferTo(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				if (mode.equals("insert")) {
					fileNameOrig = "no-image.jpg";
				} else if (mode.equals("update")) {
					fileNameOrig = o_mainimg;
				}
			}
			
			return fileNameOrig;
			
			// ------------- 파일 업로드 END ------------- //
	}
}
