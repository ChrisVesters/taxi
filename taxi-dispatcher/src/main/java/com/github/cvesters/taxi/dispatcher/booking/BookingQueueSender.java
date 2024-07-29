package com.github.cvesters.taxi.dispatcher.booking;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cvesters.taxi.dispatcher.booking.dto.BookingDto;
import com.github.cvesters.taxi.dispatcher.location.dto.LocationDto;

@Component
public class BookingQueueSender {

	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;
	// TODO: add queue here as well?

	public BookingQueueSender(final RabbitTemplate rabbitTemplate, final ObjectMapper objectMapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
	}

	public void sendBooking(final BookingDto booking) {
		try {
			final String message = objectMapper.writeValueAsString(booking);
			rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
				System.out.println(correlationData);
				System.out.println(ack);
			});
			rabbitTemplate.convertAndSend(BookingMessagingConfig.BOOKINGS_EXCHANGE,
					BookingMessagingConfig.NEW_BOOKINGS_QUEUE, message);
		} catch (final JsonProcessingException e) {
			// Ingore message.
			e.printStackTrace();
		}
	}
}
