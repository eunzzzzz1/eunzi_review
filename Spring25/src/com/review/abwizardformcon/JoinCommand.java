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
	private String userPhone1; // 핸드폰번호 앞자리
	private String userPhone2; // 핸드폰번호 중간자리
	private String userPhone3; // 핸드폰번호 뒷자리	
	private String userAddress; // 주소
	private String check; // 동의여부
	
	//error message
	private String message;
	
	
	
	

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
	public String getUserPhone1() {
		return userPhone1;
	}
	public void setUserPhone1(String userPhone1) {
		this.userPhone1 = userPhone1;
	}
	public String getUserPhone2() {
		return userPhone2;
	}
	public void setUserPhone2(String userPhone2) {
		this.userPhone2 = userPhone2;
	}
	public String getUserPhone3() {
		return userPhone3;
	}
	public void setUserPhone3(String userPhone3) {
		this.userPhone3 = userPhone3;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
