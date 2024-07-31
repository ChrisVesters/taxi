package com.github.cvesters.taxi.dispatcher.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.github.cvesters.taxi.dispatcher.booking.bdo.Booking;
import com.github.cvesters.taxi.dispatcher.booking.dao.BookingDao;
import com.github.cvesters.taxi.dispatcher.booking.dto.BookingDto;
import com.github.cvesters.taxi.dispatcher.location.dto.LocationDto;

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
	class UpdateDao {

		@Test
		void single() {
			final TestBooking testBooking = TestBooking.OPEN;
			final Booking booking = testBooking.createBdo();
			final BookingDao dao = mock(BookingDao.class);

			final BookingDao updated = BookingMapper.updateDao(dao, booking);

			assertThat(updated).isEqualTo(dao);
			verify(dao).setStart(argThat(location -> {
				assertThat(location.getLatitude()).isEqualTo(booking.getStart().latitude());
				assertThat(location.getLongitude()).isEqualTo(booking.getStart().longitude());
				return true;
			}));
			verify(dao).setDestination(argThat(location -> {
				assertThat(location.getLatitude()).isEqualTo(booking.getDestination().latitude());
				assertThat(location.getLongitude()).isEqualTo(booking.getDestination().longitude());
				return true;
			}));
			verify(dao).setStatus(booking.getStatus().ordinal());
			verify(dao).setTaxiId(booking.getTaxiId());
		}
	}

	@Nested
	class FromDto {

		@Test
		void single() {
			final TestBooking testBooking = TestBooking.OPEN;
			final BookingDto dto = testBooking.createDto();

			final Booking booking = BookingMapper.fromDto(dto);

			assertThat(booking).isEqualTo(testBooking.createBdo());
		}

		@Test
		void invalidObject() {
			final LocationDto location = new LocationDto(0.0, 0.0);
			final BookingDto dto = new BookingDto(1L, "INVALID", location, location, null);

			assertThatThrownBy(() -> BookingMapper.fromDto(dto))
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