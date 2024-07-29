package com.github.cvesters.taxi.dispatcher;

import java.time.Duration;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.cvesters.taxi.dispatcher.booking.BookingService;
import com.github.cvesters.taxi.dispatcher.booking.bdo.Booking;
import com.github.cvesters.taxi.dispatcher.location.bdo.Location;

import jakarta.annotation.PostConstruct;

@Component
public class Simulation {

	private static final Duration INTERVAL = Duration.ofMinutes(5);

	private final BookingService bookingService;

	private final boolean enabled;
	private final long speed;

	public Simulation(final BookingService bookingService,
			@Value("${simulation.enabled:false}") final boolean enabled,
			@Value("${simulation.speed:1}") final long speed) {
		this.bookingService = bookingService;
		this.enabled = enabled;
		this.speed = speed;
	}

	@PostConstruct
	void run() throws InterruptedException {
		if (!enabled) {
			return;
		}

		final var simulation = new Thread(() -> {
			try {
				while (true) {
					final Location start = new Location(0.0, 0.0);
					final Location destination = new Location(0.1, 0.1);
					final Booking booking = new Booking(start, destination);
					bookingService.create(booking);

					Thread.sleep(INTERVAL.toMillis() / speed);
				}
			} catch (final InterruptedException e) {
				// Time to stop;
			}
		});

		simulation.start();
	}
}
