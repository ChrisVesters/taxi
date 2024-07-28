package com.github.cvesters.taxi.dispatcher.taxi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.cvesters.taxi.dispatcher.taxi.bdo.Taxi;
import com.github.cvesters.taxi.dispatcher.taxi.dao.TaxiDao;

@ExtendWith(MockitoExtension.class)
class TaxiServiceTest {

	@Mock
	private TaxiRepository taxiRepository;

	private TaxiService service;

	@BeforeEach
	void setup() {
		service = new TaxiService(taxiRepository);
	}

	@Nested
	class GetAll {

		@Test
		void none() {
			when(taxiRepository.findAll()).thenReturn(Collections.emptyList());

			final List<Taxi> taxis = service.getAll();

			assertThat(taxis).isEmpty();
		}

		@Test
		void single() {
			final List<TestTaxi> testTaxis = List.of(TestTaxi.VICKI);
			final List<TaxiDao> foundTaxis = testTaxis.stream().map(TestTaxi::createDao).toList();
			when(taxiRepository.findAll()).thenReturn(foundTaxis);

			final List<Taxi> taxis = service.getAll();

			final List<Taxi> expectedTaxis = testTaxis.stream().map(TestTaxi::createBdo).toList();
			assertThat(taxis)
					.hasSize(testTaxis.size())
					.containsAll(expectedTaxis);
		}

		@Test
		void multiple() {
			final List<TestTaxi> testTaxis = List.of(TestTaxi.DWAIN, TestTaxi.VICKI);
			final List<TaxiDao> foundTaxis = testTaxis.stream().map(TestTaxi::createDao).toList();
			when(taxiRepository.findAll()).thenReturn(foundTaxis);

			final List<Taxi> taxis = service.getAll();

			final List<Taxi> expectedTaxis = testTaxis.stream().map(TestTaxi::createBdo).toList();
			assertThat(taxis)
					.hasSize(testTaxis.size())
					.containsAll(expectedTaxis);
		}
	}
}
