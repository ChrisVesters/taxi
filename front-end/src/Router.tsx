import {
  createBrowserRouter,
  createRoutesFromElements,
  Navigate,
  Route,
} from "react-router-dom";

import BookingsPage from "./booking/BookingsPage";
import Page from "./Page";
import FleetPage from "./taxi/FleetPage";

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route element={<Page />}>
      <Route index element={<Navigate to="fleet" replace />} />
      <Route path="fleet" element={<FleetPage />} />
      <Route path="bookings" element={<BookingsPage />} />
    </Route>
  )
);

export default router;
