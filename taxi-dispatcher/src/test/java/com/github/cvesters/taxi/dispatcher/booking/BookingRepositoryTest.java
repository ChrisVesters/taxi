package com.github.cvesters.taxi.dispatcher.booking;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.github.cvesters.taxi.dispatcher.booking.dao.BookingDao;
import com.github.cvesters.taxi.dispatcher.location.dao.LocationDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Sql({ "/db/taxi.sql", "/db/booking.sql" })
@DataJpaTest
@AutoConfigureTestDatabase
public class BookingRepositoryTest {

	@Autowired
	private BookingRepository repository;

	@PersistenceContext
	private EntityManager entityManager;

	@Nested
	class FindAll {

		@Test
		void success() {
			final List<BookingDao> bookings = repository.findAll();

			final Long expectedCount = (Long) entityManager.createNativeQuery("select COUNT(*) from bookings")
					.getSingleResult();
			assertThat(bookings)
					.hasSize(expectedCount.intValue())
					.allSatisfy(taxi -> {
						final BookingDao expected = entityManager.find(BookingDao.class, taxi.getId());
						assertThat(taxi.getStatus()).isEqualTo(expected.getStatus());
						assertThat(taxi.getTaxiId()).isEqualTo(expected.getTaxiId());
					});
		}
	}

	@Nested
	class Find {

		@Test
		void success() {
			final long id = 1L;
			final Optional<BookingDao> found = repository.findById(id);

			assertThat(found)
					.contains(entityManager.find(BookingDao.class, id));
		}

		@Test
		void notFofund() {
			assertThat(repository.findById(-1L)).isEmpty();
		}
	}

	@Nested
	class Save {

		@Test
		void create() {
			final var source = new LocationDao(0.0, 0.0);
			final var target = new LocationDao(0.2, 0.1);
			final BookingDao saved = repository.save(new BookingDao(0, source, target, null));

			final BookingDao found = entityManager.find(BookingDao.class, saved.getId());
			assertThat(found).isNotNull();
			assertThat(found.getStatus()).isZero();
			assertThat(found.getTaxiId()).isNull();
		}

		@Test
		void update() {
			final BookingDao dao = repository.findById(3L).get();
			dao.setStatus(1);
			dao.setTaxiId(1L);

			final BookingDao saved = repository.save(dao);

			final BookingDao found = entityManager.find(BookingDao.class, saved.getId());
			assertThat(found).isNotNull();
			assertThat(found.getStatus()).isEqualTo(1);
			assertThat(found.getTaxiId()).isEqualTo(1L);
		}

	}
}