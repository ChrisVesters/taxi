package com.github.cvesters.taxi.dispatcher.booking;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cvesters.taxi.dispatcher.booking.dto.BookingDto;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(final BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@GetMapping
	public List<BookingDto> getAll() {
		return BookingMapper.toDto(bookingService.getAll());
	}
}
