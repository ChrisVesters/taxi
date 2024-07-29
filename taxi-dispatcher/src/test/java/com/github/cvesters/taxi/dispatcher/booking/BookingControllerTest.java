package com.github.cvesters.taxi.dispatcher.booking;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

	private static final String ENDPOINT = "/api/bookings";

	@MockBean
	private BookingService bookingService;

	@Autowired
	private MockMvc mockMvc;

	@Nested
	class GetAll {

		@Test
		void success() throws Exception {
			final List<TestBooking> bookings = List.of(TestBooking.OPEN, TestBooking.IN_PROGRESS);

			when(bookingService.getAll())
					.thenReturn(bookings.stream()
							.map(TestBooking::createBdo)
							.toList());

			final RequestBuilder request = get(ENDPOINT);

			mockMvc.perform(request)
					.andExpect(status().isOk())
					.andExpect(content().json(bookings.stream()
							.map(TestBooking::createJson)
							.collect(Collectors.joining(",", "[", "]"))));
		}

		@Test
		void empty() throws Exception {
			when(bookingService.getAll()).thenReturn(Collections.emptyList());

			final RequestBuilder request = get(ENDPOINT);

			mockMvc.perform(request)
					.andExpect(status().isOk())
					.andExpect(content().json("[]"));
		}
	}
}
