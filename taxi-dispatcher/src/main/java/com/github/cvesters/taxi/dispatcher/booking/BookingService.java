package com.github.cvesters.taxi.dispatcher.booking;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import com.github.cvesters.taxi.dispatcher.booking.bdo.Booking;
import com.github.cvesters.taxi.dispatcher.booking.dao.BookingDao;
import com.github.cvesters.taxi.dispatcher.booking.dto.BookingDto;

@Service
public class BookingService {

	private final BookingRepository repository;
	private final BookingQueueSender bookingSender;

	public BookingService(final BookingRepository repository, final BookingQueueSender bookingSender) {
		this.repository = repository;
		this.bookingSender = bookingSender;
	}

	public List<Booking> getAll() {
		return BookingMapper.fromDao(repository.findAll());
	}

	public Booking create(final Booking booking) {
		Objects.requireNonNull(booking);
		Validate.isTrue(booking.getId() == null);

		BookingDao dao = BookingMapper.toDao(booking);
		dao = repository.save(dao);

		final Booking created = BookingMapper.fromDao(dao);
		final BookingDto dto = BookingMapper.toDto(created);
		bookingSender.sendBooking(dto);

		return created;
	}
}