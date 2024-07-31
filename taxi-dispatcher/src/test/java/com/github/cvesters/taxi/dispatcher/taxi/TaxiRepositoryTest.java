package com.github.cvesters.taxi.dispatcher.taxi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.github.cvesters.taxi.dispatcher.taxi.dao.TaxiDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Sql("/db/taxi.sql")
@DataJpaTest
@AutoConfigureTestDatabase
class TaxiRepositoryTest {

	@Autowired
	private TaxiRepository repository;

	@PersistenceContext
	private EntityManager entityManager;

	@Nested
	class FindAll {

		@Test
		void success() {
			final List<TaxiDao> taxis = repository.findAll();

			final Long expectedCount = (Long) entityManager.createNativeQuery("select COUNT(*) from taxis")
					.getSingleResult();
			assertThat(taxis)
					.isNotEmpty()
					.hasSize(expectedCount.intValue())
					.allSatisfy(taxi -> {
						final TaxiDao expected = entityManager.find(TaxiDao.class, taxi.getId());
						assertThat(taxi.getStatus()).isEqualTo(expected.getStatus());
						assertThat(taxi.getLocation()).isEqualTo(expected.getLocation());
					});
		}
	}
}
