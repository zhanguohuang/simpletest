package org.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ���ڼ������Ƿ�������Controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("test")
public class BaseController{ 	
	
	Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@RequestMapping("hello")
	public String test(HttpServletRequest request, HttpServletResponse response) {
		logger.info("request-getquery====>" + request.getQueryString());
		Enumeration<String> enumer = request.getParameterNames();
		while(enumer.hasMoreElements()) {
			logger.info(enumer.nextElement() + "====>" + request.getParameter(enumer.nextElement()));
		}
		
		return "index";
	}

}