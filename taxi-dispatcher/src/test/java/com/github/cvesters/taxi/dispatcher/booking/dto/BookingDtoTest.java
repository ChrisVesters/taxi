package com.github.cvesters.taxi.dispatcher.booking.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.github.cvesters.taxi.dispatcher.location.dto.LocationDto;

public class BookingDtoTest {

	private static final long ID = 1;
	private static final String STATUS = "OPEN";
	private static final LocationDto LOCATION = new LocationDto(0, 0);
	private static final long TAXI_ID = 3L;

	@Nested
	class Constructor {

		@Test
		void valid() {
			final BookingDto taxi = new BookingDto(ID, STATUS, LOCATION, LOCATION, TAXI_ID);

			assertThat(taxi.id()).isEqualTo(ID);
			assertThat(taxi.status()).isEqualTo(STATUS);
			assertThat(taxi.start()).isEqualTo(LOCATION);
			assertThat(taxi.destination()).isEqualTo(LOCATION);

		}

		@Test
		void nullStatus() {
			assertThatThrownBy(() -> new BookingDto(ID, null, LOCATION, LOCATION, TAXI_ID))
					.isInstanceOf(NullPointerException.class);
		}

		@Test
		void nullStart() {
			assertThatThrownBy(() -> new BookingDto(ID, STATUS, null, LOCATION, TAXI_ID))
					.isInstanceOf(NullPointerException.class);
		}

		@Test
		void nullLocation() {
			assertThatThrownBy(() -> new BookingDto(ID, STATUS, LOCATION, null, TAXI_ID))
					.isInstanceOf(NullPointerException.class);
		}
	}
}
