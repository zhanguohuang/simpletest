package org.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.dto.Spittle;
import org.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.JmsUtils;
import org.springframework.stereotype.Service;

@Service("alertService")
public class AlertServiceImpl implements AlertService {

	private JmsOperations jmsOperations;
	
	@Autowired
	public AlertServiceImpl(JmsOperations jmsOperations) {
		this.jmsOperations = jmsOperations;
	}
	
	public void sendSpittleAlert(final Spittle spittle) {
		jmsOperations.send("spittle.alert.queue", new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(spittle);
			}
		});
	}

	public Spittle receiveSpittleAlert() {
		try {
			ObjectMessage receivedMessage = (ObjectMessage) jmsOperations.receive("spittle.alert.queue");
			return (Spittle) receivedMessage.getObject();
		} catch(JMSException jmsException) {
			throw JmsUtils.convertJmsAccessException(jmsException);
		}
	}

}
