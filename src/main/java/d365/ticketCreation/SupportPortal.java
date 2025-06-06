package d365.ticketCreation;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import d365.utilities.*;

public class SupportPortal {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;
    private String mainWindowHandle;
    private Actions actions;
    private CommonUtilities commonUtilities;

    private static final int TIMEOUT = 30;

    // ✅ Locators for Ticket Fields
    private final By createTicketButton = By.xpath("/html/body/app-root/app-main-nav/div/div/div[2]/div/div/div/div[1]/button/span[2]/a");
    private final By emailField = By.xpath("//*[@id='mat-input-1']");
    private final By subjectField = By.xpath("//*[@id='mat-input-2']");
    private final By descriptionField = By.xpath("//*[@id='reply']/app-froala-editor/div/div/div[3]/div");

    // ✅ Dropdowns
    private final By groupDropdown = By.xpath("//div[@id='mat-select-value-5']");
    private final By assignToDropdown = By.xpath("//*[@id='mat-select-value-7']");
    private final By typeDropdown = By.xpath("//*[@id='mat-select-value-9']");
    private final By categoryDropdown = By.xpath("//*[@id='mat-select-value-11']");
    private final By subCategoryDropdown = By.xpath("//*[@id='mat-select-value-13']");
    private final By dropdownField = By.xpath("//*[@id='mat-select-value-15']");
    private final By dropdownWithSections = By.xpath("//*[@id='mat-select-value-17']");
    private final By nestedDropdown = By.xpath("//*[@id='mat-select-value-19']");

    // ✅ Text Inputs
    private final By textInputField = By.xpath("//*[@id='mat-input-3']");
    private final By paragraphField = By.xpath("//*[@id='mat-input-4']");
    private final By numberField = By.xpath("//*[@id='mat-input-6']");
    private final By decimalField = By.xpath("//*[@id='mat-input-7']");
    private final By dateField = By.xpath("//*[@id=\"mat-input-5\"]"); // Date Picker
    private final By dateField1 = By.xpath("//*[@id=\"mat-input-4\"]"); // Date Picker

    // ✅ Submit Button
    private final By submitButton = By.xpath("/html/body/app-root/app-main-nav/div/div/div[3]/app-sp-form/div/div/div[2]/div/div[3]/app-button-spinner/button");
    private final By submitButton1 = By.xpath("/html/body/app-root/app-main-nav/div/div/div[3]/app-sp-form/div/div/div[2]/div/div[3]/app-button-spinner/button/span[2]/div/span");

    public SupportPortal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    /**
     * ✅ Create Ticket Without Signing In
     */
    public void createTicketWithoutSignIn(String email, String subject, String description) {
        System.out.println("📌 Creating Ticket Without Sign In...");

        // ✅ Click "Create Ticket"
        WebElement createTicket = wait.until(ExpectedConditions.elementToBeClickable(createTicketButton));
        createTicket.click();
        System.out.println("✅ Clicked 'Create Ticket' Without Sign In");

        // ✅ Fill Ticket Details
        enterTextField(emailField, email, "Email");
        enterTextField(subjectField, subject, "Subject");
        enterDescriptionField(description);

        // ✅ Selecting dropdown fields in correct order
        selectDropdownValue(groupDropdown, "Group");
        selectDropdownValue(assignToDropdown, "Assign To");
        selectDropdownValue(typeDropdown, "Type");
        selectDropdownValue(categoryDropdown, "Category");
        selectDropdownValue(subCategoryDropdown, "Sub Category");
        selectDropdownValue(dropdownField, "Dropdown Field");

        // ✅ Enter Text Inputs
        enterTextField(textInputField, "Support Portal Text", "Text Input");
        enterTextField(paragraphField, "This is a paragraph input from the Webform.", "Paragraph Input");

        // ✅ Enter Numeric Fields
        enterTextField(numberField, "456", "Number Field");
        enterTextField(decimalField, "123.45", "Decimal Field");

        selectDate(dateField);

        selectDropdownAndEnterText(
                "//*[@id='mat-select-value-17']",  // Dropdown XPath
                "//*[@id='mat-input-8']",  // Text Field XPath
                "Dropdown with Sections",
                "SP Custom Field"
        );

        selectNestedDropdown(
                "//*[@id='mat-select-value-19']",  // First Dropdown XPath
                "//*[@id='mat-select-value-21']",  // Second Dropdown XPath
                "First Nested Dropdown",
                "Second Nested Dropdown"
        );

        // ✅ Submit the Ticket
        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitBtn.click();
        System.out.println("🚀 Clicked 'Submit Ticket' Button!");
    }

