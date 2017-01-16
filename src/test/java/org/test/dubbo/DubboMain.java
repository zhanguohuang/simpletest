package org.test.dubbo;

import org.service.TestService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml", "spring-dubbo.xml");
		TestService testService = (TestService) ctx.getBean("getDubboService");
		testService.test();
	}
}
