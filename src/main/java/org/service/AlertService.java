package org.service;

import org.dto.Spittle;

public interface AlertService {

	void sendSpittleAlert(Spittle spittle);
	
	Spittle receiveSpittleAlert();
}
