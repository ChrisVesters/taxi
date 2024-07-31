package com.github.cvesters.taxi.dispatcher.booking;

import java.util.EnumMap;
import java.util.Map;

import com.github.cvesters.taxi.dispatcher.booking.bdo.BookingStatus;

public final class BookingStatusMapper {

	private static final EnumMap<BookingStatus, Integer> DAO_MAPPING = new EnumMap<>(BookingStatus.class);

	static {
		DAO_MAPPING.put(BookingStatus.OPEN, 0);
		DAO_MAPPING.put(BookingStatus.ASSIGNED, 1);
		DAO_MAPPING.put(BookingStatus.IN_PROGRESS, 2);
		DAO_MAPPING.put(BookingStatus.COMPLETED, 3);
		DAO_MAPPING.put(BookingStatus.CANCELLED, 4);
	}

	private BookingStatusMapper() {
	}

	public static BookingStatus fromDao(final int status) {
		return DAO_MAPPING.entrySet()
				.stream()
				.filter(entry -> entry.getValue() == status)
				.map(Map.Entry::getKey)
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

	public static int toDao(final BookingStatus status) {
		return DAO_MAPPING.get(status);
	}

	public static BookingStatus fromDto(final String status) {
		return switch (status) {
			case "OPEN" -> BookingStatus.OPEN;
			case "ASSIGNED" -> BookingStatus.ASSIGNED;
			case "IN_PROGRESS" -> BookingStatus.IN_PROGRESS;
			case "COMPLETED" -> BookingStatus.COMPLETED;
			case "CANCELLED" -> BookingStatus.CANCELLED;
			default -> throw new IllegalArgumentException("Invalid booking status: " + status);
		};
	}

	public static String toDto(final BookingStatus status) {
		return switch (status) {
			case OPEN -> "OPEN";
			case ASSIGNED -> "ASSIGNED";
			case IN_PROGRESS -> "IN_PROGRESS";
			case COMPLETED -> "COMPLETED";
			case CANCELLED -> "CANCELLED";
		};
	}
}
