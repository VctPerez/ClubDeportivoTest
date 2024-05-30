import { browser } from "k6/experimental/browser";
import { sleep } from "k6";
import { check } from "k6";

export const options = {
  scenarios: {
    ui: {
      executor: "shared-iterations", // para realizar iteraciones sin indicar el tiempo
      options: {
        browser: {
          type: "chromium",
        },
      },
    },
  },
  thresholds: {
    checks: ["rate==1.0"],
  },
};

export default async () => {
  const dni = "222222222";
  const nombre = "Ibai";
  const edad = "25";
  const cita = "mañana";
  const page = browser.newPage();
  try {
    await page.goto("http://localhost:4200");

    page.locator('input[name="nombre"]').type("Pablo Motos");
    sleep(1);
    page.locator('input[name="DNI"]').type("111111111");
    sleep(1);
    const button = page.locator('button[name="login"]');
    sleep(1);
    await Promise.all([page.waitForNavigation({waitUntil:
      'networkidle'}), button.click()]);

   sleep(1);

    const addButton = page.locator('button[name="add"]');
    await Promise.all([page.waitForNavigation(), addButton.click()]);

    page.locator('input[name="dni"]').type(dni);
    sleep(1);
    page.locator('input[name="nombre"]').type(nombre);
    sleep(1);
    page.locator('input[name="edad"]').type(edad);
    sleep(1);
    page.locator('input[name="cita"]').type(cita);
    sleep(1);

    const createButton = page.locator('button[type="submit"]');
    await Promise.all([page.waitForNavigation({waitUntil:
      'networkidle'}), createButton.click()]);

      sleep(3);

    check(page, {
      "dni correcto": (p) => p.$("table tbody tr:last-child td[title='"+dni+"']").textContent().trim() == dni,
      "nombre correcto" : (p) => p.$("table tbody tr:last-child td[title='"+nombre+"']").textContent().trim() == nombre,
    });
  } finally {
    page.close();
  }
};
