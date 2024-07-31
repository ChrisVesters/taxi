package com.github.cvesters.taxi.agent.bookings;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BookingQueueReceiver {

  private static final Duration WAIT_TIME = Duration.ofMinutes(5);

  private RabbitTemplate rabbitTemplate;
  private ObjectMapper objectMapper;

  public BookingQueueReceiver(final ObjectMapper objectMapper, final RabbitTemplate rabbitTemplate) {
    this.objectMapper = objectMapper;
    this.rabbitTemplate = rabbitTemplate;
  }

  public Optional<Booking> getBooking() {
    final Message message = rabbitTemplate.receive(BookingQueueConfig.QUEUE_NAME, WAIT_TIME.toMillis());

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
      rabbitTemplate.convertAndSend(BookingQueueConfig.UPDATE_BOOKINGS_QUEUE, message);
    } catch (final JsonProcessingException e) {
      // Ingore message.
      e.printStackTrace();
    }
  }
}