import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import i18n from "./i18n";

i18n.init().then(() => {
  document.documentElement.lang = i18n.language;

  const container = document.getElementById("root");
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const root = ReactDOM.createRoot(container!);
  root.render(
    <React.StrictMode>
      <App />
    </React.StrictMode>
  );
});
