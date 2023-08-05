package com.file;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class FileForm extends ActionForm{

	private static final long serialVersionUID = 1L;
	
	/*
	 * fileTest 테이블 컬럼명
	 * num, subject, saveFileName, OriginalFileName
	 */
	
	private int num;
	private String subject;
	private String saveFileName;
	private String originalFileName;
	
	// 일련번호
	private int listNum;
	
	
	// 파일 관련 변수들
	
	// 중요, FormFile이 파일처리를 하기 때문에 여기 넣어줘야 함.
	private FormFile fileUpload;
	// 파일 다운로드 링크
	private String fileDownloadUrl;
	
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String getFileDownloadUrl() {
		return fileDownloadUrl;
	}
	public void setFileDownloadUrl(String fileDownloadUrl) {
		this.fileDownloadUrl = fileDownloadUrl;
	}

	
	
	

}
