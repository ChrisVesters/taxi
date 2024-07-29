package com.github.cvesters.taxi.dispatcher.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
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

	@Mock
	private BookingQueueSender bookingSender;

	private BookingService service;

	@BeforeEach
	void setup() {
		service = new BookingService(bookingRepository, bookingSender);
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

	@Nested
	class Create {

		@Test
		void success() {
			final TestBooking requested = TestBooking.NEW;
			final Booking requestedBdo = requested.createBdo();

			final TestBooking created = TestBooking.OPEN;
			final BookingDao createdDao = created.createDao();
			final Booking createdBdo = created.createBdo();

			when(bookingRepository.save(any())).thenReturn(createdDao);

			final Booking result = service.create(requestedBdo);

			assertThat(result).isEqualTo(createdBdo);

			verify(bookingRepository).save(argThat(b -> {
				assertThat(b.getId()).isZero();
				assertThat(b.getStatus()).isEqualTo(requested.status().ordinal());
				assertThat(b.getStart().getLatitude()).isEqualTo(requested.start().latitude());
				assertThat(b.getStart().getLongitude()).isEqualTo(requested.start().longitude());
				assertThat(b.getDestination().getLatitude()).isEqualTo(requested.destination().latitude());
				assertThat(b.getDestination().getLongitude()).isEqualTo(requested.destination().longitude());
				assertThat(b.getTaxiId()).isEqualTo(requested.taxiId());
				return true;
			}));
		}

		@Test
		void nullBooking() {
			assertThatThrownBy(() -> service.create(null)).isInstanceOf(NullPointerException.class);
		}

		@Test
		void nonNullId() {
			final Booking booking = TestBooking.IN_PROGRESS.createBdo();
			assertThatThrownBy(() -> service.create(booking)).isInstanceOf(IllegalArgumentException.class);
		}
	}
}
