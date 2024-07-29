import type { Config } from "jest";

const config: Config = {
	verbose: true,
	collectCoverage: true,
	preset: "ts-jest/presets/js-with-ts",
	testEnvironment: "jest-environment-jsdom",
	moduleNameMapper: {
		"\\.(jpg|jpeg|png|gif|eot|otf|webp|svg|ttf|woff|woff2|mp4|webm|wav|mp3|m4a|aac|oga)$":
			"<rootDir>/__mocks__/fileMock.js",
		"\\.(css|less)$": "<rootDir>/__mocks__/styleMock.js",
	},
	modulePaths: ["<rootDir>/src/"],
	moduleDirectories: ["node_modules", "src/"],
	setupFilesAfterEnv: ["<rootDir>/src/setupTests.ts"],
};

export default config;
