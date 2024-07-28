package com.github.cvesters.taxi.dispatcher.taxi.dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TaxiDtoTest {

	private static final long ID = 1;
	private static final String STATUS = "AVAILABLE";
	private static final LocationDto LOCATION = new LocationDto(0, 0);

	@Nested
	class Constructor {

		@Test
		void valid() {
			final TaxiDto taxi = new TaxiDto(ID, STATUS, LOCATION);

			assertThat(taxi.id()).isEqualTo(ID);
			assertThat(taxi.status()).isEqualTo(STATUS);
			assertThat(taxi.location()).isEqualTo(LOCATION);
		}

		@Test
		void nullStatus() {
			assertThatThrownBy(() -> new TaxiDto(ID, null, LOCATION))
					.isInstanceOf(NullPointerException.class);
		}

		@Test
		void nullLocation() {
			assertThatThrownBy(() -> new TaxiDto(ID, STATUS, null))
					.isInstanceOf(NullPointerException.class);
		}
	}
}
