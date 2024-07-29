package com.github.cvesters.taxi.dispatcher.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.cvesters.taxi.dispatcher.booking.bdo.Booking;
import com.github.cvesters.taxi.dispatcher.booking.dao.BookingDao;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

	@Mock
	private BookingRepository bookingRepository;

	private BookingService service;

	@BeforeEach
	void setup() {
		service = new BookingService(bookingRepository);
	}

	@Nested
	class GetAll {

		@Test
		void none() {
			when(bookingRepository.findAll()).thenReturn(Collections.emptyList());

			final List<Booking> bookings = service.getAll();

			assertThat(bookings).isEmpty();
		}

		@Test
		void single() {
			final List<TestBooking> testBookings = List.of(TestBooking.OPEN);
			final List<BookingDao> foundTaxis = testBookings.stream().map(TestBooking::createDao).toList();
			when(bookingRepository.findAll()).thenReturn(foundTaxis);

			final List<Booking> bookings = service.getAll();

			final List<Booking> expectedBookings = testBookings.stream().map(TestBooking::createBdo).toList();
			assertThat(bookings)
					.hasSize(testBookings.size())
					.containsAll(expectedBookings);
		}

		@Test
		void multiple() {
			final List<TestBooking> testBookings = List.of(TestBooking.ASSIGNED, TestBooking.IN_PROGRESS);
			final List<BookingDao> foundBookings = testBookings.stream().map(TestBooking::createDao).toList();
			when(bookingRepository.findAll()).thenReturn(foundBookings);

			final List<Booking> bookings = service.getAll();

			final List<Booking> expectedBookings = testBookings.stream().map(TestBooking::createBdo).toList();
			assertThat(bookings)
					.hasSize(testBookings.size())
					.containsAll(expectedBookings);
		}
	}
}
