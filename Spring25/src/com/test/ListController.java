package com.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class ListController extends AbstractCommandController{

	
	public ListController() {
		setCommandClass(ListCommand.class);
		setCommandName("listCommand");
		// ListCommand listCommand = new ListComment(); 가 됨
		// 이 클래스의 객체가 생성될 때
		// userId와 userName이 담긴 ListCommand객체가 같이 생성됨
	}
	
	@Override
	protected ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object command, BindException errors)
			throws Exception {
		
		ListCommand vo = (ListCommand)command; //
		String message = "아이디 : " + vo.getUserId();
		message += ", 이름 : " + vo.getUserName();
		
		req.setAttribute("message", message);
		
		return new ModelAndView("test/write_ok");
	}

}
