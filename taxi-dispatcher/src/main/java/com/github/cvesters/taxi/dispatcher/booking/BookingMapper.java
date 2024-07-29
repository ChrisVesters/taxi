package com.github.cvesters.taxi.dispatcher.booking;

import java.util.List;

import com.github.cvesters.taxi.dispatcher.booking.bdo.Booking;
import com.github.cvesters.taxi.dispatcher.booking.bdo.BookingStatus;
import com.github.cvesters.taxi.dispatcher.booking.dao.BookingDao;
import com.github.cvesters.taxi.dispatcher.booking.dto.BookingDto;
import com.github.cvesters.taxi.dispatcher.location.LocationMapper;
import com.github.cvesters.taxi.dispatcher.location.bdo.Location;
import com.github.cvesters.taxi.dispatcher.location.dao.LocationDao;
import com.github.cvesters.taxi.dispatcher.location.dto.LocationDto;

public final class BookingMapper {

	private BookingMapper() {
	}

	public static List<Booking> fromDao(final List<BookingDao> daos) {
		return daos.stream().map(BookingMapper::fromDao).toList();
	}

	public static Booking fromDao(final BookingDao dao) {
		final long id = dao.getId();
		final BookingStatus status = BookingStatusMapper.fromDao(dao.getStatus());
		final Location start = LocationMapper.fromDao(dao.getStart());
		final Location destination = LocationMapper.fromDao(dao.getDestination());
		final Long taxiId = dao.getTaxiId();

		return new Booking(id, status, start, destination, taxiId);
	}

	public static BookingDao toDao(final Booking booking) {
		final int status =  BookingStatusMapper.toDao(booking.getStatus());
		final LocationDao start = LocationMapper.toDao(booking.getStart());
		final LocationDao destination = LocationMapper.toDao(booking.getDestination());
		final Long taxiId = booking.getTaxiId();

		return new BookingDao(status, start, destination, taxiId);
	}

	public static List<BookingDto> toDto(final List<Booking> bookings) {
		return bookings.stream().map(BookingMapper::toDto).toList();
	}

	public static BookingDto toDto(final Booking booking) {
		final long id = booking.getId();
		final String status = BookingStatusMapper.toDto(booking.getStatus());
		final LocationDto start = LocationMapper.toDto(booking.getStart());
		final LocationDto destination = LocationMapper.toDto(booking.getDestination());
		final Long taxiId = booking.getTaxiId();

		return new BookingDto(id, status, start, destination, taxiId);
	}
}
