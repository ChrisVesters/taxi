package com.github.cvesters.taxi.dispatcher.taxi.bdo;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Taxi {

	private final long id;
	private TaxiStatus status;
	private Location location;

	public Taxi(final long id, final TaxiStatus status, final Location location) {
		Objects.requireNonNull(status);
		Objects.requireNonNull(location);

		this.id = id;
		this.status = status;
		this.location = location;
	}

}
