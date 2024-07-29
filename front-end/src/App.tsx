import { JSX } from "react";
import { I18nextProvider } from "react-i18next";
import { RouterProvider } from "react-router-dom";

import i18n from "i18next";

import { StyledEngineProvider, ThemeProvider } from "@mui/material/styles";

import router from "./Router";
import theme from "./Theme";

console.log(i18n);

export default function App(): JSX.Element {
  return (
    <StyledEngineProvider injectFirst>
      <ThemeProvider theme={theme}>
        <I18nextProvider i18n={i18n}>
          <RouterProvider router={router} />
        </I18nextProvider>
      </ThemeProvider>
    </StyledEngineProvider>
  );
}
