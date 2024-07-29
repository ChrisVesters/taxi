import { JSX } from "react";
import { Outlet } from "react-router-dom";

import Container from "@mui/material/Container";

import Menubar from "./Menubar";

export default function Page(): JSX.Element {
  return (
    <>
      <Menubar />
      <Container sx={{ paddingTop: 3, paddingBottom: 3 }}>
        <Outlet />
      </Container>
    </>
  );
}
