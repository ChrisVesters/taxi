package com.github.cvesters.taxi.agent;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.cvesters.taxi.agent.bookings.Booking;
import com.github.cvesters.taxi.agent.bookings.BookingsReceiver;

import jakarta.annotation.PostConstruct;

@Component
public class Simulation {

	private static final Duration MIN_DURATION = Duration.ofMinutes(5);
	private static final Duration MAX_DURATION = MIN_DURATION.multipliedBy(12);

	private static final Random RNG = new Random();

	private final BookingsReceiver receiver;

	private final State state;
	private final boolean enabled;
	private final long speed;

	public Simulation(final State state, final BookingsReceiver receiver,
			@Value("${simulation.enabled:false}") final boolean enabled,
			@Value("${simulation.speed:1}") final long speed) {
		this.state = state;
		this.receiver = receiver;
		this.enabled = enabled;
		this.speed = speed;
	}

	@PostConstruct
	void run() throws InterruptedException {
		while (enabled) {
			final Booking booking = getBooking();

			booking.accept(state.getId());
			receiver.updateBooking(booking);
			state.setCurrentBooking(booking);

			final long duration = RNG.nextLong(MIN_DURATION.toMillis(), MAX_DURATION.toMillis());
			Thread.sleep(duration / speed);

			booking.complete();
			receiver.updateBooking(booking);
			state.setCurrentBooking(null);
		}
	}

	private Booking getBooking() {
		Optional<Booking> booking = Optional.empty();

		while (booking.isEmpty()) {
			booking = receiver.getBooking();
		}

		return booking.get();
	}
}
