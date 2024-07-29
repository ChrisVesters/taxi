export type Taxi = {
  id: number;
  status: TaxiStatusType;
  location: Location;
};

export type Location = {
  latitude: number;
  longitude: number;
}

export const TaxiStatus = {
  AVAILABLE: "AVAILABLE",
  BOOKED: "BOOKED",
} as const;

export type TaxiStatusType = typeof TaxiStatus[keyof typeof TaxiStatus];
