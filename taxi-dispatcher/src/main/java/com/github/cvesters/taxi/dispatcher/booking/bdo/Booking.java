package com.github.cvesters.taxi.dispatcher.booking.bdo;

import java.util.Objects;

import org.apache.commons.lang3.Validate;

import com.github.cvesters.taxi.dispatcher.location.bdo.Location;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Booking {

	private long id;
	private BookingStatus status;
	private Location start;
	private Location destination;
	private Long taxiId;

	public Booking(final long id, final BookingStatus status, final Location start, final Location destination,
			final Long taxiId) {
		Objects.requireNonNull(status);
		Objects.requireNonNull(start);
		Objects.requireNonNull(destination);
		Validate.isTrue(!start.equals(destination));

		if (status == BookingStatus.OPEN) {
			Validate.isTrue(taxiId == null);
		}
		if (requiresTaxiId(status)) {
			Objects.requireNonNull(taxiId);
		}

		this.id = id;
		this.status = status;
		this.start = start;
		this.destination = destination;
		this.taxiId = taxiId;
	}

	private static boolean requiresTaxiId(final BookingStatus status) {
		return status != BookingStatus.OPEN && status != BookingStatus.CANCELLED;
	}
}
