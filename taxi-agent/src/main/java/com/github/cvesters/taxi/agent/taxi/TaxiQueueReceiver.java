package com.github.cvesters.taxi.agent.taxi;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TaxiQueueReceiver {

	@RabbitListener(queues = "#{taxiQueueConfig.getQueueName}")
	public void updateBooking(final Message message) {
		final String content = new String(message.getBody());
		System.out.println("MESSAGE: " + content);
	}
}
