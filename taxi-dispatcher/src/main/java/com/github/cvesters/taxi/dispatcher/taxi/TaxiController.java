package com.github.cvesters.taxi.dispatcher.taxi;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cvesters.taxi.dispatcher.taxi.dto.TaxiDto;

@RestController
@RequestMapping("/api/taxis")
public class TaxiController {

	private final TaxiService taxiService;

	public TaxiController(final TaxiService taxiService) {
		this.taxiService = taxiService;
	}

	@GetMapping
	public List<TaxiDto> getAll() {
		return TaxiMapper.toDto(taxiService.getAll());
	}
}
