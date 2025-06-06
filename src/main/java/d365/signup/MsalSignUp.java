package d365.signup;

//import d365.utilities.CommonUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import d365.utilities.CommonUtilities;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class MsalSignUp {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor jsExecutor;

    private static final int TIMEOUT = 30;

    CommonUtilities commonutilities = new CommonUtilities();

    // Locators
    private final By companyNameField = By.xpath("(//input[contains(@class, 'mat-mdc-input-element')])[1]");
    private final By subdomainField = By.xpath("(//input[contains(@class, 'mat-mdc-input-element')])[2]");
    private final By recaptchaFrame = By.xpath("//iframe[@title='reCAPTCHA']");
    private final By recaptchaCheckbox = By.className("recaptcha-checkbox-border");
    private final By signUpButton = By.xpath("//span[contains(text(),'Sign in with Microsoft')]");

    private static final Logger logger = LoggerFactory.getLogger(MsalSignUp.class);  // SLF4J logger instance

    public MsalSignUp(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        this.actions = new Actions(driver);
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void openSignUpPage() {
        try {
            String url = commonutilities.getSignUpUrl();
            driver.get(url);
            logger.info("✅ Opened the sign-up page: https://apps.kanidesk.com/app/signup");

            // Wait for the "Sign up using Microsoft" text to be clickable and click it
            WebElement signUpMicrosoftButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[text()='Sign-up with Microsoft']")));
            signUpMicrosoftButton.click();
            logger.info("✅ Clicked 'Sign up with Microsoft' button");
        } catch (Exception e) {
            logger.error("❌ Error opening sign-up page: " + e.getMessage());
            throw new RuntimeException("Failed to open the sign-up page");
        }
    }

    public void fillCompanyName(String companyName) {
        try {
            // Ensure the input field is clickable
            WebElement companyNameFieldElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[contains(@class, 'mat-mdc-input-element')])[1]"))); // Replace with actual locator
            companyNameFieldElement.clear();  // Clear any existing text
            companyNameFieldElement.sendKeys(companyName);  // Enter the company name
            logger.info("✅ Entered Company Name: " + companyName);
        } catch (Exception e) {
            logger.error("❌ Failed to enter company name: " + e.getMessage());
            throw new RuntimeException("Company name entry failed");
        }
    }

    public void fillSubdomain(String subdomain) {
        try {
            WebElement subdomainFieldElement = wait.until(ExpectedConditions.elementToBeClickable(subdomainField));
            subdomainFieldElement.sendKeys(subdomain);
            logger.info("✅ Entered Subdomain: " + subdomain);
        } catch (Exception e) {
            logger.error("❌ Failed to enter subdomain: " + e.getMessage());
            throw new RuntimeException("Subdomain entry failed");
        }
    }

    public void selectDataCenterRegion(String region) {
        try {
            // Step 1: Click the button to open the dropdown
            WebElement dataCenterDropdownButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='signup-btn-microsoft']//div[2]//a[1]")));
            dataCenterDropdownButton.click();
            logger.info("✅ Clicked the data center dropdown button");
            System.out.println("✅ Clicked the data center dropdown button");

            // Step 2: Click the dropdown arrow to reveal the options
            WebElement dropdownArrow = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class, 'mat-mdc-select-arrow') and contains(@class, 'ng-tns-c')])[1]")));
            dropdownArrow.click();
            logger.info("✅ Clicked the dropdown arrow to expand the options");
            System.out.println("✅ Clicked the dropdown arrow to expand the options");

            // Step 3: Set XPath for the region based on the input region
            String regionXPath = "";
            if ("United States".equalsIgnoreCase(region)) {
                regionXPath = "//mat-option[@id='mat-option-0']";  // XPath for United States
            } else if ("European Union".equalsIgnoreCase(region)) {
                regionXPath = "//mat-option[@id='mat-option-1']";  // XPath for European Union
            } else {
                logger.error("❌ Region not recognized: " + region);
                System.out.println("❌ Region not recognized: " + region);
                throw new RuntimeException("Region not recognized");
            }

            // Step 4: Select the desired option from the dropdown
            WebElement regionOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(regionXPath)));
            regionOption.click();
            logger.info("✅ Selected Data Center Region: " + region);
            System.out.println("✅ Selected Data Center Region: " + region);

        } catch (Exception e) {
            logger.error("❌ Failed to select data center region: " + e.getMessage());
            System.out.println("❌ Failed to select data center region: " + e.getMessage());
            throw new RuntimeException("Data center region selection failed");
        }
    }

    public void handleRecaptcha() {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(recaptchaFrame));

            // Find and click the reCAPTCHA checkbox inside the iframe to activate it
            WebElement recaptchaCheckboxElement = driver.findElement(recaptchaCheckbox);
            recaptchaCheckboxElement.click();

            // Log the action and print to the console
            logger.info("✅ Clicked reCAPTCHA checkbox. Please solve it manually...");
            System.out.println("✅ Clicked reCAPTCHA checkbox. Please solve it manually...");

            // Wait for a few seconds to allow manual solving of the reCAPTCHA
            Thread.sleep(30000);  // 30 seconds to manually solve the reCAPTCHA (you can adjust this time as needed)

            // Switch back to the main content after solving the reCAPTCHA
            driver.switchTo().defaultContent();

            // Wait until the "Sign up with Microsoft" button is clickable
            wait.until(ExpectedConditions.elementToBeClickable(signUpButton));

            // Log the successful completion and print to the console
            logger.info("✅ reCAPTCHA solved, returning to main page.");
            System.out.println("✅ reCAPTCHA solved, returning to main page.");
        } catch (InterruptedException e) {
            // Log the error and print it to the console
            logger.error("❌ Error during waiting for reCAPTCHA: " + e.getMessage());
            System.out.println("❌ Error during waiting for reCAPTCHA: " + e.getMessage());
            throw new RuntimeException("Failed to wait for reCAPTCHA completion");
        } catch (Exception e) {
            // Log the error and print it to the console
            logger.error("❌ Error during reCAPTCHA handling: " + e.getMessage());
            System.out.println("❌ Error during reCAPTCHA handling: " + e.getMessage());
            throw new RuntimeException("reCAPTCHA handling failed");
        }
    }

    public void signInWithMicrosoft(String username, String password) {
        try {
            // Click the "Sign in with Microsoft" button
            WebElement signUpButtonElement = wait.until(ExpectedConditions.elementToBeClickable(signUpButton));
            signUpButtonElement.click();
            logger.info("✅ Clicked 'Sign up with Microsoft'");
            System.out.println("✅ Clicked 'Sign up with Microsoft'");

            // Store the main window handle
            String mainWindow = driver.getWindowHandle();
            logger.info("🔄 Waiting for new window...");
            System.out.println("🔄 Waiting for new window...");

            // Wait for the new window to appear
            wait.until(d -> driver.getWindowHandles().size() > 1);

            // Switch to the new Microsoft login window
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }
            // Maximize the new sign-in window
            driver.manage().window().maximize();
            logger.info("✅ Maximized the sign-in window.");
            System.out.println("✅ Maximized the sign-in window.");

            // Wait for the email input field to be visible and enter the email
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0116")));
            emailInput.sendKeys(username);
            logger.info("✅ Entered email: " + username);
            System.out.println("✅ Entered email: " + username);

            // Click 'Next' button
            WebElement nextButtonEmail = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            nextButtonEmail.click();
            logger.info("✅ Clicked 'Next' after entering email.");
            System.out.println("✅ Clicked 'Next' after entering email.");

            // Wait for the password field to be visible and enter password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0118")));
            passwordInput.sendKeys(password);
            logger.info("✅ Entered password.");
            System.out.println("✅ Entered password.");

            // Click 'Next' button after entering password
            WebElement nextButtonPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            nextButtonPassword.click();
            logger.info("✅ Clicked 'Next' after entering password.");
            System.out.println("✅ Clicked 'Next' after entering password.");

            // Click the "Next" button (skip email and password entry)
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='idSubmit_ProofUp_Redirect']")));
            nextButton.click();
            logger.info("✅ Clicked 'Next' button to proceed.");
            System.out.println("✅ Clicked 'Next' button to proceed.");

            // Click the "Skip setup" button (to skip additional setup steps)
            WebElement skipSetupButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class, 'ms-Link')])[3]")));
            skipSetupButton.click();
            logger.info("✅ Clicked 'Skip setup' to bypass setup.");
            System.out.println("✅ Clicked 'Skip setup' to bypass setup.");

            // Handle "Stay Signed In?" prompt if it appears
            try {
                WebElement staySignedInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
                staySignedInButton.click();
                logger.info("✅ Clicked 'Stay Signed In'.");
                System.out.println("✅ Clicked 'Stay Signed In'.");
            } catch (Exception e) {
                logger.info("ℹ️ 'Stay Signed In' prompt did not appear.");
                System.out.println("ℹ️ 'Stay Signed In' prompt did not appear.");
            }

            // Wait until redirected back to the main application
            wait.until(ExpectedConditions.numberOfWindowsToBe(1));

            // Switch back to the main application window
            driver.switchTo().window(mainWindow);
            wait.until(ExpectedConditions.urlContains("msal-signup-res"));
            logger.info("✅ Successfully logged into Microsoft and switched back to the main application.");
            System.out.println("✅ Successfully logged into Microsoft and switched back to the main application.");

        } catch (Exception e) {
            logger.error("❌ Error during Microsoft sign-in: " + e.getMessage());
            System.err.println("❌ Error during Microsoft sign-in: " + e.getMessage());
            throw new RuntimeException("Microsoft sign-in failed");
        }
    }

    public void completeSignUpProcess() {
        try {
            // Step 1: Wait for redirection after Microsoft sign-up
            wait.until(ExpectedConditions.urlContains("msal-signup-res"));
            logger.info("✅ Redirected successfully after Microsoft sign-up.");
            System.out.println("✅ Redirected successfully after Microsoft sign-up.");

            // Step 2: Click the "Create New Account" button
            WebElement createNewAccountButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='item']")));
            createNewAccountButton.click();
            logger.info("✅ Clicked 'Create New Account' button.");
            System.out.println("✅ Clicked 'Create New Account' button.");

            // Step 3: Wait for redirection to the "Get Started" page
            wait.until(ExpectedConditions.urlContains("get-started/welcome"));
            logger.info("✅ Successfully redirected to the Get Started page.");
            System.out.println("✅ Successfully redirected to the Get Started page.");

            // Step 4: Verification step (Final confirmation)
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("get-started/welcome")) {
                logger.info("🎉 Helpdesk signed up successfully!");
                System.out.println("🎉 Helpdesk signed up successfully!");
            } else {
                throw new RuntimeException("❌ Helpdesk sign-up verification failed. Expected 'get-started/welcome', but got: " + currentUrl);
            }
        } catch (Exception e) {
            logger.error("❌ Error during sign-up completion: " + e.getMessage());
            System.err.println("❌ Error during sign-up completion: " + e.getMessage());
            throw new RuntimeException("Failed to complete sign-up process");
        }
    }

    public void verifyPageHeadersAndClickNext(String subdomain) {
        try {
            // Define expected headers for each step
            String[] expectedHeaders = {
                    "Welcome to Desk365. We're glad you're here.",
                    "Your support email is setup and ready to go.",
                    "Get Started Video:",
                    "Desk365 Support Bot:",
                    "Set up Contacts, Companies and Domains."
            };

            By headerLocator = By.xpath("//div[@class='item gp-gs-headline']");
            By nextButtonLocator = By.xpath("(//span[@class='mdc-button__label'])[6]");
            By subdomainTextLocator = By.xpath("//span[contains(text(), '" + subdomain + "')]");

            for (int i = 0; i < expectedHeaders.length; i++) {
                if (i == 1) { // Step 2: Verify both the header AND the subdomain
                    WebElement headerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(headerLocator));
                    String actualHeader = headerElement.getText();

                    if (actualHeader.equals(expectedHeaders[i])) {
                        logger.info("✅ Step 2: Header verified - " + actualHeader);
                        System.out.println("✅ Step 2: Header verified - " + actualHeader);
                    } else {
                        throw new RuntimeException("❌ Header mismatch at Step 2. Expected: " + expectedHeaders[i] + " but got: " + actualHeader);
                    }

                    WebElement subdomainTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(subdomainTextLocator));
                    if (subdomainTextElement.isDisplayed()) {
                        logger.info("✅ Step 2: Subdomain verified in URL - " + subdomain);
                        System.out.println("✅ Step 2: Subdomain verified in URL - " + subdomain);
                    } else {
                        throw new RuntimeException("❌ Subdomain verification failed at Step 2. Expected: " + subdomain);
                    }

                } else if (i == 3) { // Step 4: Verify both "Desk365 Support Bot" and "Desk365 Agent Bot"
                    By supportBotHeaderLocator = By.xpath("(//div[@class='gp-gs-headline'])[1]");
                    By agentBotHeaderLocator = By.xpath("(//div[@class='gp-gs-headline'])[2]");

                    WebElement supportBotHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(supportBotHeaderLocator));
                    WebElement agentBotHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(agentBotHeaderLocator));

                    if (supportBotHeader.getText().equals("Desk365 Support Bot:") &&
                            agentBotHeader.getText().equals("Desk365 Agent Bot:")) {
                        logger.info("✅ Step 4: Both headers verified - Desk365 Support Bot & Desk365 Agent Bot");
                        System.out.println("✅ Step 4: Both headers verified - Desk365 Support Bot & Desk365 Agent Bot");
                    } else {
                        throw new RuntimeException("❌ Step 4 Header verification failed. Expected: 'Desk365 Support Bot:' & 'Desk365 Agent Bot:'");
                    }

                } else { // For all other steps, verify the single header
                    WebElement headerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(headerLocator));
                    String actualHeader = headerElement.getText();

                    if (actualHeader.equals(expectedHeaders[i])) {
                        logger.info("✅ Step " + (i + 1) + ": Header verified - " + actualHeader);
                        System.out.println("✅ Step " + (i + 1) + ": Header verified - " + actualHeader);
                    } else {
                        throw new RuntimeException("❌ Header mismatch at step " + (i + 1) +
                                ". Expected: " + expectedHeaders[i] +
                                " but got: " + actualHeader);
                    }
                }

                // Click the "Next" button
                WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextButtonLocator));
                nextButton.click();
                logger.info("➡️ Clicked 'Next' button for step " + (i + 1));
                System.out.println("➡️ Clicked 'Next' button for step " + (i + 1));
            }

            // After last step, wait for final page (gp-home) to load
            wait.until(ExpectedConditions.urlContains("gp-home"));
            logger.info("✅ Successfully navigated to 'gp-home' page.");
            System.out.println("✅ Successfully navigated to 'gp-home' page.");

        } catch (Exception e) {
            logger.error("❌ Error during header verification: " + e.getMessage());
            System.err.println("❌ Error during header verification: " + e.getMessage());
            throw new RuntimeException("Header verification failed");
        }
    }

    public void verifyHelpdeskCreation() {
        try {
            // Step 1: Wait for the final page "gp-home" to load
            wait.until(ExpectedConditions.urlContains("gp-home"));
            logger.info("✅ Helpdesk successfully redirected to 'gp-home'.");
            System.out.println("✅ Helpdesk successfully redirected to 'gp-home'.");

            // Step 2: Define expected text for each ticket-action link
            String[] expectedTexts = {
                    "Joe Contact",
                    "#2: Sample Ticket: Attachments",
                    "Joe Contact",
                    "#1: Sample Ticket: Meet your first ticket"
            };

            // Step 3: Loop through and verify each element
            for (int i = 0; i < expectedTexts.length; i++) {
                By ticketLinkLocator = By.xpath("(//a[@class='ticket-action-link'])[" + (i + 1) + "]");
                WebElement ticketElement = wait.until(ExpectedConditions.visibilityOfElementLocated(ticketLinkLocator));
                String actualText = ticketElement.getText();

                if (actualText.equals(expectedTexts[i])) {
                    logger.info("✅ Verified ticket action link " + (i + 1) + ": " + actualText);
                    System.out.println("✅ Verified ticket action link " + (i + 1) + ": " + actualText);
                } else {
                    throw new RuntimeException("❌ Ticket verification failed at position " + (i + 1) +
                            ". Expected: '" + expectedTexts[i] + "' but got: '" + actualText + "'");
                }
            }

            logger.info("🎉 Signup successful! Helpdesk is fully set up.");
            System.out.println("🎉 Signup successful! Helpdesk is fully set up.");

        } catch (Exception e) {
            logger.error("❌ Error during helpdesk verification: " + e.getMessage());
            System.err.println("❌ Error during helpdesk verification: " + e.getMessage());
            throw new RuntimeException("Helpdesk verification failed");
        }
    }


