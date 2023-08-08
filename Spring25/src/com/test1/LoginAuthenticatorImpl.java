package com.test1;

public class LoginAuthenticatorImpl implements Authenticator{

	@Override
	public void authen(String userId, String userPwd) throws UserException {
		
		if(!userId.equals("eunzi") || !userPwd.equals("123")) {
			// kim이 아니거나 123이 아니면
			// 사용자 정의 에러를 발생시키기
			
			throw new UserException("invalid id:" + userId);
		}
		
	}

}
