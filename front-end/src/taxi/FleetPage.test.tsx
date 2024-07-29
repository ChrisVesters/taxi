import { screen, render, getAllByRole, waitFor } from "@testing-library/react";

import FleetPage from "./FleetPage";
import { Taxi, TaxiStatus } from "./TaxiTypes";
import { format } from "../location/LocationUtils";

jest.mock("@mui/x-charts/Gauge", () => ({
  Gauge: jest.fn().mockImplementation(({ children }) => children),
  gaugeClasses: {
    valueText: "valueText",
  },
}));

beforeEach(() => {
  fetchMock.resetMocks();
  jest.clearAllMocks();
});

test("renders empty", async () => {
  fetchMock.mockResponseOnce(JSON.stringify([]));

  render(<FleetPage />);

  await waitFor(() =>
    expect(fetchMock).toHaveBeenCalledWith("http://localhost:8080/api/taxis")
  );

  expect(screen.getByRole("heading", { level: 1 })).toHaveTextContent("Fleet");
  expect(screen.getAllByRole("heading", { level: 2 })[0]).toHaveTextContent(
    "Booked"
  );
  expect(screen.getAllByRole("heading", { level: 2 })[1]).toHaveTextContent(
    "Overview"
  );

  const rows = screen.getAllByRole("row");
  expect(rows).toHaveLength(1);

  const tableHeaders = getAllByRole(rows[0], "columnheader");
  expect(tableHeaders[0]).toHaveTextContent("Id");
  expect(tableHeaders[1]).toHaveTextContent("Status");
  expect(tableHeaders[2]).toHaveTextContent("Location");
});

test("renders data", async () => {
  const data: Array<Taxi> = [
    {
      id: 1,
      status: TaxiStatus.BOOKED,
      location: {
        latitude: 48.858093,
        longitude: 2.294694,
      },
    },
    {
      id: 2,
      status: TaxiStatus.AVAILABLE,
      location: {
        latitude: 48.858093,
        longitude: 2.294694,
      },
    },
  ];

  fetchMock.mockResponseOnce(JSON.stringify(data));

  render(<FleetPage />);

  await waitFor(() =>
    expect(fetchMock).toHaveBeenCalledWith("http://localhost:8080/api/taxis")
  );

  expect(screen.getByRole("heading", { level: 1 })).toHaveTextContent("Fleet");
  expect(screen.getAllByRole("heading", { level: 2 })[0]).toHaveTextContent(
    "Booked"
  );
  expect(screen.getAllByRole("heading", { level: 2 })[1]).toHaveTextContent(
    "Overview"
  );

  const rows = screen.getAllByRole("row");
  expect(rows).toHaveLength(data.length + 1);

  const tableHeaders = getAllByRole(rows[0], "columnheader");
  expect(tableHeaders[0]).toHaveTextContent("Id");
  expect(tableHeaders[1]).toHaveTextContent("Status");
  expect(tableHeaders[2]).toHaveTextContent("Location");

  data.forEach((taxi, index) => {
    const row = getAllByRole(rows[index + 1], "cell");
    expect(row[0]).toHaveTextContent(taxi.id.toString());
    expect(row[1]).toHaveTextContent(taxi.status);
    expect(row[2]).toHaveTextContent(format(taxi.location));
  });
});
