package com.github.cvesters.taxi.dispatcher.booking.dao;

import com.github.cvesters.taxi.dispatcher.location.dao.LocationDao;

import jakarta.persistence.AttributeOverride;
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
@Entity(name = "bookings")
public class BookingDao {

	@Id
	@GeneratedValue
	@Setter(AccessLevel.PRIVATE)
	private long id;

	@Column
	private int status;

	@Embedded
	@AttributeOverride(name = "latitude", column = @Column(name = "start_latitude"))
	@AttributeOverride(name = "longitude", column = @Column(name = "start_longitude"))
	private LocationDao start;

	@Embedded
	@AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude"))
	@AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude"))
	private LocationDao destination;

	@Column(name = "taxi_id")
	private Long taxiId;

	public BookingDao(final int status, final LocationDao start,
			final LocationDao destination, final Long taxiId) {
		this.status = status;
		this.start = start;
		this.destination = destination;
		this.taxiId = taxiId;
	}
}
