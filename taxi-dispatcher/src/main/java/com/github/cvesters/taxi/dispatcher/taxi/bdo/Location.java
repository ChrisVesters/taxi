package com.github.cvesters.taxi.dispatcher.taxi.bdo;

import org.apache.commons.lang3.Validate;

public record Location(double latitude, double longitude) {

	private static final double MAX_LATITUDE = 90.0;
	private static final double MAX_LONGITUDE = 180.0;

	public Location {
		Validate.inclusiveBetween(-MAX_LATITUDE, MAX_LATITUDE, latitude);
		Validate.inclusiveBetween(-MAX_LONGITUDE, MAX_LONGITUDE, longitude);
	}
}
