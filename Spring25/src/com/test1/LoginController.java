package com.test1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class LoginController extends SimpleFormController{

	private Authenticator authenticator;
	
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	// 메소드로 의존성 주입!

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		
		LoginCommand login = (LoginCommand)command;
		
		try {
			
			//해당 클래스 생성자에 LoginAuthenticatiorImpl객체를 넘겨줄 거기 때문에 authenticator에 담겨있는 객체는
			// 로그인을 검사하는 클래스인 LoginAuthenticatiorImpl.
			//로그인을 검사하는 클래스인 LoginAuthenticatiorImpl에 login에 담긴 정보들을 넘겨줄 것
			authenticator.authen(login.getUserId(), login.getUserPwd());
			
			String message = "id: " + login.getUserId();
			message += ", Pwd: " + login.getUserPwd();
			message += ", type: " + login.getLoginType();
			 
			request.setAttribute("message", message);
			
			return new ModelAndView("test1/login_ok");
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return showForm(request,response,errors);
			// 작성 중에 에러가 발생하면(id,Pwd가 틀리면), 입력창을 다시 띄워야한다.
			// showForm = 입력창 넘어가지 말고 다시 띄우라는 코드.
		}
		
	}

	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		// 데이터를 준비해두는 공간
		// ex - 수정창을 띄울 때, 수정창에 미리 채워져있어야할 한 개의 데이터를 여기로 불러와 넘기는 등의 방식으로 사용함.
		
		List<String> loginTypes = new ArrayList<>();
		loginTypes.add("일반회원");
		loginTypes.add("특별회원");
		loginTypes.add("기업회원");
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("loginTypes", loginTypes);
		
		return map;
	}
	
	
	
}
