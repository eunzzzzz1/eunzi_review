package com.di.test;

public class TestService {
	
	// 생성자로 의존성주입하기
	private Test test;
	
	public TestService() {}
	
	public TestService(Test test) {
		this.test = test;
	}
	// 자료형이 인터페이스라고 인터페이스의 객체를 생성하면 안되고,
	
	// Test test = new TestImpl1();
	// Test test = new TestImpl2(); 이렇게 생성해야행
	// 어떤 값을 주냐에 따라서 Test가 달라짐
	// 그리고 Spring은 new로 새 객체를 생성하는걸 안 좋아한대...
	
	//getter
	public String getValue() {
		return test.result();
	} // 
	
	
	// 메소드로 의존성주입하기
	public void setTest(Test test) {
		this.test = test;
	}

}
