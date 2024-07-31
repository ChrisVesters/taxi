package com.github.cvesters.taxi.dispatcher.booking.bdo;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.Validate;

import com.github.cvesters.taxi.dispatcher.location.bdo.Location;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Booking {

	private Long id;
	private BookingStatus status;
	private Location start;
	private Location destination;
	private Long taxiId;

	public Booking(final Location start, final Location destination) {
		this(null, BookingStatus.OPEN, start, destination, null);
	}

	public Booking(final Long id, final BookingStatus status, final Location start, final Location destination,
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

	public void update(final BookingStatus status) {
		update(status, null);
	}

	public void update(final BookingStatus status, final Long taxiId) {
		Objects.requireNonNull(status);
		Validate.isTrue(status != BookingStatus.OPEN);
		Validate.isTrue(this.status != BookingStatus.COMPLETED && this.status != BookingStatus.CANCELLED);
		Validate.isTrue(this.status != status);

		if ((status != BookingStatus.CANCELLED) && this.taxiId == null) {
			Objects.requireNonNull(taxiId);
		}

		if (this.status == BookingStatus.IN_PROGRESS) {
			Validate.isTrue(status == BookingStatus.COMPLETED || status == BookingStatus.CANCELLED);
		}

		this.status = status;
		this.taxiId = Optional.ofNullable(taxiId).orElse(this.taxiId);
	}

	private static boolean requiresTaxiId(final BookingStatus status) {
		return status != BookingStatus.OPEN && status != BookingStatus.CANCELLED;
	}
}
