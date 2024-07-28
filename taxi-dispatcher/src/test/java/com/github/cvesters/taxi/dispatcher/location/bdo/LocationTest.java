package com.github.cvesters.taxi.dispatcher.location.bdo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LocationTest {

	@Test
	void valid() {
		final Location location = new Location(0.0, 0.0);

		assertThat(location.latitude()).isEqualTo(0.0);
		assertThat(location.longitude()).isEqualTo(0.0);
	}

	@ParameterizedTest
	@ValueSource(doubles = { -90.1, 90.1 })
	void invalidLatitude(final double latitude) {
		assertThatThrownBy(() -> new Location(latitude, 0.0))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@ValueSource(doubles = { -180.1, 180.1 })
	void invalidLongitude(final double longitude) {
		assertThatThrownBy(() -> new Location(0.0, longitude))
				.isInstanceOf(IllegalArgumentException.class);
	}
}
