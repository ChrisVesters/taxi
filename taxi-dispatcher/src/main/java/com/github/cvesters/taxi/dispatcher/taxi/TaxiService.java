package com.github.cvesters.taxi.dispatcher.taxi;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.cvesters.taxi.dispatcher.taxi.bdo.Taxi;
import com.github.cvesters.taxi.dispatcher.taxi.dao.TaxiDao;

@Service
public class TaxiService {

	private final TaxiRepository repository;

	public TaxiService(final TaxiRepository repository) {
		this.repository = repository;
	}

	public List<Taxi> getAll() {
		return TaxiMapper.fromDao(repository.findAll());
	}

	public Taxi get(final long id) {
		final TaxiDao found = repository.findById(id).orElseThrow(IllegalArgumentException::new);
		return TaxiMapper.fromDao(found);
	}

	public void update(final Taxi taxi) {
		final TaxiDao found = repository.findById(taxi.getId()).orElseThrow(IllegalArgumentException::new);
		final TaxiDao updated = TaxiMapper.updateDao(found, taxi);
		repository.save(updated);
	}
}
