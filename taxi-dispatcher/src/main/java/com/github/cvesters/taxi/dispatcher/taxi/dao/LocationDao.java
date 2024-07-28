package com.github.cvesters.taxi.dispatcher.taxi.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class LocationDao {

	@Column(nullable = false)
	private double latitude;

	@Column(nullable = false)
	private double longitude;
}
