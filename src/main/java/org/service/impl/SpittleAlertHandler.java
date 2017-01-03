package org.service.impl;

import org.dto.Spittle;
import org.springframework.stereotype.Service;

@Service("spittleAlertHandler")
public class SpittleAlertHandler {

	public void handleSpittleAlert(Spittle spittle) {
		System.out.println(spittle.getKey());
	}
}
