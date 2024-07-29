package com.github.cvesters.taxi.dispatcher.booking;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.cvesters.taxi.dispatcher.booking.bdo.Booking;
import com.github.cvesters.taxi.dispatcher.booking.bdo.BookingStatus;
import com.github.cvesters.taxi.dispatcher.booking.dao.BookingDao;
import com.github.cvesters.taxi.dispatcher.booking.dto.BookingDto;
import com.github.cvesters.taxi.dispatcher.location.bdo.Location;
import com.github.cvesters.taxi.dispatcher.location.dao.LocationDao;
import com.github.cvesters.taxi.dispatcher.location.dto.LocationDto;

public record TestBooking(long id, BookingStatus status, Location start, Location destination, Long taxiId) {

	public static final TestBooking OPEN = new TestBooking(1, BookingStatus.OPEN, new Location(0.0, 0.0),
			new Location(1.0, 1.0), null);
	public static final TestBooking ASSIGNED = new TestBooking(1, BookingStatus.ASSIGNED, new Location(0.0, 0.0),
			new Location(1.0, 1.0), 1L);
	public static final TestBooking IN_PROGRESS = new TestBooking(1, BookingStatus.IN_PROGRESS, new Location(0.0, 0.0),
			new Location(1.0, 1.0), 1L);
	public static final TestBooking COMPLETED = new TestBooking(1, BookingStatus.COMPLETED, new Location(0.0, 0.0),
			new Location(1.0, 1.0), 2L);
	public static final TestBooking CANCELLED = new TestBooking(1, BookingStatus.CANCELLED, new Location(0.0, 0.0),
			new Location(1.0, 1.0), null);

	public BookingDao createDao() {
		final BookingDao dao = mock(BookingDao.class);
		when(dao.getId()).thenReturn(id);
		when(dao.getStatus()).thenReturn(status.ordinal());

		final LocationDao sourceDao = mock(LocationDao.class);
		when(sourceDao.getLatitude()).thenReturn(start.latitude());
		when(sourceDao.getLongitude()).thenReturn(start.longitude());
		when(dao.getStart()).thenReturn(sourceDao);

		final LocationDao destinationDao = mock(LocationDao.class);
		when(destinationDao.getLatitude()).thenReturn(destination.latitude());
		when(destinationDao.getLongitude()).thenReturn(destination.longitude());
		when(dao.getDestination()).thenReturn(destinationDao);

		when(dao.getTaxiId()).thenReturn(taxiId);
		return dao;
	}

	public Booking createBdo() {
		return new Booking(id, status, start, destination, taxiId);
	}

	public BookingDto createDto() {
		final LocationDto startDto = new LocationDto(start.latitude(), start.longitude());
		final LocationDto destinationDto = new LocationDto(destination.latitude(), destination.longitude());
		return new BookingDto(id, status.name(), startDto, destinationDto, taxiId);
	}

	public String createJson() {
		final var mapper = new JsonMapper();
		final var root = mapper.createObjectNode();
		root.put("id", id);
		root.put("status", status.name());

		final var startObject = root.putObject("start");
		startObject.put("latitude", start.latitude());
		startObject.put("longitude", start.longitude());

		final var destinationObject = root.putObject("destination");
		destinationObject.put("latitude", destination.latitude());
		destinationObject.put("longitude", destination.longitude());

		root.put("taxiId", taxiId);
		return root.toString();
	}
}
