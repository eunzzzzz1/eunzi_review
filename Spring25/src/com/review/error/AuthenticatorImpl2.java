package com.review.error;

public class AuthenticatorImpl2 implements Authenticator{

	@Override
	public void authen(String userId, String userPwd) throws UserException {
		
		if(!userId.equals("yunha") || !userPwd.equals("123")) {
			// 아이디가 yunha가 아니거나,
			// 비밀번호가 123이 아니면
			// 사용자 정의 에러를 발생시킨다.
			
			throw new UserException("invalid id:" + userId);
		}
		
	}

}
