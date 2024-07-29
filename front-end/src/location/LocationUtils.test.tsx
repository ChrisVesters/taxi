import { format } from "./LocationUtils";

describe("format", () => {
  test("add digits", () => {
    expect(format({ latitude: 0.0, longitude: 0.2 })).toEqual("0.0000, 0.2000");
  });

  test("removes digits", () => {
    expect(format({ latitude: 0.123456, longitude: 0.987654 })).toEqual(
      "0.1235, 0.9877"
    );
  });
});