//    public void updateHelpdeskSettings(String subdomain) {
//        try {
//            // Step 1: Navigate to Helpdesk Settings page
//            String settingsURL = "https://" + subdomain + ".kanidesk.com/app/settings/helpdesk";
//            driver.get(settingsURL);
//            System.out.println("🔄 Navigating to Helpdesk Settings page: " + settingsURL);
//
//            // Step 2: Ensure the page has fully loaded before interacting
//            wait.until(ExpectedConditions.urlContains("/settings/helpdesk"));
//            System.out.println("✅ Page loaded successfully.");
//
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//
////            // Step 3: Scroll and update first input field
////            WebElement inputField3 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='mat-input-element mat-form-field-autofill-control ng-tns-c57-5 ng-untouched ng-pristine ng-valid cdk-text-field-autofill-monitored']")));
////            js.executeScript("arguments[0].scrollIntoView(true);", inputField3);
////            Thread.sleep(1000);
////            String currentText = inputField3.getAttribute("value");
////            inputField3.clear();
////            inputField3.sendKeys(currentText + "-1");
////            System.out.println("✅ Updated field 'mat-input-3' to: " + currentText + "-1");
////
////            // Step 4: Scroll and update second input field
////            WebElement inputField4 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='mat-input-element mat-form-field-autofill-control ng-tns-c57-6 ng-untouched ng-pristine ng-valid cdk-text-field-autofill-monitored']")));
////            js.executeScript("arguments[0].scrollIntoView(true);", inputField4);
////            Thread.sleep(1000);
////            inputField4.clear();
////            inputField4.sendKeys("4");
////            System.out.println("✅ Set field 'mat-input-4' to: 4");
//
//            // Step 5: Scroll and select "English" Language
////            WebElement languageDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='mat-select-arrow-wrapper ng-tns-c82-13']")));
////            js.executeScript("arguments[0].scrollIntoView(true);", languageDropdown);
////            Thread.sleep(1000);
////            languageDropdown.click();
////            WebElement selectEnglish = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='English']")));
////            selectEnglish.click();
////            System.out.println("✅ Selected 'English' language.");
////
////            // Step 6: Scroll and select "dd/MM/yyyy" Date Format
////            WebElement dateDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='mat-select-arrow-wrapper ng-tns-c82-19']")));
////            js.executeScript("arguments[0].scrollIntoView(true);", dateDropdown);
////            Thread.sleep(1000);
////            dateDropdown.click();
////            WebElement selectDateFormat = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='mat-option-text'][normalize-space()='dd/MM/yyyy']")));
////            selectDateFormat.click();
////            System.out.println("✅ Selected date format 'dd/MM/yyyy'.");
////
////            // Step 7: Scroll and select Timezone
////            WebElement timezoneDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='mat-select-arrow-wrapper ng-tns-c82-15']")));
////            js.executeScript("arguments[0].scrollIntoView(true);", timezoneDropdown);
////            Thread.sleep(1000);
////            timezoneDropdown.click();
////            WebElement selectTimezone = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='(GMT+05:30) Mumbai, Kolkata, Chennai, New Delhi']")));
////            selectTimezone.click();
////            System.out.println("✅ Selected Timezone: (GMT+05:30) Mumbai, Kolkata, Chennai, New Delhi.");
////
////            // Step 8: Scroll and disable the first Toggle Button
////            WebElement toggleButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='mat-slide-toggle-1-input']//span[@class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']")));
////            js.executeScript("arguments[0].scrollIntoView(true);", toggleButton);
////            Thread.sleep(1000);
////            toggleButton.click();
////            System.out.println("✅ Disabled toggle button.");
////
////            // Step 9: Scroll and click Add Button
////            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Add Button']")));
////            js.executeScript("arguments[0].scrollIntoView(true);", addButton);
////            Thread.sleep(1000);
////            addButton.click();
////            System.out.println("✅ Clicked 'Add Button'.");
////
////            // Step 10: Scroll and set inputs
////            WebElement addButtonInput1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='mat-input-element mat-form-field-autofill-control ng-tns-c57-21 ng-untouched ng-pristine ng-invalid cdk-text-field-autofill-monitored']")));
////            js.executeScript("arguments[0].scrollIntoView(true);", addButtonInput1);
////            Thread.sleep(1000);
////            addButtonInput1.sendKeys("Custom Button");
////            System.out.println("✅ Set 'mat-input-13' to: Custom Button");
////
////            WebElement addButtonInput2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='mat-input-element mat-form-field-autofill-control ng-tns-c57-22 ng-untouched ng-pristine ng-invalid cdk-text-field-autofill-monitored']")));
////            js.executeScript("arguments[0].scrollIntoView(true);", addButtonInput2);
////            Thread.sleep(1000);
////            addButtonInput2.sendKeys(settingsURL);
////            System.out.println("✅ Set 'mat-input-14' to current URL: " + settingsURL);
////
////            // Step 11: Scroll and enable Second Toggle Button
////            WebElement toggleButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='mat-slide-toggle-4-input']//span[@class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']")));
////            js.executeScript("arguments[0].scrollIntoView(true);", toggleButton2);
////            Thread.sleep(1000);
////            toggleButton2.click();
////            System.out.println("✅ Enabled second toggle button.");
////
////            // Step 12: Scroll and add "desk365corp.com" to Input
////            WebElement domainInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='mat-autocomplete-trigger mat-input-element mat-form-field-autofill-control mat-chip-input gp-pointer ng-untouched ng-pristine ng-valid cdk-text-field-autofill-monitored']")));
////            js.executeScript("arguments[0].scrollIntoView(true);", domainInput);
////            Thread.sleep(1000);
////            domainInput.sendKeys("desk365corp.com");
////            domainInput.sendKeys(Keys.ENTER);
////            System.out.println("✅ Added 'desk365corp.com' to input field.");
//
//            // Step 13: Scroll and check the Checkbox
//            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[contains(@id, 'mat-mdc-checkbox-')])[1]")));
//            js.executeScript("arguments[0].scrollIntoView(true);", checkbox);
//            Thread.sleep(1000);
//            checkbox.click();
//            System.out.println("✅ Checked the required checkbox.");
//
//            // Step 14: Scroll and click Save Button
//            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("button[class='mdc-button mdc-button--raised mat-mdc-raised-button mat-primary mat-mdc-button-base'] span[class='mat-mdc-button-touch-target']")));
//            js.executeScript("arguments[0].scrollIntoView(true);", saveButton);
//            Thread.sleep(1000);
//            saveButton.click();
//            System.out.println("💾 ✅ Saved Helpdesk settings successfully!");
//
//        } catch (Exception e) {
//            System.err.println("❌ Error while updating helpdesk settings: " + e.getMessage());
//            throw new RuntimeException("Helpdesk settings update failed", e);
//        }
//    }

    public void updateHelpdeskSettings(String subdomain) {
//        String url = commonutilities.getHelpdeskSettingsUrl();
        String url = "https://" + subdomain + ".kanidesk.com/app/settings/helpdesk";
        driver.get(url);
        try {
            logger.info("🌐 Navigated to Helpdesk Settings Page");
            System.out.println("🌐 Navigated to Helpdesk Settings Page");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@qa-test='input-helpdeskName']")));

            // Scroll to Helpdesk Name input element
            WebElement helpdeskNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@qa-test='input-helpdeskName']")));
            scrollToElement(helpdeskNameInput);
            String newHelpdeskName = "Edited Helpdesk Name - Automation";
            helpdeskNameInput.clear();
            helpdeskNameInput.sendKeys(newHelpdeskName);
            logger.info("✅ Helpdesk Name updated to: " + newHelpdeskName);
            System.out.println("✅ Helpdesk Name updated to: " + newHelpdeskName);

            // Scroll to Next Ticket Number input element
            WebElement ticketNumberInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@qa-test='input-ticketNumber']")));
            scrollToElement(ticketNumberInput);
            String newTicketNumber = "100";
            ticketNumberInput.clear();
            ticketNumberInput.sendKeys(newTicketNumber);
            logger.info("✅ Ticket Number updated to: " + newTicketNumber);
            System.out.println("✅ Ticket Number updated to: " + newTicketNumber);

            // Scroll to Language dropdown and select an option
            WebElement languageDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@qa-test='dd-language']")));
            scrollToElement(languageDropdown);
            languageDropdown.click();
            WebElement languageOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option[@qa-test='options-language'][contains(normalize-space(span), 'English')]")));
            languageOption.click();
            logger.info("✅ Language selected");
            System.out.println("✅ Language selected");

            // Scroll to Date Format dropdown and select an option
            WebElement dateFormatDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@qa-test='dd-dateFormat']")));
            scrollToElement(dateFormatDropdown);
            dateFormatDropdown.click();
            WebElement dateFormatOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option[@qa-test='options-dateFormat'][contains(normalize-space(span), 'dd/MM/yyyy')]")));
            dateFormatOption.click();
            logger.info("✅ Date Format selected");
            System.out.println("✅ Date Format selected");

            // Scroll to Timezone dropdown and select an option
            WebElement timezoneDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@qa-test='dd-timezone']")));
            scrollToElement(timezoneDropdown);
            timezoneDropdown.click();
            WebElement timezoneOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option[@qa-test='options-timezone'][contains(normalize-space(span), '(GMT+05:30) Mumbai, Kolkata, Chennai, New Delhi')]")));
            timezoneOption.click();
            logger.info("✅ Timezone selected");
            System.out.println("✅ Timezone selected");

            // Scroll to Domain input field and enter domain
            WebElement domainInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//app-text-create-as-chip-list[@qa-test='addDomain-supportPortal']//input[@type='text']")));
            scrollToElement(domainInput);
            domainInput.sendKeys("example.com");
            domainInput.sendKeys("\n");
            logger.info("✅ Domain added: example.com");
            System.out.println("✅ Domain added: example.com");

            WebElement recaptchaCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-checkbox[@qa-test='checkbox-reCaptacha']//div[@class='mdc-checkbox']")));
            recaptchaCheckbox.click();
            logger.info("✅ Checked the reCAPTCHA checkbox");
            System.out.println("✅ Checked the reCAPTCHA checkbox");

            // Scroll to Save Button and click it
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@qa-test='saveBtn-helpdeskSettings']")));
            // Introduce a small delay before clicking the save button
            Thread.sleep(3000);  // Adjust delay as needed (2000ms = 2 seconds)

            saveButton.click();
            Thread.sleep(2000);  // Adjust delay as needed (2000ms = 2 seconds)
            logger.info("✅ Helpdesk Settings saved");
            System.out.println("✅ Helpdesk Settings saved");

        } catch (Exception e) {
            logger.error("❌ Error while editing Helpdesk Settings: " + e.getMessage());
            System.err.println("❌ Error while editing Helpdesk Settings: " + e.getMessage());
        }
    }

    // Method to scroll to the element
    public void scrollToElement(WebElement element) {
        // Scroll the element into view using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        // Optional: You can add a check here if you want to ensure that the element is interactable or visible
    }

    public void updateSupportPortalSettings(String subdomain) {
        try {
            // Step 1: Navigate to Support Portal Settings page
//            String portalSettingsURL = commonutilities.getSupportPortalSettingsUrl();
            String portalSettingsURL = "https://" + subdomain + ".kanidesk.com/app/settings/portal";
            driver.get(portalSettingsURL);
            logger.info("🌐 Navigated to Support Portal Settings Page");
            System.out.println("🌐 Navigated to Support Portal Settings Page");

            // Step 2: Check the reCAPTCHA checkbox

            Thread.sleep(2000);
            WebElement recaptchaCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-checkbox[@qa-test='checkbox-removeCaptcha']//div[@class='mdc-checkbox']")));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", recaptchaCheckbox);
            Thread.sleep(1000);  // Optional, adjust delay as needed

            recaptchaCheckbox.click();  // Now click the checkbox
            logger.info("✅ Checked the reCAPTCHA checkbox");
            System.out.println("✅ Checked the reCAPTCHA checkbox");

            // Step 3: Click the Save button
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@qa-test='saveBtn-supportPortal']")));
            Thread.sleep(2000); // Adjust delay as needed
            saveButton.click();
            Thread.sleep(2000); // Adjust delay as needed
            logger.info("✅ Support Portal Settings saved");
            System.out.println("✅ Support Portal Settings saved");

        } catch (Exception e) {
            logger.error("❌ Error while updating Support Portal Settings: " + e.getMessage());
            System.err.println("❌ Error while updating Support Portal Settings: " + e.getMessage());
        }
    }
}