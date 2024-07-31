package com.github.cvesters.taxi.agent.taxi;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaxiQueueConfig {

	public static final String FLEET_EXCHANGE = "fleet";
	public static final String TAXI_BASE_QUEUE = "taxi-";

	private final int id;

	public TaxiQueueConfig(@Value("${taxi.agent.id}") final int id) {
		this.id = id;
	}

	public String getQueueName() {
		return TAXI_BASE_QUEUE + id;
	}

	@Bean
	Queue taxiQueue() {
		return QueueBuilder
				.durable(getQueueName())
				.build();
	}

	@Bean
	FanoutExchange fleetExchange() {
		return ExchangeBuilder.fanoutExchange(FLEET_EXCHANGE).build();
	}

	@Bean
	Binding taxiBinding() {
		return BindingBuilder
				.bind(taxiQueue())
				.to(fleetExchange());
	}
}
