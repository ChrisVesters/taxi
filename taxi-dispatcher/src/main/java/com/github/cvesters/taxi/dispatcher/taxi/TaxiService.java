package com.github.cvesters.taxi.dispatcher.taxi;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.cvesters.taxi.dispatcher.taxi.bdo.Taxi;

@Service
public class TaxiService {

	private final TaxiRepository repository;

	public TaxiService(final TaxiRepository repository) {
		this.repository = repository;
	}

	public List<Taxi> getAll() {
		return TaxiMapper.fromDao(repository.findAll());
	}
}
