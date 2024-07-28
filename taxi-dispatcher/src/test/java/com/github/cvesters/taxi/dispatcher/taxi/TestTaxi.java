package com.github.cvesters.taxi.dispatcher.taxi;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.cvesters.taxi.dispatcher.location.bdo.Location;
import com.github.cvesters.taxi.dispatcher.location.dao.LocationDao;
import com.github.cvesters.taxi.dispatcher.location.dto.LocationDto;
import com.github.cvesters.taxi.dispatcher.taxi.bdo.Taxi;
import com.github.cvesters.taxi.dispatcher.taxi.bdo.TaxiStatus;
import com.github.cvesters.taxi.dispatcher.taxi.dao.TaxiDao;
import com.github.cvesters.taxi.dispatcher.taxi.dto.TaxiDto;

public record TestTaxi(long id, TaxiStatus status, Location location) {

	public static final TestTaxi VICKI = new TestTaxi(1, TaxiStatus.AVAILABLE, new Location(0.0, 0.0));
	public static final TestTaxi DWAIN = new TestTaxi(2, TaxiStatus.BOOKED, new Location(0.0, 0.0));

	public TaxiDao createDao() {
		final TaxiDao dao = mock(TaxiDao.class);
		when(dao.getId()).thenReturn(id);
		when(dao.getStatus()).thenReturn(status.ordinal());

		final LocationDao locationDao = mock(LocationDao.class);
		when(locationDao.getLatitude()).thenReturn(location.latitude());
		when(locationDao.getLongitude()).thenReturn(location.longitude());
		when(dao.getLocation()).thenReturn(locationDao);

		return dao;
	}

	public Taxi createBdo() {
		return new Taxi(id, status, location);
	}

	public TaxiDto createDto() {
		final LocationDto locationDto = new LocationDto(location.latitude(), location.longitude());
		return new TaxiDto(id, status.name(), locationDto);
	}

	public String createJson() {
		final var mapper = new JsonMapper();
		final var root = mapper.createObjectNode();
		root.put("id", id);
		root.put("status", status.name());
		final var locationObject = root.putObject("location");
		locationObject.put("latitude", location.latitude());
		locationObject.put("longitude", location.longitude());

		return root.toString();
	}
}
