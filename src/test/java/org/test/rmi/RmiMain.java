package org.test.rmi;

import org.service.TestService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestService testService = (TestService) ctx.getBean("getRmiService");
		testService.test();
	}
	
}
