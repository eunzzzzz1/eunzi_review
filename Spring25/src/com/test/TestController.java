package com.test;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class TestController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		String message;
		
		if(hour>=6 && hour<8) {
			message = "기상 기상!!!";
		} else if (hour>=8 && hour<13) {
			message = "지각... 지각...";
		} else if (hour>=13 && hour<14) {
			message = "점심시간~";
		} else if (hour>=14 && hour<18) {
			message = "오후시간 Zzz";
		} else {
			message = "bye bye....";
		}
		
		
		req.setAttribute("message", message); // 얘가 데이터니까 model!
		
		return new ModelAndView("test/view"); // test 폴더의 view.jsp를 가리킴
	}

}
