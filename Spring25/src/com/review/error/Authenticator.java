package com.review.error;

public interface Authenticator {
	
	public void authen(String userId, String userPwd) throws UserException;

}
