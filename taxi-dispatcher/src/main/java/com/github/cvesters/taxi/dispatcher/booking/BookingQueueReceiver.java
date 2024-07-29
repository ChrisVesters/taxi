package com.github.cvesters.taxi.dispatcher.booking;

import java.io.IOError;
import java.io.IOException;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cvesters.taxi.dispatcher.booking.dto.BookingDto;

@Component
public class BookingQueueReceiver {

	private final ObjectMapper objectMapper;
	private final BookingService bookingService;

	public BookingQueueReceiver(final BookingService bookingService,
			final ObjectMapper objectMapper) {
		this.bookingService = bookingService;
		this.objectMapper = objectMapper;
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = BookingMessagingConfig.UPDATE_BOOKINGS_QUEUE), exchange = @Exchange(value = BookingMessagingConfig.BOOKINGS_EXCHANGE)))
	public void updateBooking(final Message message) {
		try {
			final BookingDto booking = objectMapper.readValue(message.getBody(), BookingDto.class);
			System.out.println("Received booking update: " + booking);
			// bookingService.updateBooking(booking);
		} catch (final IOException e) {
			// Ingore message.
			e.printStackTrace();
		}
	}

}
