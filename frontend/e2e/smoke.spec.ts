import { test, expect } from "@playwright/test";

test("smoke: login then list tickets", async ({ page }) => {
  await page.goto("/login");

  await page.getByPlaceholder("username").fill("admin");
  await page.getByPlaceholder("password").fill("admin");
  await page.getByRole("button", { name: "Se connecter" }).click();

  await expect(page).toHaveURL("/");

  await expect(page.getByRole("heading", { name: "Tickets" })).toBeVisible();
});
