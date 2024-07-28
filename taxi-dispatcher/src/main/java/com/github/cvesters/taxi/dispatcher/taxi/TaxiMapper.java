package com.github.cvesters.taxi.dispatcher.taxi;

import java.util.List;

import com.github.cvesters.taxi.dispatcher.taxi.bdo.Location;
import com.github.cvesters.taxi.dispatcher.taxi.bdo.Taxi;
import com.github.cvesters.taxi.dispatcher.taxi.bdo.TaxiStatus;
import com.github.cvesters.taxi.dispatcher.taxi.dao.LocationDao;
import com.github.cvesters.taxi.dispatcher.taxi.dao.TaxiDao;
import com.github.cvesters.taxi.dispatcher.taxi.dto.LocationDto;
import com.github.cvesters.taxi.dispatcher.taxi.dto.TaxiDto;

public final class TaxiMapper {

	private TaxiMapper() {
	}

	public static List<Taxi> fromDao(final List<TaxiDao> daos) {
		return daos.stream().map(TaxiMapper::fromDao).toList();
	}

	public static Taxi fromDao(final TaxiDao dao) {
		final long id = dao.getId();
		final TaxiStatus status = TaxiStatusMapper.fromDao(dao.getStatus());
		final Location location = locationFromDao(dao.getLocation());

		return new Taxi(id, status, location);
	}

	public static List<TaxiDto> toDto(final List<Taxi> taxis) {
		return taxis.stream().map(TaxiMapper::toDto).toList();
	}

	public static TaxiDto toDto(final Taxi taxi) {
		final long id = taxi.getId();
		final String status = TaxiStatusMapper.toDto(taxi.getStatus());
		final LocationDto location = locationToDto(taxi.getLocation());

		return new TaxiDto(id, status, location);
	}

	private static Location locationFromDao(final LocationDao dao) {
		return new Location(dao.getLatitude(), dao.getLongitude());
	}

	private static LocationDto locationToDto(final Location location) {
		return new LocationDto(location.latitude(), location.longitude());
	}
}
