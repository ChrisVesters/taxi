import { createBrowserRouter, RouterProvider } from "react-router-dom";

import { render } from "@testing-library/react";

export function renderRouter(
  element: JSX.Element,
  path = "/",
  additionalPaths: string[] = [],
  previousPaths: string[] = []
): void {
  previousPaths.forEach((p) => window.history.pushState({}, "", p));
  window.history.pushState({}, "", path);
  const search = path.indexOf("?");
  const pathName = search !== -1 ? path.substring(0, search) : path;
  const pathMap = additionalPaths
    .concat(previousPaths)
    .map((p) => {
      return {
        path: p,
        element: <div />,
      };
    })
    .concat({
      path: pathName,
      element,
    });

  const router = createBrowserRouter(pathMap);
  render(<RouterProvider router={router} />);
}
