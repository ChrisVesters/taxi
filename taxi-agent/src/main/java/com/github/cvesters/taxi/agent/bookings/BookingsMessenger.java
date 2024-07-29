package com.github.cvesters.taxi.agent.bookings;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingsMessenger {

	public static final String QUEUE_NAME = "new-bookings";
	public static final String UPDATE_BOOKINGS_QUEUE = "update-bookings";

	@Bean
	Queue newBookingsQueue() {
		return new Queue(QUEUE_NAME, true);
	}

	@Bean
	Queue confirmBookingsQueue() {
		return new Queue(UPDATE_BOOKINGS_QUEUE, true);
	}

	// TODO: is all of this required?
	// @Bean
	// SimpleMessageListenerContainer container(final ConnectionFactory connectionFactory,
	// 		final MessageListenerAdapter listenerAdapter) {
	// 	SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	// 	container.setConnectionFactory(connectionFactory);
	// 	container.setQueueNames(QUEUE_NAME);
	// 	container.setMessageListener(listenerAdapter);
	// 	return container;
	// }

	// @Bean
	// MessageListenerAdapter listenerAdapter(final BookingsReceiver receiver) {
	// 	return new MessageListenerAdapter(receiver, "receiveMessage");
	// }
}
