package com.github.cvesters.taxi.dispatcher.booking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.cvesters.taxi.dispatcher.booking.bdo.Booking;

@Service
public class BookingService {

	private final BookingRepository repository;

	public BookingService(final BookingRepository repository) {
		this.repository = repository;
	}

	public List<Booking> getAll() {
		return BookingMapper.fromDao(repository.findAll());
	}

}