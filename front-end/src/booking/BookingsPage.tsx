import { useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";

import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Typography from "@mui/material/Typography";

import { PieChart } from "@mui/x-charts/PieChart";

import { format } from "../location/LocationUtils";
import { asTitle } from "../utils/TextUtils";
import { Booking, BookingStatus } from "./BookingTypes";

import config from "../config.json";

export default function BookingsPage(): JSX.Element {
  const { t } = useTranslation();

  const [bookings, setBookings] = useState<Array<Booking>>([]);

  useEffect(() => {
    fetch(`${config.server}/api/bookings`).then((response) =>
      response.json().then(setBookings)
    );
  }, []);

  const bookingsByStatus = useMemo(() => {
    return Object.values(BookingStatus).map((status) => ({
      id: status,
      value: bookings.filter((booking) => booking.status === status).length,
      label: asTitle(t(status)),
    }));
  }, [bookings]);

  return (
    <>
      <Typography variant="h1">{asTitle(t("booking_other"))}</Typography>

      <Typography variant="h2">{asTitle(t("Status"))}</Typography>
      <PieChart
        series={[
          {
            data: bookingsByStatus,
            innerRadius: 30,
            outerRadius: 100,
            paddingAngle: 2,
          },
        ]}
        height={300}
      />

      <Typography variant="h2">{asTitle(t("Overview"))}</Typography>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>{asTitle(t("id"))}</TableCell>
            <TableCell>{asTitle(t("status"))}</TableCell>
            <TableCell align="right">{asTitle(t("origin"))}</TableCell>
            <TableCell align="right">{asTitle(t("destination"))}</TableCell>
            <TableCell align="right">{asTitle(t("assignedTo"))}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {bookings.map((booking) => (
            <TableRow key={booking.id}>
              <TableCell scope="row">{booking.id}</TableCell>
              <TableCell>{asTitle(t(booking.status))}</TableCell>
              <TableCell align="right">{format(booking.origin)}</TableCell>
              <TableCell align="right">{format(booking.destination)}</TableCell>
              <TableCell align="right">
                {booking.taxi ?? asTitle(t("unassigned"))}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </>
  );
}
