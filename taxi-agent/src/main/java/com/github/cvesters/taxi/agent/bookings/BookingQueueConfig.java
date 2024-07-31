package com.github.cvesters.taxi.agent.bookings;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingQueueConfig {

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
}
