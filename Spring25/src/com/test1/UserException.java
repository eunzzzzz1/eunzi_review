package com.test1;

public class UserException extends Exception{
	
	public UserException(String str) {
		super(str);
		// 오버로딩된 생성자에 에러메시지가 전해지면
		// 부모인 Exception에 str이 전해짐
	}

}
