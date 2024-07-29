import { asTitle } from "./TextUtils";

describe("asTitle", () => {
  it("capitalizes the first letter", () => {
    expect(asTitle("test")).toEqual("Test");
  });

  it("only capitalizes the first letter", () => {
    expect(asTitle("test test")).toEqual("Test test");
  });

  it("does not change already capitalized letters", () => {
    expect(asTitle("TEST")).toEqual("TEST");
  });
});
