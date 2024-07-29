import { screen, render, getAllByRole, waitFor } from "@testing-library/react";

import BookingsPage from "./BookingsPage";
import { Booking, BookingStatus } from "./BookingTypes";
import { format } from "../location/LocationUtils";

jest.mock("@mui/x-charts/PieChart", () => ({
  PieChart: jest.fn().mockImplementation(({ children }) => children),
}));

beforeEach(() => {
  fetchMock.resetMocks();
  jest.clearAllMocks();
});

test("renders empty", async () => {
  fetchMock.mockResponseOnce(JSON.stringify([]));

  render(<BookingsPage />);

  await waitFor(() =>
    expect(fetchMock).toHaveBeenCalledWith("http://localhost:8080/api/bookings")
  );

  expect(screen.getByRole("heading", { level: 1 })).toHaveTextContent(
    "Booking_other"
  );
  expect(screen.getAllByRole("heading", { level: 2 })[0]).toHaveTextContent(
    "Status"
  );
  expect(screen.getAllByRole("heading", { level: 2 })[1]).toHaveTextContent(
    "Overview"
  );

  const rows = screen.getAllByRole("row");
  expect(rows).toHaveLength(1);

  const tableHeaders = getAllByRole(rows[0], "columnheader");
  expect(tableHeaders[0]).toHaveTextContent("Id");
  expect(tableHeaders[1]).toHaveTextContent("Status");
  expect(tableHeaders[2]).toHaveTextContent("Origin");
  expect(tableHeaders[3]).toHaveTextContent("Destination");
  expect(tableHeaders[4]).toHaveTextContent("AssignedTo");
});

test("renders data", async () => {
  const data: Array<Booking> = [
    {
      id: 1,
      status: BookingStatus.COMPLETED,
      origin: {
        latitude: 48.858093,
        longitude: 2.294694,
      },
      destination: {
        latitude: 48.858093,
        longitude: 2.294694,
      },
      taxi: 1,
    },
    {
      id: 2,
      status: BookingStatus.IN_PROGRESS,
      origin: {
        latitude: 48.858093,
        longitude: 2.294694,
      },
      destination: {
        latitude: 48.858093,
        longitude: 2.294694,
      },
      taxi: 1,
    },
    {
      id: 3,
      status: BookingStatus.CANCELLED,
      origin: {
        latitude: 48.858093,
        longitude: 2.294694,
      },
      destination: {
        latitude: 48.858093,
        longitude: 2.294694,
      },
      taxi: null,
    },
    {
      id: 4,
      status: BookingStatus.OPEN,
      origin: {
        latitude: 48.858093,
        longitude: 2.294694,
      },
      destination: {
        latitude: 48.858093,
        longitude: 2.294694,
      },
      taxi: null,
    },
  ];

  fetchMock.mockResponseOnce(JSON.stringify(data));

  render(<BookingsPage />);

  await waitFor(() =>
    expect(fetchMock).toHaveBeenCalledWith("http://localhost:8080/api/bookings")
  );

  expect(screen.getByRole("heading", { level: 1 })).toHaveTextContent(
    "Booking_other"
  );
  expect(screen.getAllByRole("heading", { level: 2 })[0]).toHaveTextContent(
    "Status"
  );
  expect(screen.getAllByRole("heading", { level: 2 })[1]).toHaveTextContent(
    "Overview"
  );

  const rows = screen.getAllByRole("row");
  expect(rows).toHaveLength(data.length + 1);

  const tableHeaders = getAllByRole(rows[0], "columnheader");
  expect(tableHeaders[0]).toHaveTextContent("Id");
  expect(tableHeaders[1]).toHaveTextContent("Status");
  expect(tableHeaders[2]).toHaveTextContent("Origin");
  expect(tableHeaders[3]).toHaveTextContent("Destination");
  expect(tableHeaders[4]).toHaveTextContent("AssignedTo");

  data.forEach((booking, index) => {
    const row = getAllByRole(rows[index + 1], "cell");
    expect(row[0]).toHaveTextContent(booking.id.toString());
    expect(row[1]).toHaveTextContent(booking.status);
    expect(row[2]).toHaveTextContent(format(booking.origin));
    expect(row[3]).toHaveTextContent(format(booking.destination));
    expect(row[4]).toHaveTextContent(booking.taxi?.toString() ?? "Unassigned");
  });
});
