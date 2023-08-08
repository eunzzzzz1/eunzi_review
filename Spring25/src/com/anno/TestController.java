package com.anno;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("anno.testController")
public class TestController {
	
	@RequestMapping(value="/demo/write.action", method= {RequestMethod.GET})
	//해당 경로가 왔을 때 이 메소드로 와
		// get방식으로 찾아오면 (즉, 경로를 주소에 쳐서 찾아오면) 로그인 창을 띄우고
		// post방식으로 찾아오면 (즉, 로그인 폼에서 submit버튼을 누르면) 결과창이 뜨도록 할 수 있음
		// 얘는 get방식!
			
	public String write() throws Exception{
		// 어노테이션을 활용한 스프링은 메소드의 이름으로 찾아가지 않기 때문에
		// 메소드의 이름을 마음대로 지어줘도 된다.
		
		
		return "anno/created";
		
	}
	
	@RequestMapping(value="/demo/write.action", method= {RequestMethod.POST})
		// post방식으로 찾아오면 (즉, 로그인 폼에서 submit버튼을 누르면) 결과창이 뜨도록
	public String write_ok(TestCommand command, HttpServletRequest req) throws Exception{
							// 필요한 객체들을 해당 메소드의 매개변수로 선언해주면
							// 알아서 매개변수로 들어온다...! 객체 생성 따로 해줄 필요 없이...!
							// 진짜 편하다!!!!!!
		
		String message = "이름 : " + command.getUserName();
		message += ", 아이디 : " + command.getUserId();
		
		req.setAttribute("message", message);
		
		return "anno/result";
	}

	@RequestMapping(value="/demo/save.action", method= {RequestMethod.GET,RequestMethod.POST})
	//get방식을 때도, post방식일 때도 모두 이 메소드로 와라!
	public String save(TestCommand command, HttpServletRequest req) {
		
		//처음 /demo/save.action에 접속했을 땐 command는 null일 것 
		if(command==null||command.getMode()==null||command.getMode().equals("")) {
			return "anno/save";
			// 처음 창을 띄울 때는, anno/save를 띄워라
		}
		
		/**
		 * 위 if문을 뛰어넘겼다면,
		 * 1. command 안에는 데이터가 들어있을 것
		 * 2. mode에는 "insert"가 들어있을 것
		 */
		
		String message = "이름 : " + command.getUserName();
		message += ", 아이디 : " + command.getUserId();
		
		req.setAttribute("message", message);
		
		return "anno/result";
		
	}
	@RequestMapping(value="/demo/demo.action", method= {RequestMethod.GET,RequestMethod.POST})
	public String demo(String userId, String userName, String mode, HttpServletRequest req) {
		// 이런식으로 Form 전체를 받아내는 것이 아니라, Form내의 특정 매개변수도 받아낼 수 있다.
		
		if(mode==null||mode.equals("")) {
			return "anno/demo";
		}
		
		String message = "이름 : " + userName;
		message += ", 아이디 : " + userId;
		
		req.setAttribute("message", message);
		
		return "anno/result";
	}
	
	
	
	
}
