package com.github.cvesters.taxi.dispatcher.taxi.dto;

import java.util.Objects;

public record TaxiDto(long id, String status, LocationDto location) {

	public TaxiDto {
		Objects.requireNonNull(status);
		Objects.requireNonNull(location);
	}

}
