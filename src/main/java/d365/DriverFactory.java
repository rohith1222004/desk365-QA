package d365;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class DriverFactory {

    // Declare the driver as a class variable
    private static WebDriver driver;

    // Method to initialize and return the WebDriver instance
    @BeforeClass
    public static WebDriver getDriver() {
        WebDriverManager.firefoxdriver().driverVersion("0.36.0").setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized"); // Start Firefox maximized for better visibility
        // For headless mode, which is often required in CI/CD pipelines
        options.addArguments("--headless");
        // Initialize the FirefoxDriver with the specified options
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        return driver;
    }

    @AfterClass
    public static void quitDriver() {
        if (driver != null) {
            driver.quit(); // Close the browser and end the WebDriver session
            System.out.println("✅ Browser closed successfully.");
        }
    }
}
