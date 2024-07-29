import userEvent from "@testing-library/user-event";
import { screen } from "@testing-library/react";

import { renderRouter } from "./test/TestUtils";

import Menubar from "./Menubar";

const fleetLink = () => screen.getByRole("link", { name: "Fleet" });
const bookingsLink = () => screen.getByRole("link", { name: "Bookings" });

beforeEach(() => {
  jest.clearAllMocks();
});

test("renders", () => {
  renderRouter(<Menubar />);

  expect(fleetLink()).toBeVisible();
  expect(bookingsLink()).toBeVisible();
});

test.each([
  ["fleet", fleetLink, "/fleet"],
  ["bookings", bookingsLink, "/bookings"],
])("links to %s when clicked", async (_name, button, page) => {
  const user = userEvent.setup();

  renderRouter(<Menubar />, "/", [page]);

  await user.click(button());

  expect(window.location.pathname).toBe(page);
});
