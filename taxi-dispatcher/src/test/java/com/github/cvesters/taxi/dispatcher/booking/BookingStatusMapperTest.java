package com.github.cvesters.taxi.dispatcher.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.cvesters.taxi.dispatcher.booking.bdo.BookingStatus;

class BookingStatusMapperTest {

	@SuppressWarnings("unused")
	private static Stream<Arguments> statusMappings() {
		return Stream.of(
				Arguments.of(BookingStatus.OPEN, 0, "OPEN"),
				Arguments.of(BookingStatus.ASSIGNED, 1, "ASSIGNED"),
				Arguments.of(BookingStatus.IN_PROGRESS, 2, "IN_PROGRESS"),
				Arguments.of(BookingStatus.COMPLETED, 3, "COMPLETED"),
				Arguments.of(BookingStatus.CANCELLED, 4, "CANCELLED"));
	}

	@Nested
	class FromDao {

		@ParameterizedTest
		@MethodSource("com.github.cvesters.taxi.dispatcher.booking.BookingStatusMapperTest#statusMappings")
		void valid(final BookingStatus status, final int value) {
			assertThat(BookingStatusMapper.fromDao(value)).isEqualTo(status);
		}

		@ParameterizedTest
		@ValueSource(ints = { -1, 5 })
		void invalid(final int value) {
			assertThatThrownBy(() -> BookingStatusMapper.fromDao(value))
					.isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Nested
	class ToDao {
		@ParameterizedTest
		@MethodSource("com.github.cvesters.taxi.dispatcher.booking.BookingStatusMapperTest#statusMappings")
		void valid(final BookingStatus status, final int value, final String name) {
			assertThat(BookingStatusMapper.toDao(status)).isEqualTo(value);
		}
	}

	@Nested
	class ToDto {

		@ParameterizedTest
		@MethodSource("com.github.cvesters.taxi.dispatcher.booking.BookingStatusMapperTest#statusMappings")
		void valid(final BookingStatus status, final int value, final String name) {
			assertThat(BookingStatusMapper.toDto(status)).isEqualTo(name);
		}
	}
}
