package com.github.cvesters.taxi.dispatcher.booking;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import com.github.cvesters.taxi.dispatcher.booking.bdo.Booking;
import com.github.cvesters.taxi.dispatcher.booking.bdo.BookingStatus;
import com.github.cvesters.taxi.dispatcher.booking.dao.BookingDao;
import com.github.cvesters.taxi.dispatcher.booking.dto.BookingDto;
import com.github.cvesters.taxi.dispatcher.taxi.TaxiService;
import com.github.cvesters.taxi.dispatcher.taxi.bdo.Taxi;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;

	private final TaxiService taxiService;
	private final BookingQueueSender bookingSender;

	public BookingService(final BookingRepository bookingRepository,
			final TaxiService taxiService,
			final BookingQueueSender bookingSender) {
		this.bookingRepository = bookingRepository;
		this.taxiService = taxiService;
		this.bookingSender = bookingSender;
	}

	public List<Booking> getAll() {
		return BookingMapper.fromDao(bookingRepository.findAll());
	}

	public Booking create(final Booking booking) {
		Objects.requireNonNull(booking);
		Validate.isTrue(booking.getId() == null);

		BookingDao dao = BookingMapper.updateDao(new BookingDao(), booking);
		dao = bookingRepository.save(dao);

		final Booking created = BookingMapper.fromDao(dao);
		final BookingDto dto = BookingMapper.toDto(created);
		bookingSender.sendBooking(dto);

		return created;
	}

	public void update(final Booking booking) {
		Objects.requireNonNull(booking);

		final BookingDao bookingDao = bookingRepository.findById(booking.getId())
				.orElseThrow(IllegalArgumentException::new);
		final Taxi taxi = taxiService.get(booking.getTaxiId());

		final Booking currentBooking = BookingMapper.fromDao(bookingDao);
		currentBooking.update(booking.getStatus(), taxi.getId());

		if (booking.getStatus() == BookingStatus.COMPLETED || booking.getStatus() == BookingStatus.CANCELLED) {
			taxi.setAvailable();
		}
		if (booking.getStatus() == BookingStatus.ASSIGNED || booking.getStatus() == BookingStatus.IN_PROGRESS) {
			taxi.setBooked();
		}

		final BookingDao updatedDao = BookingMapper.updateDao(bookingDao, currentBooking);
		bookingRepository.save(updatedDao);
		taxiService.update(taxi);
	}
}