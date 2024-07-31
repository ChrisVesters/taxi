package com.github.cvesters.taxi.dispatcher.booking;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingMessagingConfig {

	public static final String BOOKINGS_EXCHANGE = "bookings";
	public static final String NEW_BOOKINGS_QUEUE = "new-bookings";
	public static final String UPDATE_BOOKINGS_QUEUE = "update-bookings";

	private final AmqpAdmin amqpAdmin;

	public BookingMessagingConfig(final AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
	}

	@Bean
	DirectExchange bookingsExchange() {
		return ExchangeBuilder.directExchange(BOOKINGS_EXCHANGE).build();
	}

	@Bean
	Queue newBookingsQueue() {
		return QueueBuilder.durable(NEW_BOOKINGS_QUEUE).build();
	}

	@Bean
	Binding newBookingsBinding() {
		return BindingBuilder
				.bind(newBookingsQueue())
				.to(bookingsExchange())
				.withQueueName();
	}

	@Bean
	Queue updateBookingsQueue() {
		return QueueBuilder.durable(UPDATE_BOOKINGS_QUEUE).build();
	}

	@Bean
	Binding updateBookingsBinding() {
		return BindingBuilder
				.bind(updateBookingsQueue())
				.to(bookingsExchange())
				.withQueueName();
	}
}
