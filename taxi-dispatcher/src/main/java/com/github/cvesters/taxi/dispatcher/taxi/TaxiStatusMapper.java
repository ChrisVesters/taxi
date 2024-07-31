package com.github.cvesters.taxi.dispatcher.taxi;

import java.util.EnumMap;
import java.util.Map;

import com.github.cvesters.taxi.dispatcher.taxi.bdo.TaxiStatus;

public final class TaxiStatusMapper {

	private static final EnumMap<TaxiStatus, Integer> DAO_MAPPING = new EnumMap<>(TaxiStatus.class);

	static {
		DAO_MAPPING.put(TaxiStatus.AVAILABLE, 0);
		DAO_MAPPING.put(TaxiStatus.BOOKED, 1);
	}

	private TaxiStatusMapper() {
	}

	public static TaxiStatus fromDao(final int status) {
		return DAO_MAPPING.entrySet()
				.stream()
				.filter(entry -> entry.getValue() == status)
				.map(Map.Entry::getKey)
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

	public static int toDao(final TaxiStatus status) {
		return DAO_MAPPING.get(status);
	}

	public static String toDto(final TaxiStatus status) {
		return switch (status) {
			case AVAILABLE -> "AVAILABLE";
			case BOOKED -> "BOOKED";
		};
	}

}
