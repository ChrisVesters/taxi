package com.github.cvesters.taxi.dispatcher.taxi;

import static org.mockito.Mockito.when;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaxiController.class)
class TaxiControllerTest {

	private static final String ENDPOINT = "/api/taxis";

	@MockBean
	private TaxiService taxiService;

	@Autowired
	private MockMvc mockMvc;

	@Nested
	class GetAll {

		@Test
		void success() throws Exception {
			final List<TestTaxi> taxis = List.of(TestTaxi.VICKI, TestTaxi.DWAIN);

			when(taxiService.getAll())
					.thenReturn(taxis.stream()
							.map(TestTaxi::createBdo)
							.toList());

			final RequestBuilder request = get(ENDPOINT);

			mockMvc.perform(request)
					.andExpect(status().isOk())
					.andExpect(content().json(taxis.stream()
							.map(TestTaxi::createJson)
							.collect(Collectors.joining(",", "[", "]"))));
		}

		@Test
		void empty() throws Exception {
			when(taxiService.getAll()).thenReturn(Collections.emptyList());

			final RequestBuilder request = get(ENDPOINT);

			mockMvc.perform(request)
					.andExpect(status().isOk())
					.andExpect(content().json("[]"));
		}
	}
}
