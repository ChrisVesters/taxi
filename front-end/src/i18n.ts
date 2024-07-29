import i18n from "i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import HttpApi from "i18next-http-backend";
import { initReactI18next } from "react-i18next";

declare module "i18next" {
	interface CustomTypeOptions {
		returnNull: false;
	}
}

i18n.use(HttpApi)
	.use(initReactI18next)
	.use(LanguageDetector)
	.init({
		returnNull: false,
		fallbackLng: "en",
		backend: {
			loadPath: "/locales/{{lng}}.json"
		},
		interpolation: {
			escapeValue: false
		},
		react: {
			useSuspense: false
		}
	});

export default i18n;
