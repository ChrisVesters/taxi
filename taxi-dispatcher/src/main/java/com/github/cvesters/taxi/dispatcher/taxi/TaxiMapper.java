package com.github.cvesters.taxi.dispatcher.taxi;

import java.util.List;

import com.github.cvesters.taxi.dispatcher.location.LocationMapper;
import com.github.cvesters.taxi.dispatcher.location.bdo.Location;
import com.github.cvesters.taxi.dispatcher.location.dto.LocationDto;
import com.github.cvesters.taxi.dispatcher.taxi.bdo.Taxi;
import com.github.cvesters.taxi.dispatcher.taxi.bdo.TaxiStatus;
import com.github.cvesters.taxi.dispatcher.taxi.dao.TaxiDao;
import com.github.cvesters.taxi.dispatcher.taxi.dto.TaxiDto;

public final class TaxiMapper {

	private TaxiMapper() {
	}

	public static List<Taxi> fromDao(final List<TaxiDao> daos) {
		return daos.stream().map(TaxiMapper::fromDao).toList();
	}

	public static Taxi fromDao(final TaxiDao dao) {
		final long id = dao.getId();
		final TaxiStatus status = TaxiStatusMapper.fromDao(dao.getStatus());
		final Location location = LocationMapper.fromDao(dao.getLocation());

		return new Taxi(id, status, location);
	}

	public static TaxiDao updateDao(final TaxiDao dao, final Taxi taxi) {
		dao.setStatus(TaxiStatusMapper.toDao(taxi.getStatus()));
		dao.setLocation(LocationMapper.toDao(taxi.getLocation()));
		return dao;
	}

	public static List<TaxiDto> toDto(final List<Taxi> taxis) {
		return taxis.stream().map(TaxiMapper::toDto).toList();
	}

	public static TaxiDto toDto(final Taxi taxi) {
		final long id = taxi.getId();
		final String status = TaxiStatusMapper.toDto(taxi.getStatus());
		final LocationDto location = LocationMapper.toDto(taxi.getLocation());

		return new TaxiDto(id, status, location);
	}
}
