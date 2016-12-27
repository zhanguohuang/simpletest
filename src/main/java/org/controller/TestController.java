package org.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@Autowired
	private TestService testService;
	
	@RequestMapping("/test")
	public void testService(HttpServletRequest request, HttpServletResponse reponse) {
		testService.test();
	}
}
