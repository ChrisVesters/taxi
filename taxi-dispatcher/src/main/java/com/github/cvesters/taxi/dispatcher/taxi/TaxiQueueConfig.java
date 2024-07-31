package com.github.cvesters.taxi.dispatcher.taxi;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaxiQueueConfig {

	public static final String FLEET_EXCHANGE = "fleet";

	private final AmqpAdmin amqpAdmin;

	public TaxiQueueConfig(final AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
	}

	@Bean
	FanoutExchange fleetExchange() {
		return ExchangeBuilder.fanoutExchange(FLEET_EXCHANGE).build();
	}
}
