package com.github.cvesters.taxi.dispatcher.taxi.dto;

import java.util.Objects;

import com.github.cvesters.taxi.dispatcher.location.dto.LocationDto;

public record TaxiDto(long id, String status, LocationDto location) {

	public TaxiDto {
		Objects.requireNonNull(status);
		Objects.requireNonNull(location);
	}

}
