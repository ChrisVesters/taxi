package com.github.cvesters.taxi.agent.bookings;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cvesters.taxi.agent.State;

@Component
public class BookingsReceiver {

  private static final Duration WAIT_TIME = Duration.ofMinutes(5);

  private State state;
  private RabbitTemplate rabbitTemplate;
  private ObjectMapper objectMapper;

  public BookingsReceiver(final State state, final ObjectMapper objectMapper, final RabbitTemplate rabbitTemplate) {
    this.objectMapper = objectMapper;
    this.rabbitTemplate = rabbitTemplate;
  }

  public Optional<Booking> getBooking() {
    final Message message = rabbitTemplate.receive(BookingsMessenger.QUEUE_NAME, WAIT_TIME.toMillis());

    return Optional.ofNullable(message)
        .map(m -> {
          try {
            return objectMapper.readValue(m.getBody(), Booking.class);
          } catch (final IOException e) {
            e.printStackTrace();
            return null;
          }
        });
  }

  public void updateBooking(final Booking booking) {
		try {
			final String message = objectMapper.writeValueAsString(booking);
			rabbitTemplate.convertAndSend(BookingsMessenger.UPDATE_BOOKINGS_QUEUE, message);
		} catch (final JsonProcessingException e) {
			// Ingore message.
			e.printStackTrace();
		}
  }
}