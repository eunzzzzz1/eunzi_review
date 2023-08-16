package com.review.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.test1.Authenticator;
import com.test1.LoginCommand;

public class LoginController extends SimpleFormController{

	private Authenticator authenticator;
	
	
	/**-------------------------------------------
	 * 메소드를 통해 의존성 주입을 해주자.
	 * -------------------------------------------
	 *  - 아이디가 eunzi여야 하는 AuthenticatorImpl 클래스가 주입될 수도 있고,
	 *  - 아이디가 yunha여야 하는 AuthenticatorImpl2 클래스가 주입될 수도 있다.
	 * -------------------------------------------
	 */
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	

	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		
		/**
		 * review_loginError의 login.jsp를 View 하기 전 실행되는 메소드.
		 * View에 띄울 데이터들을 미리 세팅해둔다.
		 */
		
		List<String> loginTypes = new ArrayList<>();
		loginTypes.add("일반회원");
		loginTypes.add("특별회원");
		loginTypes.add("기업회원");
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("loginTypes", loginTypes);
		
		return map;
	}
	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		
		/**
		 * review_loginError의 login.jsp에서 Submit 버튼을 누르면 이 메소드가 실행된다.
		 */
		
		LoginCommand login = (LoginCommand)command;
		
		try {
			

			
			authenticator.authen(login.getUserId(), login.getUserPwd());
			/**
			 * authenticator는 이 클래스의 생성자를 통해
			 * AuthenticatorImpl 객체들 중 하나로 초기화된다.
			 * 
			 * Impl1 또는 2가 Command에 담겨온 아이디와 비밀번호를 검증하고,
			 * 일치하는 회원정보일 경우 다음 코드를 실행,
			 * 일치하지 않는 회원정보일 경우 예외처리를 통해 Error를 발생시킨다.
			 * 그리고 이 Error는 catch블럭에서 잡는다.
			 */
			
			String message = "id: " + login.getUserId();
			message += ", Pwd: " + login.getUserPwd();
			message += ", type: " + login.getLoginType();
			 
			request.setAttribute("message", message);
			/**
			 * 일치하는 아이디와 비밀번호를 입력했다면
			 * 메시지가 Request 객체에 세팅되어
			 * review_loginError 폴더의 login_ok로 넘어가게 된다.
			 */
			
			return new ModelAndView("review_loginError/login_ok");
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return showForm(request,response,errors);
			/**
			 * 에러가 발생하면(id,Pwd가 틀리면), 입력창을 다시 띄우도록 설정할 것.
			 * showForm : 에러가 발생했을 때 다시 입력창을 띄우는 메솓,
			 */
		}
		
	}


	
	
	
}