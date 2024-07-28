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
			when(dao.getLatitude()).thenReturn(0.0);
			when(dao.getLongitude()).thenReturn(0.0);

			final Location location = LocationMapper.fromDao(dao);

			assertThat(location.latitude()).isEqualTo(0.0);
			assertThat(location.longitude()).isEqualTo(0.0);
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
	class ToDto {

		@Test
		void success() {
			final Location location = new Location(0.0, 0.0);

			final LocationDto dto = LocationMapper.toDto(location);

			assertThat(dto.latitude()).isEqualTo(0.0);
			assertThat(dto.longitude()).isEqualTo(0.0);
		}
	}
}
