import { defineConfig } from "vitest/config";

export default defineConfig({
    test: {
        environment: "node",
        clearMocks: true,
        restoreMocks: true,
        coverage: {
            provider: "v8",
            reporter: [
                "text",
                "html",
                "json-summary"
            ],
            reportsDirectory: "./coverage",
            exclude: [
                "**/tests/**",
                "**/fixtures/**"
            ]
        }
    }
});