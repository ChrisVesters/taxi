import { JSX, useCallback } from "react";
import { useTranslation } from "react-i18next";
import { NavLink } from "react-router-dom";

import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";

import { asTitle } from "./utils/TextUtils";

type NavLinkStyleProps = {
  isActive: boolean;
  isPending: boolean;
};

export default function Menubar(): JSX.Element {
  const { t } = useTranslation();

  const menuStyle = useCallback(
    (args: NavLinkStyleProps): React.CSSProperties => {
      return {
        textDecoration: args.isActive ? "underline" : "none",
        fontSize: "1.2rem",
        color: "white",
        marginLeft: "0.5rem",
        marginRight: "1rem",
      };
    },
    []
  );

  return (
    <>
      <AppBar
        sx={{
          zIndex: (theme) => theme.zIndex.drawer + 1,
        }}
      >
        <Toolbar>
          <NavLink to="/fleet" style={menuStyle}>
            {asTitle(t("fleet"))}
          </NavLink>
          <NavLink to="/bookings" style={menuStyle}>
            {asTitle(t("bookings"))}
          </NavLink>
        </Toolbar>
      </AppBar>
      <Toolbar />
    </>
  );
}
