package org.test.jms;

import org.dto.Spittle;
import org.service.AlertService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpittleJmsMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		AlertService alertService = context.getBean(AlertService.class);
		Spittle spittle = new Spittle("test");
		alertService.sendSpittleAlert(spittle);
	}
}
