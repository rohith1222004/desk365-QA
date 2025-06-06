package d365.signin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignInMSAL {

    public WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    public SignInMSAL(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));  // Using WebDriverWait
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    private static final Logger logger = LoggerFactory.getLogger(SignInMSAL.class);

    public void signInWithMicrosoft(String username, String password) {
        try {
            // Click the "Sign in with Microsoft" button
            WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@alt='Sign in With Microsoft']")));
            signUpButton.click();
            System.out.println("✅ Clicked 'Sign in with Microsoft'");
            logger.info("✅ Clicked 'Sign in with Microsoft'");


            // Store the main window handle
//            String mainWindow = driver.getWindowHandle();
//            System.out.println("🔄 Waiting for new window...");
//
//            // Wait for the new window to appear
//            wait.until(d -> driver.getWindowHandles().size() > 1);
//
//            // Switch to the new Microsoft login window
//            for (String handle : driver.getWindowHandles()) {
//                if (!handle.equals(mainWindow)) {
//                    driver.switchTo().window(handle);
//                    break;
//                }
//            }
//            // Maximize the new sign-in window
//            driver.manage().window().maximize();
//            System.out.println("✅ Maximized the sign-in window.");

            // Wait for the email input field to be visible and enter the email
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0116")));
            emailInput.sendKeys(username);
            System.out.println("✅ Entered email: " + username);
            logger.info("✅ Entered email: " + username);

            // Click 'Next' button
            WebElement nextButtonEmail = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            nextButtonEmail.click();
            System.out.println("✅ Clicked 'Next' after entering email.");
            logger.info("✅ Clicked 'Next' after entering email.");

            // Wait for the password field to be visible and enter password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0118")));
            passwordInput.sendKeys(password);
            System.out.println("✅ Entered password.");
            logger.info("✅ Entered password.");

            // Click 'Next' button after entering password
            WebElement nextButtonPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            nextButtonPassword.click();
            System.out.println("✅ Clicked 'Next' after entering password.");
            logger.info("✅ Clicked 'Next' after entering password.");

            // Click the "Next" button (skip email and password entry)
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='idSubmit_ProofUp_Redirect']")));
            nextButton.click();
            System.out.println("✅ Clicked 'Next' button to proceed.");
            logger.info("✅ Clicked 'Next' button to proceed.");

            // Click the "Skip setup" button (to skip additional setup steps)
            WebElement skipSetupButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class, 'ms-Link')])[3]")));
            skipSetupButton.click();
            System.out.println("✅ Clicked 'Skip setup' to bypass setup.");
            logger.info("✅ Clicked 'Skip setup' to bypass setup.");

            // Handle "Stay Signed In?" prompt if it appears
            try {
                WebElement staySignedInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
                staySignedInButton.click();
                System.out.println("✅ Clicked 'Stay Signed In'.");
                logger.info("✅ Clicked 'Stay Signed In'.");
            } catch (Exception e) {
                System.out.println("ℹ️ 'Stay Signed In' prompt did not appear.");
                logger.info("ℹ️ 'Stay Signed In' prompt did not appear.");
            }

            // Wait until redirected back to the main application
//            wait.until(ExpectedConditions.numberOfWindowsToBe(1));

            // Switch back to the main application window
//            driver.switchTo().window(mainWindow);
//            wait.until(ExpectedConditions.urlContains("msal-signup-res"));
//            System.out.println("✅ Successfully logged into Microsoft and switched back to the main application.");

            wait.until(ExpectedConditions.urlContains("gp-home"));
            System.out.println("✅ Successfully loaded Desk365 Home Page.");
            logger.info("✅ Successfully loaded Desk365 Home Page.");
        } catch (Exception e) {
            System.err.println("❌ Error during Microsoft sign-in: " + e.getMessage());
            logger.error("❌ Error during Microsoft sign-in: " + e.getMessage());
            throw new RuntimeException("Microsoft sign-in failed", e);
        }
    }


}
