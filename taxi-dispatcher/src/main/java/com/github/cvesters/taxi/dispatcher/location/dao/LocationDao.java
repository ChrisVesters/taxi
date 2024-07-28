package com.github.cvesters.taxi.dispatcher.location.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class LocationDao {

	@Column(nullable = false)
	private double latitude;

	@Column(nullable = false)
	private double longitude;

	public LocationDao(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
