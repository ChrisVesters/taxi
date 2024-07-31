import { Location } from "../location/LocationTypes";

export type Booking = {
  id: number;
  status: BookingStatusType;
  start: Location;
  destination: Location;
  taxiId: number | null;
};

export const BookingStatus = {
  OPEN: "OPEN",
  ASSIGNED: "ASSIGNED",
  IN_PROGRESS: "IN_PROGRESS",
  COMPLETED: "COMPLETED",
  CANCELLED: "CANCELLED",
} as const;

export type BookingStatusType =
  (typeof BookingStatus)[keyof typeof BookingStatus];
