package com.github.cvesters.taxi.dispatcher.booking;

import org.springframework.data.repository.ListCrudRepository;

import com.github.cvesters.taxi.dispatcher.booking.dao.BookingDao;

public interface BookingRepository extends ListCrudRepository<BookingDao, Long> {

}
