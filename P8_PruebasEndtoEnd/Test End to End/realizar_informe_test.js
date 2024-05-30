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
  const page = browser.newPage();
  try {
    await page.goto("http://localhost:4200");

    page.locator('input[name="nombre"]').type("Pablo Motos");
    sleep(1);
    page.locator('input[name="DNI"]').type("111111111");
    sleep(1);
    const button = page.locator('button[name="login"]');
    sleep(1);
    await Promise.all([
      page.waitForNavigation({ waitUntil: "networkidle" }),
      button.click(),
    ]);

    sleep(1);

    const paciente = page.$$("table tbody tr")[0];
    await Promise.all([page.waitForNavigation(), paciente.click()]);

    sleep(1);
    const viewButton = page.locator('button[name="view"]');
    await Promise.all([
      page.waitForNavigation({ waitUntil: "networkidle" }),
      viewButton.click(),
    ]);

    sleep(1);
    
    const addButton = page.locator('button[name="add"]');
    await Promise.all([
      page.waitForNavigation({ waitUntil: "networkidle" }),
      addButton.click(),
    ]);

    sleep(1);

    page.locator('textarea').type("Paciente con cáncer");

    const saveButton = page.locator('button[name="save"]');
    await Promise.all([
      page.waitForNavigation({ waitUntil: "networkidle" }),
      saveButton.click(),
    ]);

    sleep(1);

    check({
        'Title': p => p.locator('h1').textContent() == 'Informe de la imagen',
    });
  } finally {
    page.close();
  }
};
