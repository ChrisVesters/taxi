package com.github.cvesters.taxi.dispatcher.taxi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.cvesters.taxi.dispatcher.taxi.bdo.TaxiStatus;

class TaxiStatusMapperTest {

	@SuppressWarnings("unused")
	private static Stream<Arguments> statusMappings() {
		return Stream.of(
				Arguments.of(TaxiStatus.AVAILABLE, 0, "AVAILABLE"),
				Arguments.of(TaxiStatus.BOOKED, 1, "BOOKED"));
	}

	@Nested
	class FromDao {

		@ParameterizedTest
		@MethodSource("com.github.cvesters.taxi.dispatcher.taxi.TaxiStatusMapperTest#statusMappings")
		void valid(final TaxiStatus status, final int value) {
			assertThat(TaxiStatusMapper.fromDao(value)).isEqualTo(status);
		}

		@ParameterizedTest
		@ValueSource(ints = { -1, 2 })
		void invalid(final int value) {
			assertThatThrownBy(() -> TaxiStatusMapper.fromDao(value))
					.isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Nested
	class ToDto {

		@ParameterizedTest
		@MethodSource("com.github.cvesters.taxi.dispatcher.taxi.TaxiStatusMapperTest#statusMappings")
		void valid(final TaxiStatus status, final int value, final String name) {
			assertThat(TaxiStatusMapper.toDto(status)).isEqualTo(name);
		}
	}
}
