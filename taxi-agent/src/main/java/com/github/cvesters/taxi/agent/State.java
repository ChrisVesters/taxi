package com.github.cvesters.taxi.agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.cvesters.taxi.agent.bookings.Booking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class State {

	private final long id;
	private Booking currentBooking;

	public State(@Value("${taxi.agent.id}") final long id) {
		this.id = id;
	}

	public boolean isBooked() {
		return currentBooking != null;
	}
}
