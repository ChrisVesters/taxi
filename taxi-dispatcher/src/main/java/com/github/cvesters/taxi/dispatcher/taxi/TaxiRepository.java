package com.github.cvesters.taxi.dispatcher.taxi;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.github.cvesters.taxi.dispatcher.taxi.dao.TaxiDao;

@Repository
public interface TaxiRepository extends ListCrudRepository<TaxiDao, Long> {

}
