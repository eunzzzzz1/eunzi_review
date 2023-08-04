package com.file;

import org.apache.struts.action.ActionForm;

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
	
	
	

}
