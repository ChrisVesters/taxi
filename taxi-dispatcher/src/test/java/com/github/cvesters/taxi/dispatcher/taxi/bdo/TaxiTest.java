package com.github.cvesters.taxi.dispatcher.taxi.bdo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TaxiTest {

	private static final long ID = 1L;
	private static final TaxiStatus STATUS = TaxiStatus.AVAILABLE;
	private static final Location LOCATION = new Location(0.0, 0.0);

	@Nested
	class Constructor {

		@Test
		void valid() {
			final Taxi taxi = new Taxi(ID, STATUS, LOCATION);

			assertThat(taxi.getId()).isEqualTo(ID);
			assertThat(taxi.getStatus()).isEqualTo(STATUS);
			assertThat(taxi.getLocation()).isEqualTo(LOCATION);
		}

		@Test
		void nullStatus() {
			assertThatThrownBy(() -> new Taxi(ID, null, LOCATION))
					.isInstanceOf(NullPointerException.class);
		}

		@Test
		void nullLocation() {
			assertThatThrownBy(() -> new Taxi(ID, STATUS, null))
					.isInstanceOf(NullPointerException.class);
		}
	}
}
