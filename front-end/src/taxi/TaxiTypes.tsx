import { Location } from "../location/LocationTypes";

export type Taxi = {
  id: number;
  status: TaxiStatusType;
  location: Location;
};

export const TaxiStatus = {
  AVAILABLE: "AVAILABLE",
  BOOKED: "BOOKED",
} as const;

export type TaxiStatusType = (typeof TaxiStatus)[keyof typeof TaxiStatus];
