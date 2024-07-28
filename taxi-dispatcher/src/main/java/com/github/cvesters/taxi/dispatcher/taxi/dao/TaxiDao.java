package com.github.cvesters.taxi.dispatcher.taxi.dao;

import com.github.cvesters.taxi.dispatcher.location.dao.LocationDao;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "taxis")
public class TaxiDao {

	@Id
	@GeneratedValue
	@Setter(AccessLevel.PRIVATE)
	private long id;

	@Column
	private int status;

	@Embedded
	private LocationDao location;
}
