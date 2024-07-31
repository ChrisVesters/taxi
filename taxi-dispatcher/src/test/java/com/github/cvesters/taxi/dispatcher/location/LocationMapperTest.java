package com.github.cvesters.taxi.dispatcher.location;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.github.cvesters.taxi.dispatcher.location.bdo.Location;
import com.github.cvesters.taxi.dispatcher.location.dao.LocationDao;
import com.github.cvesters.taxi.dispatcher.location.dto.LocationDto;

class LocationMapperTest {

	@Nested
	class FromDao {

		@Test
		void success() {
			final LocationDao dao = mock(LocationDao.class);
			when(dao.getLatitude()).thenReturn(45.0);
			when(dao.getLongitude()).thenReturn(67.0);

			final Location location = LocationMapper.fromDao(dao);

			assertThat(location.latitude()).isEqualTo(45.0);
			assertThat(location.longitude()).isEqualTo(67.0);
		}

		@Test
		void invalid() {
			final LocationDao dao = mock(LocationDao.class);
			when(dao.getLatitude()).thenReturn(178.0);
			when(dao.getLongitude()).thenReturn(13.9);

			assertThatThrownBy(() -> LocationMapper.fromDao(dao))
					.isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Nested
	class ToDao {

		@Test
		void success() {
			final Location location = new Location(0.0, 0.0);

			final LocationDao dao = LocationMapper.toDao(location);

			assertThat(dao.getLatitude()).isEqualTo(0.0);
			assertThat(dao.getLongitude()).isEqualTo(0.0);
		}
	}

	@Nested
	class FromDto {

		@Test
		void success() {
			final LocationDto dto = new LocationDto(3.4, 6.7);

			final Location location = LocationMapper.fromDto(dto);

			assertThat(location.latitude()).isEqualTo(dto.latitude());
			assertThat(location.longitude()).isEqualTo(dto.longitude());
		}

		@Test
		void invalid() {
			final LocationDto dto = new LocationDto(334.4, 6.7);

			assertThatThrownBy(() -> LocationMapper.fromDto(dto)).isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Nested
	class ToDto {

		@Test
		void success() {
			final Location location = new Location(23.0, 77.2);

			final LocationDto dto = LocationMapper.toDto(location);

			assertThat(dto.latitude()).isEqualTo(location.latitude());
			assertThat(dto.longitude()).isEqualTo(location.longitude());
		}
	}
}
