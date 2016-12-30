package org.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dto.Spittle;
import org.service.AlertService;
import org.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@Autowired
	private TestService testService;
	@Autowired
	private AlertService alertService;
	
	@RequestMapping("/test123")
	public void testService(HttpServletRequest request, HttpServletResponse reponse) {
		testService.test();
	}
	
	@RequestMapping("/testJms")
	public void testJms(HttpServletRequest request, HttpServletResponse reponse) {
		alertService.sendSpittleAlert(new Spittle("can you receive it?"));
		Spittle spittle = alertService.receiveSpittleAlert();
		System.out.println(spittle.getKey());
	}
}
