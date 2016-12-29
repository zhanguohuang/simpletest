package org.service.impl;

import org.service.TestService;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {

	public void test() {
		System.out.println("test");
	}

}
