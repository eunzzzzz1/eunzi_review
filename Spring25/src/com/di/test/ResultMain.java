package com.di.test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ResultMain {

	public static void main(String[] args) {
		
		String path = "com/di/test/applicationContext.xml";
		Resource res = new ClassPathResource(path);
		
		// 스프링 컨테이너 생성하기
		BeanFactory factory = new XmlBeanFactory(res);
		
		TestService ob = (TestService)factory.getBean("testService");
			//factory에 가면 testService라는 객체가 있을거야!
		System.out.println(ob.getValue());

	}

}
