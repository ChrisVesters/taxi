package com.github.cvesters.taxi.agent.taxi;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TaxiMessenger {
	
	private static final String EXCHANGE_NAME = "fleet";
	private static final String QUEUE_BASE_NAME = "taxi";



	// This is a topic on which we both publish and subscribe.
	// Each taxi should create its own que with it's id in it.




}
