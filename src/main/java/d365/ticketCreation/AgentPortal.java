package d365.ticketCreation;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AgentPortal {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor jsExecutor;

    private static final int TIMEOUT = 30;
    String filePath = "C:\\Users\\Asus\\Downloads\\Tkt_Lst_All Tickets-w-changes_Created_Time_Last_4_weeks_Mar_05_2025 (1).csv";

    // Locators
    private final By contactField = By.xpath("//mat-chip-grid//input");
    private final By contactDropdown = By.xpath("//div[contains(@class, 'mat-mdc-autocomplete-panel')]//mat-option[1]");
    private final By subjectField = By.cssSelector("input[formcontrolname='subject']");
    private final By descriptionField = By.cssSelector("div.fr-element[contenteditable='true']");
    private final By createButton = By.xpath("//span[contains(text(),'Create')]");

    // Dropdown Locators
    private final By statusDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-form-field-infix ng-tns')])[3]");
    private final By priorityDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-form-field-infix ng-tns')])[4]");
    private final By groupDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-form-field-infix ng-tns')])[5]");
    private final By assignToDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-form-field-infix ng-tns')])[6]");
    private final By typeDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-form-field-infix ng-tns')])[7]");
    private final By categoryDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-form-field-infix ng-tns')])[8]");
    private final By subCategoryDropdown = By.xpath("(//div[contains(@class, 'mat-mdc-form-field-infix ng-tns')])[9]");
    private final By dropdownField = By.xpath("(//div[contains(@class, 'mat-mdc-form-field-infix ng-tns')])[10]");

    // Text Fields
    private final By textField = By.xpath("//*[@id=\"mat-input-1\"]");
    private final By numField = By.xpath("//*[@id='mat-input-4']");
    private final By paragraphField = By.cssSelector("textarea[matinput]");
    private final By decimalField = By.xpath("//*[@id=\"mat-input-5\"]");

    // Date Picker
    private final By dateField = By.cssSelector("input[matinput][aria-haspopup='dialog']");

    public AgentPortal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        this.actions = new Actions(driver);
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void navigateToTicketPage() {
        System.out.println("📌 Navigating to Ticket Creation Page...");
        WebElement newButton = waitForElementClickable(By.xpath("//button[.//span[text()='New']]"));
        newButton.click();
        WebElement newTicketOption = waitForElementClickable(
                By.xpath("//div[contains(@class,'mat-mdc-menu-content')]//button[1]"));
        newTicketOption.click();
        // ✅ Ensure the ticket creation form loads
        wait.until(ExpectedConditions.visibilityOfElementLocated(subjectField));
        System.out.println("✅ Ticket Creation Page Loaded.");
    }

    public void createValidTicket(String contact, String subject, String description) {
        System.out.println("📌 Filling in Ticket Details...");

        enterContact(contact);
        enterTextField(subjectField, subject, "Subject");
        enterDescription(description);
        // uploadAttachment(filePath); // Upload file attachment after description
        // enterContact(contact);

        // Updated Dropdown Selections
        selectDropdownOption(statusDropdown, "Pending", "Status");
        selectDropdownOption(priorityDropdown, "High", "Priority");
        selectDropdownOption(groupDropdown, "Account", "Group");
        selectDropdownOption(assignToDropdown, "vibin-kdesk", "Assign To");
        selectDropdownOption(typeDropdown, "Incident", "Type");
        selectDropdownOption(categoryDropdown, "Billing", "Category");
        selectDropdownOption(subCategoryDropdown, "Extension", "Subcategory");
        selectDropdownOption(dropdownField, "Dropdown Choice 1", "Dropdown Field");

        enterTextField(textField, "MSD", "Text Input");
        enterTextField(paragraphField, "This is a long paragraph input test.", "Paragraph");
        selectDate();

        enterTextField(numField, "789", "Number Field");
        enterTextField(decimalField, "123.45", "Decimal Field");

        selectDropdownAndEnterText(
                "(//div[contains(@class, 'mat-mdc-form-field-infix ng-tns')])[16]", // Dropdown XPath
                "//*[@id=\"mat-input-7\"]", // Text Field XPath
                "Dropdown with Sections",
                "Custom Field");

        // ✅ Selecting Nested Dropdowns
        selectNestedDropdown(
                "(//div[contains(@class, 'mat-mdc-form-field-infix ng-tns')])[18]",
                "(//div[contains(@class, 'mat-mdc-form-field-infix ng-tns')])[19]",
                "Nested Dropdown", "Level 1 Dropdown");

        clickCreateButton();
    }

    public void clickCreateButton() {
        try {
            WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "/html/body/app-root/app-main-nav/mat-sidenav-container/mat-sidenav-content/div/app-createticket/div/div/div/div/div/div[2]/div/div[1]/app-button-spinner/button/span[2]/div/span[2]")));

            // Scroll to the button to ensure visibility
            scrollToElement(createButton);

            // Click the button using JavaScript if needed
            try {
                createButton.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("⚠️ Click intercepted, using JavaScript to click...");
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].click();", createButton);
            }

            System.out.println("🚀 Successfully clicked the 'Create' button!");

        } catch (Exception e) {
            System.err.println("❌ Failed to click the 'Create' button: " + e.getMessage());
            throw new RuntimeException("Clicking the Create button failed", e);
        }
    }

    private void enterContact(String contact) {
        WebElement contactInput = waitForElementClickable(contactField);
        contactInput.sendKeys(contact.substring(0, 3));
        WebElement firstContact = waitForElementClickable(contactDropdown);
        firstContact.click();
    }

    private void enterTextField(By field, String value, String fieldName) {
        WebElement inputField = waitForElementClickable(field);
        scrollToElement(inputField);
        inputField.clear();
        inputField.sendKeys(value);
        System.out.println("✅ Entered " + fieldName + ": " + value);
    }

    private void enterDescription(String description) {
        try {
            WebElement descField = waitForElementClickable(descriptionField);
            scrollToElement(descField);
            descField.click();
            Thread.sleep(500);
            jsExecutor.executeScript("arguments[0].innerHTML = arguments[1];", descField, description);
            System.out.println("✅ Entered Description: " + description);
            actions.moveByOffset(10, 10).click().perform();
        } catch (Exception e) {
            System.err.println("❌ Failed to enter description: " + e.getMessage());
        }
    }

    private void selectDropdownOption(By dropdown, String optionText, String dropdownName) {
        try {
            waitForOverlayToDisappear();
            WebElement dropdownElement = waitForElementClickable(dropdown);
            scrollToElement(dropdownElement);
            dropdownElement.click();

            WebElement option = waitForElementClickable(By.xpath("//span[contains(text(), '" + optionText + "')]"));
            option.click();
            System.out.println("✅ Selected " + dropdownName + ": " + optionText);
        } catch (Exception e) {
            System.err.println("❌ Failed to select " + dropdownName + ": " + e.getMessage());
        }
    }

    private void selectDropdownAndEnterText(String dropdownXPath, String fieldXPath, String dropdownName,
            String fieldValue) {
        try {
            WebElement dropdown = waitForElementClickable(By.xpath(dropdownXPath));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dropdown);
            dropdown.click();

            WebElement secondOption = waitForElementClickable(By.xpath("(//mat-option)[2]"));
            secondOption.click();
            System.out.println("✅ Selected " + dropdownName);

            WebElement textField = waitForElementVisible(By.xpath(fieldXPath));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", textField);
            textField.clear();
            textField.sendKeys(fieldValue);
            System.out.println("✅ Entered text in " + dropdownName + ": " + fieldValue);
        } catch (Exception e) {
            System.err.println("❌ Failed to select " + dropdownName + " and enter text: " + e.getMessage());
        }
    }

    private void selectNestedDropdown(String firstDropdownXPath, String secondDropdownXPath, String firstDropdownName,
            String secondDropdownName) {
        try {
            WebElement firstDropdown = waitForElementClickable(By.xpath(firstDropdownXPath));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", firstDropdown);
            firstDropdown.click();
            WebElement firstOption = waitForElementClickable(By.xpath("(//mat-option)[1]"));
            firstOption.click();
            System.out.println("✅ Selected first option for " + firstDropdownName);

            Thread.sleep(2000);

            WebElement secondDropdown = waitForElementClickable(By.xpath(secondDropdownXPath));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", secondDropdown);
            secondDropdown.click();
            WebElement secondOption = waitForElementClickable(By.xpath("(//mat-option)[2]"));
            secondOption.click();
            System.out.println("✅ Selected second option for " + secondDropdownName);

        } catch (Exception e) {
            System.err.println("❌ Failed to select nested dropdowns: " + e.getMessage());
        }
    }

    private void selectDate() {
        try {
            WebElement dateInput = waitForElementClickable(dateField);
            scrollToElement(dateInput);
            dateInput.click();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
            String formattedDate = currentDate.format(formatter);
            WebElement dateOption = waitForElementClickable(
                    By.xpath("//button[contains(@aria-label, '" + formattedDate + "')]"));
            dateOption.click();
            System.out.println("✅ Selected Date: " + formattedDate);
        } catch (Exception e) {
            System.err.println("❌ Failed to select the current date: " + e.getMessage());
        }
    }

    private void uploadAttachment(String filePath) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            waitForOverlayToDisappear();
            WebElement attachmentIcon = wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath("(//mat-icon[contains(@class, 'mat-icon')])[11]")));
            scrollToElement(attachmentIcon);

            try {
                attachmentIcon.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("⚠️ Click intercepted, using JavaScript to click...");
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].click();", attachmentIcon);
            }

            System.out.println("✅ Clicked the attachment icon.");
            WebElement uploadFileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("uploadFile")));
            uploadFileInput.sendKeys(filePath);
            System.out.println("📂 ✅ File uploaded successfully: " + filePath);

        } catch (Exception e) {
            System.err.println("❌ Error uploading file: " + e.getMessage());
            throw new RuntimeException("File upload failed", e);
        }
    }

    // private void waitForOverlayToDisappear() {
    // try {
    // By overlayLocator = By.className("cdk-overlay-backdrop");
    // wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
    // System.out.println("✅ Overlay disappeared, proceeding...");
    // } catch (Exception e) {
    // System.out.println("ℹ️ No overlay detected, proceeding...");
    // }
    // }

    private void waitForOverlayToDisappear() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Increase timeout if needed

            // Wait for both snackbar and any other overlay/modal to disappear
            wait.until(ExpectedConditions
                    .invisibilityOfElementLocated(By.cssSelector("div.mat-mdc-snack-bar-label.mdc-snackbar__label")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.cdk-overlay-backdrop")));

            // Wait for overlays to not be interactable anymore (optional)
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.mat-mdc-snack-bar-label")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.cdk-overlay-backdrop")));

            System.out.println("✅ Overlay disappeared, proceeding...");
        } catch (TimeoutException e) {
            System.out.println("❌ Overlay didn't disappear within the time frame, proceeding anyway...");
        } catch (Exception e) {
            System.out.println("ℹ️ No overlay detected, proceeding...");
        }
    }

    private void scrollToElement(WebElement element) {
        try {
            jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
            Thread.sleep(500);
        } catch (Exception e) {
            System.err.println("❌ Scroll failed: " + e.getMessage());
        }
    }

    private WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