    public void openSupportPortalInNewTab() {

        commonUtilities = new CommonUtilities();

        System.out.println("🌐 Opening Support Portal in a new tab...");

        // ✅ Store the main window handle
        mainWindowHandle = driver.getWindowHandle();

        // ✅ Get Support Portal URL from CommonUtilities
        String supportPortalUrl = commonUtilities.getSupportPortalUrl();

        // ✅ Open Support Portal in a New Tab
        jsExecutor.executeScript("window.open(arguments[0], '_blank');", supportPortalUrl);

        // ✅ Switch to the New Tab
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(mainWindowHandle)) {
                driver.switchTo().window(tab);
                break;
            }
        }

        // ✅ Wait for the Support Portal to Load
        wait.until(ExpectedConditions.urlContains("/support"));
        System.out.println("✅ Support Portal Loaded Successfully");
    }

    public void signInToSupportPortal() {
        System.out.println("🔑 Signing in to Support Portal...");
        // ✅ Click the "Sign In" button
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-main-nav/div/div/div[1]/div/div/div[3]/button[1]/span[2]")));
        signInButton.click();
        System.out.println("✅ Clicked 'Sign In' Button");

        // ✅ Click "Sign in with Microsoft"
        WebElement signInWithMSButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-main-nav/div/div/div[3]/app-msal-signin/div/div/div/div[1]/div[2]/button")));
        signInWithMSButton.click();
        System.out.println("✅ Clicked 'Sign in with Microsoft'");

        WebElement nextMSButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"idSubmit_ProofUp_Redirect\"]")));
        nextMSButton.click();
        System.out.println("✅ Clicked 'Next'");

        // Click the "Skip setup" button (to skip additional setup steps)
        WebElement skipSetupButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class, 'ms-Link')])[3]")));
        skipSetupButton.click();
        System.out.println("✅ Clicked 'Skip setup' to bypass setup.");

        // ✅ Wait for Support Portal Home Page to Load
        wait.until(ExpectedConditions.urlContains("/support/sp-home"));
        System.out.println("✅ Successfully signed in to Support Portal.");
    }

    /**
     * ✅ Create Ticket After Signing In
     */

    public void createTicketAfterSignIn(String subject, String description) {
        System.out.println("📌 Creating Ticket After Sign In...");

        // ✅ Click "Create Ticket" Button
        WebElement createTicketButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-main-nav/div/div/div[2]/div/div/div/div[1]/button/span[2]/a")));
        createTicketButton.click();
        System.out.println("✅ Clicked 'Create Ticket' After Sign In");

        // ✅ Fill Ticket Details (Skip Email Field)
        enterTextField(By.xpath("//*[@id='mat-input-1']"), subject, "Subject");
        enterDescriptionField(description);

        // ✅ Select Dropdowns
        selectDropdownValue(By.xpath("//*[@id='mat-select-value-5']"), "Group");
        selectDropdownValue(By.xpath("//*[@id='mat-select-value-7']"), "Assign To");
        selectDropdownValue(By.xpath("//*[@id='mat-select-value-9']"), "Type");
        selectDropdownValue(By.xpath("//*[@id='mat-select-value-11']"), "Category");
        selectDropdownValue(By.xpath("//*[@id='mat-select-value-13']"), "Sub Category");
        selectDropdownValue(By.xpath("//*[@id='mat-select-value-15']"), "Dropdown Field");

        // ✅ Enter Additional Fields
        enterTextField(By.xpath("//*[@id='mat-input-2']"), "Support Portal Text", "Text Input");
        enterTextField(By.xpath("//*[@id='mat-input-3']"), "This is a test paragraph.", "Paragraph Input");
        enterTextField(By.xpath("//*[@id='mat-input-5']"), "456", "Number Field");
        enterTextField(By.xpath("//*[@id='mat-input-6']"), "123.45", "Decimal Field");
        selectDate(dateField1);

        // ✅ Handle Dropdown with Input Field
        selectDropdownAndEnterText(
                "//*[@id='mat-select-value-17']",  // Dropdown XPath
                "//*[@id='mat-input-7']",  // Text Field XPath
                "Dropdown with Sections",
                "SP Custom Field"
        );

        selectNestedDropdown(
                "//*[@id='mat-select-value-19']",  // First Dropdown XPath
                "//*[@id='mat-select-value-21']",  // Second Dropdown XPath
                "First Nested Dropdown",
                "Second Nested Dropdown"
        );

        // ✅ Submit the Ticket
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButton1));
        submitButton.click();
        System.out.println("🚀 Clicked 'Submit Ticket' Button!");
    }

    /**
     * ✅ Enter text in the paragraph field with focus stabilization
     */
    private void enterDescriptionField(String description) {
        try {
            WebElement descField = waitForElementClickable(descriptionField);
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", descField);
            descField.click();
            Thread.sleep(500);
            jsExecutor.executeScript("arguments[0].innerHTML = arguments[1];", descField, description);
            System.out.println("✅ Entered Description: " + description);
        } catch (Exception e) {
            System.err.println("❌ Failed to enter description: " + e.getMessage());
        }
    }

    /**
     * ✅ Enter text in a text field
     */
    private void enterTextField(By field, String value, String fieldName) {
        WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(field));
        inputField.clear();
        inputField.sendKeys(value);
        System.out.println("✅ Entered " + fieldName + ": " + value);
    }

    /**
     * ✅ Select a value from a dropdown
     */
    private void selectDropdownValue(By dropdown, String fieldName) {
        try {
            WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
            scrollToElement(dropdownElement);

            // ✅ Click the dropdown once
            dropdownElement.click();
            Thread.sleep(300); // Small delay for UI update

            // ✅ Check if dropdown options are visible, retry click if necessary
            By dropdownPanel = By.xpath("//div[contains(@class, 'mat-mdc-select-panel')]");
            if (!isElementPresent(dropdownPanel)) {
                System.out.println("⚠️ Dropdown did not open, clicking again...");
                dropdownElement.click();
            }

            // ✅ Wait for the second option to be visible
            WebElement secondOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//mat-option)[2]")));
            scrollToElement(secondOption);
            secondOption.click();

            System.out.println("✅ Selected " + fieldName);

        } catch (Exception e) {
            System.err.println("❌ Failed to select " + fieldName + ": " + e.getMessage());
        }
    }

    /**
     * ✅ Select Date
     */
    private void selectDate(By dateField) {
        try {
            WebElement dateInput = waitForElementClickable(dateField);
            dateInput.click();

            // ✅ Fetch the current date dynamically
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
            String formattedDate = currentDate.format(formatter);

            // ✅ Construct the dynamic XPath for selecting today's date
            String dateXPath = "//button[contains(@aria-label, '" + formattedDate + "')]";
            WebElement dateOption = waitForElementClickable(By.xpath(dateXPath));
            dateOption.click();

            System.out.println("✅ Selected Date: " + formattedDate);
        } catch (Exception e) {
            System.err.println("❌ Failed to select the current date: " + e.getMessage());
        }
    }

    private void selectDropdownAndEnterText(String dropdownXPath, String fieldXPath, String dropdownName, String fieldValue) {
        try {
            // ✅ Click the dropdown to open options
            WebElement dropdown = waitForElementClickable(By.xpath(dropdownXPath));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dropdown);
            dropdown.click();

            // ✅ Select the second option from the dropdown
            WebElement secondOption = waitForElementClickable(By.xpath("(//mat-option)[2]"));
            secondOption.click();
            System.out.println("✅ Selected " + dropdownName);

            // ✅ Wait for the corresponding text field to be visible
            WebElement textField = waitForElementVisible(By.xpath(fieldXPath));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", textField);

            // ✅ Clear and enter the provided text
            textField.clear();
            textField.sendKeys(fieldValue);
            System.out.println("✅ Entered text in " + dropdownName + ": " + fieldValue);

        } catch (Exception e) {
            System.err.println("❌ Failed to select " + dropdownName + " and enter text: " + e.getMessage());
        }
    }

    private WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * ✅ Select a nested dropdown
     */
    private void selectNestedDropdown(String firstDropdownXPath, String secondDropdownXPath, String firstDropdownName, String secondDropdownName) {
        try {
            // ✅ Click the first dropdown
            WebElement firstDropdown = waitForElementClickable(By.xpath(firstDropdownXPath));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", firstDropdown);
            firstDropdown.click();

            // ✅ Select the second option in the first dropdown
            WebElement firstOption = waitForElementClickable(By.xpath("(//mat-option)[1]"));
            firstOption.click();
            System.out.println("✅ Selected first option in " + firstDropdownName);

            // ✅ Wait briefly to ensure the UI updates before selecting the second dropdown
            Thread.sleep(1000);

            // ✅ Click the second dropdown
            WebElement secondDropdown = waitForElementClickable(By.xpath(secondDropdownXPath));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", secondDropdown);
            secondDropdown.click();

            // ✅ Select the second option in the second dropdown
            WebElement secondOption = waitForElementClickable(By.xpath("(//mat-option)[2]"));
            secondOption.click();
            System.out.println("✅ Selected second option in " + secondDropdownName);

        } catch (Exception e) {
            System.err.println("❌ Failed to select nested dropdowns for " + firstDropdownName + " and " + secondDropdownName + ": " + e.getMessage());
        }
    }

    private WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    private void scrollToElement(WebElement element) {
        try {
            jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
            Thread.sleep(500);
        } catch (Exception e) {
            System.err.println("❌ Scroll failed: " + e.getMessage());
        }
    }
}
