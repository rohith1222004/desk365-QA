package d365;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private static WebDriver driver;

    // Initialize WebDriver before running all tests (before any scenario or feature)
    @BeforeClass
    public static void setUp() {
        driver = DriverFactory.getDriver();
    }

    // Quit WebDriver after all tests have run
    @AfterClass
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();  // Quit the browser
            driver = null;  // Clean up the WebDriver reference
        }
    }

    // Optionally, you can return the WebDriver instance if needed in your step definitions
    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver d) {
        driver = d;
    }
}
