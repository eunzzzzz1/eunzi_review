package com.review.ab_command_con;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class UserController extends AbstractCommandController{
	
	/**
	 * 1. AbstractCommandController의 내장 메소드를 통해
	 * Command 객체를 세팅해준다.
	 */
	public UserController() {
		
		setCommandClass(UserCommand.class);
		setCommandName("userCommand");
		
	}

	@Override
	protected ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object command, BindException e)
			throws Exception {
		
		// getParameter를 하지 않아도, command 객체에 사용자의 Form 입력값이 담겨온다.
		UserCommand uc = (UserCommand)command;
		String message;
		
		message = "UserID : " + uc.getUserId() + ", UserName : " + uc.getUserName();
		
		req.setAttribute("message", message);
		return new ModelAndView("review_ab_command_con/review_formok");
	}

}
