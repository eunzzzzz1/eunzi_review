package com.review.abwizardformcon;

public class JoinCommand {
	
	//join_form1
	private String userId; // 아이디
	private String userPwd; // 비밀번호
	private String joinType; // 가입유형
	
	//join_form2
	private String userName; // 이름
	private String userIdentifycationNum1; // 주민등록번호 앞자리
	private String userIdentifycationNum2; // 주민등록번호 뒷자리
	
	//join_form3
	private String userPhone; // 핸드폰번호
	private String userAddress; // 주소
	private boolean check; // 동의여부
	
	//error message
	private String message;
	
	
	
	

	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	// Getter, Setter
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIdentifycationNum1() {
		return userIdentifycationNum1;
	}
	public void setUserIdentifycationNum1(String userIdentifycationNum1) {
		this.userIdentifycationNum1 = userIdentifycationNum1;
	}
	public String getUserIdentifycationNum2() {
		return userIdentifycationNum2;
	}
	public void setUserIdentifycationNum2(String userIdentifycationNum2) {
		this.userIdentifycationNum2 = userIdentifycationNum2;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone1) {
		this.userPhone = userPhone1;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
