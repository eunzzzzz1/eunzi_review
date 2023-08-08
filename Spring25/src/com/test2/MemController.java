package com.test2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

public class MemController extends AbstractWizardFormController {

	
	// MemberCommand의 객체가 생성되어야 한다.
	public MemController() {
		setCommandClass(MemberCommand.class);
		setCommandName("info");
		
		//info라는 이름의 MemberCommand 객체를 생성
		// Form에 입력한 데이터가 이 객체에 자동으로 들어오고
		// 각 메소드 별 Object command 매개변수로 자동으로 들어오게 된다.
	}

	@Override
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page)
			throws Exception {
		
		MemberCommand mem = (MemberCommand)command;
		
		if(page==0) { //mem1.jsp 에서의 작업
			
			if(mem.getJumin().equals("1234")) {
				String str = mem.getName() + "님은 이미 가입된 회원입니다.";
				// 만약 1234라는 주민번호가 들어오면, 가입된 회원임을 말해준다.
				
				errors.rejectValue("message", str);
				// error객체가 에러를 가지고 있으면 다음페이지로 넘어가지 못하고, 입력을 다시 요구한다.
				// "message"라는 에러를 가지게끔 설정한 것.
				// 그러므로 이미 주민번호가 있는 해당 경우에는 다음 페이지로 넘어가지 못하도록 에러를 발생시켜야한다.
				// str에 담긴 문구로 에러가 뜨게끔 하자.
				mem.setMessage(str);
				// mem1.jsp의 message부분에 뜨게 될 문구
			}
			
		} else if (page==1) {
			
			// mem2.jsp에서 실행할 코드를 여기서 작업해주면 된다.
			
		}
		
	}

	@Override
	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		
		//_cancel 이라는 이름을 가진 submit 버튼을 눌렀을 때 실행될 코드를 작성하는 곳
		
		return new ModelAndView("test2/mem_cancel",errors.getModel());
		// test2 폴더의 mem_cancel.jsp로 이동
		// 에러를 가지고 이동한다.
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			BindException arg3) throws Exception {
		// TODO Auto-generated method stub
		return new ModelAndView("test2/mem_ok");
	}

	@Override
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		// int page가 매개변수로 들어온다.
		// page별로 if문으로 각각의 page에 들어가기 전 준비할 코드를 작성해주면 된다.
		
		// mem2.jsp에 types를 넘겨주어야한다.
		if(page==1) {
			
			List<String> types = new ArrayList<>();
			types.add("일반회원");
			types.add("특별회원");
			types.add("기업회원");
			
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			
			map.put("types", types);
			
			return map;
			
		}
		
		
		return null;
		// 1p(mem2.jsp)가 아닌 경우에는 딱히 값을 넘겨줄 필요가 없으므로, null을 넘긴다.
	}

	
	
	
	
}
