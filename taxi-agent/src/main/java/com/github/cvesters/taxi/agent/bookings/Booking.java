package com.github.cvesters.taxi.agent.bookings;

import com.github.cvesters.taxi.agent.location.Location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Booking {

	private long id;
	private String status;
	private Location start;
	private Location destination;
	private Long taxiId;

	public void accept(final long taxiId) {
		this.status = "IN_PROGRESS";
		this.taxiId = taxiId;
	}

	public void complete() {
		this.status = "COMPLETED";
	}
}