package com.github.cvesters.taxi.dispatcher.taxi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.github.cvesters.taxi.dispatcher.location.dao.LocationDao;
import com.github.cvesters.taxi.dispatcher.taxi.bdo.Taxi;
import com.github.cvesters.taxi.dispatcher.taxi.dao.TaxiDao;
import com.github.cvesters.taxi.dispatcher.taxi.dto.TaxiDto;

class TaxiMapperTest {

	private static final long ID = 1L;

	@Nested
	class FromDao {

		@Test
		void single() {
			final TestTaxi testTaxi = TestTaxi.VICKI;
			final TaxiDao dao = testTaxi.createDao();

			final Taxi taxi = TaxiMapper.fromDao(dao);

			assertThat(taxi).isEqualTo(testTaxi.createBdo());
		}

		@Test
		void multiple() {
			final List<TestTaxi> testTaxi = List.of(TestTaxi.VICKI, TestTaxi.DWAIN);
			final List<TaxiDao> daos = testTaxi.stream().map(TestTaxi::createDao).toList();

			final List<Taxi> taxis = TaxiMapper.fromDao(daos);

			assertThat(taxis).hasSize(testTaxi.size())
					.containsAll(testTaxi.stream().map(TestTaxi::createBdo).toList());
		}

		@Test
		void invalidStatus() {
			final TaxiDao dao = mock(TaxiDao.class);
			when(dao.getId()).thenReturn(ID);
			when(dao.getStatus()).thenReturn(-1);

			final LocationDao locationDao = mock(LocationDao.class);
			when(locationDao.getLatitude()).thenReturn(0.0);
			when(locationDao.getLongitude()).thenReturn(0.0);
			when(dao.getLocation()).thenReturn(locationDao);

			assertThatThrownBy(() -> TaxiMapper.fromDao(dao))
					.isInstanceOf(IllegalArgumentException.class);
		}

		@Test
		void invalidLocation() {
			final TaxiDao dao = mock(TaxiDao.class);
			when(dao.getId()).thenReturn(ID);
			when(dao.getStatus()).thenReturn(0);

			final LocationDao locationDao = mock(LocationDao.class);
			when(locationDao.getLatitude()).thenReturn(106.0);
			when(locationDao.getLongitude()).thenReturn(0.0);
			when(dao.getLocation()).thenReturn(locationDao);

			assertThatThrownBy(() -> TaxiMapper.fromDao(dao))
					.isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Nested
	class ToDto {

		@Test
		void single() {
			final TestTaxi testTaxi = TestTaxi.VICKI;
			final Taxi taxi = testTaxi.createBdo();

			final TaxiDto dto = TaxiMapper.toDto(taxi);

			assertThat(dto).isEqualTo(testTaxi.createDto());
		}

		@Test
		void multiple() {
			final List<TestTaxi> testTaxi = List.of(TestTaxi.VICKI, TestTaxi.DWAIN);
			final List<Taxi> taxis = testTaxi.stream().map(TestTaxi::createBdo).toList();

			final List<TaxiDto> dtos = TaxiMapper.toDto(taxis);

			assertThat(dtos).hasSize(testTaxi.size())
					.containsAll(testTaxi.stream().map(TestTaxi::createDto).toList());
		}
	}
}
