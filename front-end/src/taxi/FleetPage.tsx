import { JSX, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";

import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Typography from "@mui/material/Typography";

import { Taxi, TaxiStatus } from "./TaxiTypes";
import { Gauge, gaugeClasses } from "@mui/x-charts/Gauge";
import { asTitle } from "../utils/TextUtils";

import config from "../config.json";

export default function FleetPage(): JSX.Element {
  const { t } = useTranslation();

  const [fleet, setFleet] = useState<Array<Taxi>>([]);

  useEffect(() => {
    fetch(`${config.server}/api/taxis`).then((response) =>
      response.json().then(setFleet)
    );
  }, []);

  return (
    <>
      <Typography variant="h1">{asTitle(t("fleet"))}</Typography>

      <Typography variant="h2">{asTitle(t("Booked"))}</Typography>
      <Gauge
        value={fleet.filter((taxi) => taxi.status === TaxiStatus.BOOKED).length}
        valueMax={fleet.length}
        startAngle={-110}
        endAngle={110}
        height={300}
        sx={{
          [`& .${gaugeClasses.valueText}`]: {
            fontSize: 40,
            transform: "translate(0px, 0px)",
          },
        }}
        text={({ value, valueMax }) => `${value} / ${valueMax}`}
      />

      <Typography variant="h2">{asTitle(t("Overview"))}</Typography>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>{asTitle(t("id"))}</TableCell>
            <TableCell>{asTitle(t("status"))}</TableCell>
            <TableCell align="right">{asTitle(t("location"))}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {fleet.map((taxi) => (
            <TableRow key={taxi.id}>
              <TableCell scope="row">{taxi.id}</TableCell>
              <TableCell>{t(taxi.status)}</TableCell>
              <TableCell align="right">
                {taxi.location.latitude.toFixed(4)},
                {taxi.location.longitude.toFixed(4)}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </>
  );
}
