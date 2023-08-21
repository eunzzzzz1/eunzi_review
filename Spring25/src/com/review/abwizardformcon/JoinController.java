package com.review.abwizardformcon;

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

public class JoinController extends AbstractWizardFormController{
	
	/** --------------------------------------------
	 * 순서
	 * ---------------------------------------------
	 * 1. 생성자를 통해 CommandClass 세팅하기
	 * 2. processFinish 필수로 구현하기
	 * 		: _finish submit을 눌렀을 때의 ModelAndView 작성
	 * 3. 필요 메소드 구현하기
	 *  - postProcessPage
	 *  	: 각 페이지별로 post방식으로 데이터가 넘어올 때 구현해야 하는 메소드
	 *  - processCancel
	 *  	: _cancel submit을 눌렀을 때의 ModelAndView 작성
	 *  - referenceData
	 *  	: 각 page가 View 되기 전에 넘겨줘야 할 데이터를 작성하는 메소드
	 *  ---------------------------------------------
	 */
	
	public JoinController() {
		setCommandClass(JoinCommand.class);
		setCommandName("join");
	}
	

	/**
	 * 모든 폼을 작성한 후,
	 *  _finish submit 버튼을 눌렀을 때의 ModelandView를 작업
	 */
	@Override
	protected ModelAndView processFinish(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			BindException arg3) throws Exception {
		// TODO Auto-generated method stub
		return new ModelAndView("review_abwizardformcon/join_complete");
	}


	@Override
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page)
			throws Exception {
		
		JoinCommand join = (JoinCommand)command;
			// 폼에 입력한 데이터는 Object command 매개변수로 들어온다.
			// 매개변수로 들어온 command를 우리가 사용할 Command 객체로 다운캐스팅 해준다.
		
		// 각 page별 작업은 if문으로 나누어 작업해주면 된다.
		if(page==0) {
			
			// eunzi라는 ID로 가입을 하려고하면 에러를 띄워보자.
			if(join.getUserId().equals("eunzi")) {
				// 만약 입력한 아이디가 eunzi와 같으면, Errors 객체에 값을 넣자.
				
				String str = join.getUserId() + "는 이미 존재하는 ID입니다.";
				errors.rejectValue("message", str);
				// errors 객체에 rejectValue를 설정하게 되면
				// 다음페이지로 넘어가지 못하고 폼 입력을 다시 요구한다.
				
				join.setMessage(str);
			}
			
		} else if(page==1) {
			
			//주민등록번호1이 6자리가 아니거나
			//주민등록번호2가 7자리가 아니면 오류를 띄우자
			
			if(join.getUserIdentifycationNum1().length()!=6
				|| join.getUserIdentifycationNum2().length()!=7) {
				
				String str = "주민등록번호를 다시 확인하세요.";
				errors.rejectValue("message", str);
				
				join.setMessage(str);
				
			}
			
		} else if(page==2) {
			
			// 동의여부에 체크를 하지 않으면 에러를 띄우자
			if(!join.isCheck()) {
				
				String str = "약관 동의는 필수 사항입니다.";
				errors.rejectValue("message", str);
				
				join.setMessage(str);
			}
			
		}
		
	}


	@Override
	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		// TODO Auto-generated method stub
		return new ModelAndView("review_abwizardformcon/join_fail");
	}


	@Override
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		
		if(page==0) {
			
			List<String> types = new ArrayList<>();
			
			types.add("일반회원");
			types.add("특별회원");
			types.add("기업회원");
			
			// 이 list 객체를 Map에 담아준다.
			
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map.put("types", types);
			// join_form1에서 foreach문의 items를 ${types}로 설정해주었으므로
			// 그에 맞춰 Key를 설정한다.
			
			return map;
			
		}
		
		return null;
		// if문으로 반환값을 전달하는 0page 같은 경우를 제외하고는
		// 미리 데이터를 넘겨줄 page가 없기 때문에
		// null값을 기본적으로 돌려준다.
		
	}
	
	
	
	
	

}
