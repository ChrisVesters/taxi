package com.github.cvesters.taxi.dispatcher.booking.bdo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

import com.github.cvesters.taxi.dispatcher.location.bdo.Location;

class BookingTest {

	private static final long ID = 1L;
	private static final BookingStatus STATUS = BookingStatus.IN_PROGRESS;
	private static final Location START = new Location(0.0, 0.0);
	private static final Location DESTINATION = new Location(0.2, 1.3);
	private static final long TAXI_ID = 3L;

	@Nested
	class Constructor {

		@Test
		void valid() {
			final var booking = new Booking(ID, STATUS, START, DESTINATION, TAXI_ID);

			assertThat(booking.getId()).isEqualTo(ID);
			assertThat(booking.getStatus()).isEqualTo(STATUS);
			assertThat(booking.getStart()).isEqualTo(START);
			assertThat(booking.getDestination()).isEqualTo(DESTINATION);
			assertThat(booking.getTaxiId()).isEqualTo(TAXI_ID);
		}

		@Test
		void nullStatus() {
			assertThatThrownBy(() -> new Booking(ID, null, START, DESTINATION, TAXI_ID))
					.isInstanceOf(NullPointerException.class);
		}

		@Test
		void nullStart() {
			assertThatThrownBy(() -> new Booking(ID, STATUS, null, DESTINATION, TAXI_ID))
					.isInstanceOf(NullPointerException.class);
		}

		@Test
		void nullDestination() {
			assertThatThrownBy(() -> new Booking(ID, STATUS, START, null, TAXI_ID))
					.isInstanceOf(NullPointerException.class);
		}

		@ParameterizedTest
		@EnumSource(value = BookingStatus.class, names = { "OPEN", "CANCELLED" })
		void nullTaxiIdWhenAllowed(final BookingStatus status) {
			final var booking = new Booking(ID, status, START, DESTINATION, null);

			assertThat(booking.getId()).isEqualTo(ID);
			assertThat(booking.getStatus()).isEqualTo(status);
			assertThat(booking.getStart()).isEqualTo(START);
			assertThat(booking.getDestination()).isEqualTo(DESTINATION);
			assertThat(booking.getTaxiId()).isNull();
		}

		@ParameterizedTest
		@EnumSource(value = BookingStatus.class, names = { "OPEN", "CANCELLED" }, mode = Mode.EXCLUDE)
		void nullTaxiIdWhenNotAllowed(final BookingStatus status) {
			assertThatThrownBy(() -> new Booking(ID, status, START, DESTINATION, null))
					.isInstanceOf(NullPointerException.class);
		}

		@Test
		void nonNullTaxiIdWhenOpen() {
			assertThatThrownBy(() -> new Booking(ID, BookingStatus.OPEN, START, DESTINATION, TAXI_ID))
					.isInstanceOf(IllegalArgumentException.class);
		}

		@Test
		void nonNullTaxiIdWhenCancelled() {
			final var booking = new Booking(ID, BookingStatus.CANCELLED, START, DESTINATION, TAXI_ID);

			assertThat(booking.getId()).isEqualTo(ID);
			assertThat(booking.getStatus()).isEqualTo(BookingStatus.CANCELLED);
			assertThat(booking.getStart()).isEqualTo(START);
			assertThat(booking.getDestination()).isEqualTo(DESTINATION);
			assertThat(booking.getTaxiId()).isEqualTo(TAXI_ID);
		}

		@Test
		void startAndDestinationAreTheSame() {
			assertThatThrownBy(() -> new Booking(ID, STATUS, START, START, TAXI_ID))
					.isInstanceOf(IllegalArgumentException.class);
		}
	}
}