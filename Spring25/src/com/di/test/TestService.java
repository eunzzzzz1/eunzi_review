package com.di.test;

public class TestService {
	
	
	/**-------------------------------------------------
	 * 의존성 주입하기
	 * -------------------------------------------------
	 * Test test = new TestImpl1();
	 * Test test = new TestImpl2();
	 * 의존성 주입 설계를 통해
	 * TestService 클래스까지 들어오지 않아도
	 * 외부에서 원하는 TestImpl 객체를 불러올 수 있게 된다.
	 * -------------------------------------------------
	 */

	private Test test;
	
	public TestService() {}
	
	// 생성자로 의존성 주입
	public TestService(Test test) {
		this.test = test;
	}
	
	// 메소드로 의존성 주입 -> 기본 생성자가 있어야 한다.
	public void setTest(Test test) {
		this.test = test;
	}
	
	//getter
	public String getValue() {
		return test.result();
	}
	
	


}
