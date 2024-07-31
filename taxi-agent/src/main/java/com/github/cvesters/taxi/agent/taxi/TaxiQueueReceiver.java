package com.github.cvesters.taxi.agent.taxi;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TaxiQueueReceiver {

	@RabbitListener(queues = TaxiQueueConfig.TAXI_QUEUE)
	public void updateBooking(final Message message) {
		final String content = new String(message.getBody());
		System.out.println("MESSAGE: " + content);
	}
}
