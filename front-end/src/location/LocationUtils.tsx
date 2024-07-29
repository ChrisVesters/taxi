import { Location } from "./LocationTypes";

export function format(location: Location): string {
  return `${location.latitude.toFixed(4)}, ${location.longitude.toFixed(4)}`;
}
