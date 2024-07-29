package com.github.cvesters.taxi.dispatcher.booking.dto;

import java.util.Objects;

import com.github.cvesters.taxi.dispatcher.location.dto.LocationDto;

public record BookingDto(long id, String status, LocationDto start, LocationDto destination, Long taxiId) {

	public BookingDto {
		Objects.requireNonNull(status);
		Objects.requireNonNull(start);
		Objects.requireNonNull(destination);
	}
}
