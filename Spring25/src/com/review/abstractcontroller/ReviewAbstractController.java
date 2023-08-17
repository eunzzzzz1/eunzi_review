package com.review.abstractcontroller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class ReviewAbstractController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		String message;
		
		if(hour>=18) {
			message = "지금 시간은 "+ hour + "시. 집에 갈 시간이야!";
		} else {
			message = "지금 시간은 "+ hour + "시. 아직 수업시간이야....";
		}
		
		request.setAttribute("message", message);
		
		return new ModelAndView("review_abstractcontroller/review");
	}

}
