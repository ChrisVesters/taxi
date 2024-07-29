package com.github.cvesters.taxi.dispatcher.location;

import com.github.cvesters.taxi.dispatcher.location.bdo.Location;
import com.github.cvesters.taxi.dispatcher.location.dao.LocationDao;
import com.github.cvesters.taxi.dispatcher.location.dto.LocationDto;

public final class LocationMapper {

	private LocationMapper() {
	}

	public static Location fromDao(final LocationDao dao) {
		return new Location(dao.getLatitude(), dao.getLongitude());
	}

	public static LocationDao toDao(final Location location) {
		return new LocationDao(location.latitude(), location.longitude());
	}

	public static LocationDto toDto(final Location location) {
		return new LocationDto(location.latitude(), location.longitude());
	}
}
