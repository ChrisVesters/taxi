package com.github.cvesters.taxi.agent.taxi;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaxiQueueConfig {

	public static final String FLEET_EXCHANGE = "fleet";
	public static final String TAXI_QUEUE = "taxi";

	@Bean
	FanoutExchange fleetExchange() {
		return ExchangeBuilder.fanoutExchange(FLEET_EXCHANGE).build();
	}

	@Bean
	Queue taxiMessageQueue() {
		return QueueBuilder.durable(TAXI_QUEUE).build();
	}

	@Bean
	Binding binding() {
		return BindingBuilder
				.bind(taxiMessageQueue())
				.to(fleetExchange());
	}
}
