package com.github.cvesters.taxi.dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TaxiDispatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiDispatcherApplication.class, args);
	}
}
