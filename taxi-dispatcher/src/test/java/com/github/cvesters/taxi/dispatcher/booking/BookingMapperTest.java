package com.github.cvesters.taxi.dispatcher.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.github.cvesters.taxi.dispatcher.booking.bdo.Booking;
import com.github.cvesters.taxi.dispatcher.booking.dao.BookingDao;
import com.github.cvesters.taxi.dispatcher.booking.dto.BookingDto;

public class BookingMapperTest {

	@Nested
	class FromDao {

		@Test
		void single() {
			final TestBooking testBooking = TestBooking.OPEN;
			final BookingDao dao = testBooking.createDao();

			final Booking booking = BookingMapper.fromDao(dao);

			assertThat(booking).isEqualTo(testBooking.createBdo());
		}

		@Test
		void multiple() {
			final List<TestBooking> testBooking = List.of(TestBooking.OPEN, TestBooking.COMPLETED);
			final List<BookingDao> daos = testBooking.stream().map(TestBooking::createDao).toList();

			final List<Booking> bookings = BookingMapper.fromDao(daos);

			assertThat(bookings).hasSize(testBooking.size())
					.containsAll(testBooking.stream().map(TestBooking::createBdo).toList());
		}

		@Test
		void invalidObject() {
			final TestBooking testBooking = TestBooking.OPEN;
			final BookingDao dao = testBooking.createDao();
			when(dao.getStatus()).thenReturn(-1);

			assertThatThrownBy(() -> BookingMapper.fromDao(dao))
					.isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Nested
	class ToDto {

		@Test
		void single() {
			final TestBooking testBooking = TestBooking.OPEN;
			final Booking booking = testBooking.createBdo();

			final BookingDto dto = BookingMapper.toDto(booking);

			assertThat(dto).isEqualTo(testBooking.createDto());
		}

		@Test
		void multiple() {
			final List<TestBooking> testBooking = List.of(TestBooking.IN_PROGRESS,
					TestBooking.CANCELLED);
			final List<Booking> bookings = testBooking.stream().map(TestBooking::createBdo).toList();

			final List<BookingDto> dtos = BookingMapper.toDto(bookings);

			assertThat(dtos).hasSize(testBooking.size())
					.containsAll(testBooking.stream().map(TestBooking::createDto).toList());
		}
	}
}