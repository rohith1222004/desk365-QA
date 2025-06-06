package d365.ticketCreation;

import d365.utilities.CommonUtilities; // ✅ Import CommonUtilities
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebForm {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;
    private CommonUtilities commonUtilities;
    private static final Logger logger = LoggerFactory.getLogger(WebForm.class);

    // ** Locators **
    private final By contactField = By.xpath("//*[@id='mat-input-0']"); // Contact Email
    private final By subjectField = By.xpath("//*[@id='mat-input-1']"); // Subject
    private final By descriptionField = By.xpath("(//textarea[contains(@id, 'mat-input-')])[1]");
    private final By createButton = By.xpath("/html/body/app-root/app-main-nav/mat-sidenav-container/mat-sidenav-content/div/app-wb-form/div/div/div/div/div[1]/div[2]/button/span[2]/div/span"); // Send Button for Webform
    private final By snackbarMessage = By.xpath("//div[contains(text(),'Ticket created successfully')]");

    // ** Dropdown Locators **
    private final By statusDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-select-value ng-tns')])[1]"); // Status Dropdown
    private final By priorityDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-select-value ng-tns')])[2]"); // Priority Dropdown
    private final By groupDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-select-value ng-tns')])[3]"); // Group Dropdown
    private final By assignToDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-select-value ng-tns')])[4]"); // Assign To Dropdown
    private final By typeDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-select-value ng-tns')])[5]"); // Type Dropdown
    private final By categoryDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-select-value ng-tns')])[6]"); // Category Dropdown
    private final By subCategoryDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-select-value ng-tns')])[7]"); // Subcategory Dropdown

    // ** Text Fields Locators **
    private final By textInputField = By.xpath("//*[@id='mat-input-3']"); // Text Input Field
    private final By paragraphInputField = By.xpath("//*[@id='mat-input-4']"); // Paragraph Input
    private final By numberField = By.xpath("//input[@type='number']"); // Number Field
    private final By dateField = By.xpath("//*[@id=\"mat-input-5\"]"); // Date Picker
    private final By decimalInputField = By.xpath("//*[@id='mat-input-7']"); // Decimal Field

    // ** Constructor **
    public WebForm(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.jsExecutor = (JavascriptExecutor) driver;
        this.commonUtilities = new CommonUtilities();
    }

    // ** Open Webform and Navigate **
    public void openWebform() {
        System.out.println("🌐 Opening Webform to Create Ticket...");

        // ✅ Get Webform URL from CommonUtilities
        String webFormUrl = commonUtilities.getWebFormUrl();

        // ✅ Open Webform in a New Tab
        jsExecutor.executeScript("window.open(arguments[0], '_blank');", webFormUrl);

        // ✅ Switch to the New Tab
        for (String tab : driver.getWindowHandles()) {
            driver.switchTo().window(tab);
        }
        System.out.println("✅ Switched to Webform Tab");

        // ✅ Wait for the Webform to Load
        wait.until(ExpectedConditions.visibilityOfElementLocated(contactField));
        System.out.println("✅ Webform Loaded Successfully");
    }

    // ** Create Valid Ticket in Webform **
    public void createValidTicketFromWebform(String contactEmail, String subject, String description) {
        System.out.println("📌 Filling in Webform Ticket Details...");

        // ✅ Enter Contact Email
        enterTextField(contactField, contactEmail, "Contact Email");

        // ✅ Enter Subject
        enterTextField(subjectField, subject, "Subject");

        // ✅ Enter Description
        enterDescription(description);

        // ✅ Select Dropdown Values
        selectDropdownValue(groupDropdown, "Account");
        selectDropdownValue(assignToDropdown, "vibin-kdesk");
        selectDropdownValue(typeDropdown, "Request");
        selectDropdownValue(categoryDropdown, "Billing");
        selectDropdownValue(subCategoryDropdown, "Extension");

        // ✅ Enter Additional Fields
        enterTextField(textInputField, "Webform Text", "Text Input");
        enterTextField(paragraphInputField, "This is a paragraph input from the Webform.", "Paragraph Input");
        selectDate(dateField);
        enterTextField(numberField, "456", "Number Field");
        enterTextField(decimalInputField, "123.45", "Decimal Field");

        selectDropdownAndEnterText(
                "(//div[contains(@class, 'mat-mdc-select-value ng-tns')])[9]",  // Dropdown XPath
                "//*[@id='mat-input-8']",  // Text Field XPath
                "Dropdown with Sections",
                "SP Custom Field"
        );

        selectNestedDropdown(
                "(//div[contains(@class, 'mat-mdc-select-value ng-tns')])[10]",  // First Dropdown XPath
                "(//div[contains(@class, 'mat-mdc-select-value ng-tns')])[11]",  // Second Dropdown XPath
                "First Nested Dropdown",
                "Second Nested Dropdown"
        );

        // ✅ Click Create Ticket Button
        waitForElementClickable(createButton).click();
        System.out.println("🚀 Clicked 'Create' button for Webform ticket!");

        verifySuccessPage();
        closeWebformTabAndSwitchBack();
    }

    // ** Enter Text Fields **
    private void enterTextField(By field, String value, String fieldName) {
        WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(field));
        inputField.clear();
        inputField.sendKeys(value);
        System.out.println("✅ Entered " + fieldName + ": " + value);
    }

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

    // ** Enter Description (Textarea) **
    private void enterDescription(String description) {
        WebElement descField = wait.until(ExpectedConditions.elementToBeClickable(descriptionField));
        descField.clear();
        descField.sendKeys(description);
        System.out.println("✅ Entered Description: " + description);
    }

    // ** Select Dropdown Option **
    private void selectDropdownValue(By dropdown, String optionText) {
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        dropdownElement.click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '" + optionText + "')]")));
        option.click();
        System.out.println("✅ Selected: " + optionText);
    }

    // ** Wait for Element to be Clickable **
    private WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // ** Verify Success Page After Ticket Creation **
    private void verifySuccessPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(snackbarMessage));
            System.out.println("✅ Ticket created successfully - Success message appeared.");
        } catch (TimeoutException e) {
            System.out.println("❌ Error: Ticket creation failed. Timeout occurred.");
        }
    }

    // ** Close Webform Tab and Switch Back to Original Tab **
    private void closeWebformTabAndSwitchBack() {
        String originalTab = driver.getWindowHandles().iterator().next();
        driver.close();
        System.out.println("✅ Closed Webform Tab.");
        driver.switchTo().window(originalTab);
        System.out.println("✅ Switched back to the original tab.");
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
}
