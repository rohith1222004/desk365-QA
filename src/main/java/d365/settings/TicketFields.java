package d365.settings;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import d365.utilities.CommonUtilities; // ✅ Import CommonUtilities
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketFields {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;
    protected CommonUtilities commonUtilities;
    private static final Logger logger = LoggerFactory.getLogger(TicketFields.class);

    public TicketFields(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    // ** Navigate to Ticket Fields Page **
    public void addTicketFields() throws InterruptedException {
        System.out.println("📌 Navigating to Ticket Fields Page...");
        logger.info("📌 Navigating to Ticket Fields Page...");
        commonUtilities = new CommonUtilities();

        // ✅ Click Settings Icon
//        WebElement settingsIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-icon[text()='settings']")));
//        settingsIcon.click();
//        System.out.println("✅ Clicked Settings Icon.");
//
//        // ✅ Wait for Settings Page to Load
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Settings')]")));
//        System.out.println("✅ Settings Page Loaded.");
//
//        // ✅ Click 'Ticket Fields' Menu
//        WebElement ticketFieldsMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@routerlink, 'ticket-fields')]")));
//        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", ticketFieldsMenu);
//        ticketFieldsMenu.click();
//        System.out.println("✅ Clicked on 'Ticket Fields'.");

        // ✅ Ensure Ticket Fields Page Loads
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(text(), 'Ticket Fields')])[2]")));
//        System.out.println("✅ Navigated to Ticket Fields Page!");

        // ✅ Add Fields Step-by-Step
        addDropdownField();
        addTextField();
        addParagraphField();
//        addReadOnlyField();
        addCheckBox();
        addDateField();
        addNumberField();
        addDecimalField();
        addAttachmentsField();
        addDropdownWithSections();
        addSectionToDropdown();
        addFieldInsideSection();
        addNestedDropdown();
        Thread.sleep(2000);

        System.out.println("✅ All Ticket Fields Added Successfully!");
        logger.info("✅ All Ticket Fields Added Successfully!");
    }

    // ** Add Dropdown Field **
    private void addDropdownField() {
        System.out.println("🛠 Adding Dropdown Field...");
        logger.info("🛠 Adding Dropdown Field...");

        // Step 1: Click on the "Dropdown" Field in the Ticket Fields List
        WebElement dropdownField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//mat-icon[@qa-test='button-addField'])[1]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dropdownField);
        dropdownField.click();
        System.out.println("✅ Clicked on 'Dropdown' Field");
        logger.info("✅ Clicked on 'Dropdown' Field");

        // Step 2: Enter the Dropdown Name
        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@qa-test='input-Fields'])[1]")));
        nameField.sendKeys("Dropdown Field");
        System.out.println("✅ Entered Dropdown Name: Dropdown Field");
        logger.info("✅ Entered Dropdown Name: Dropdown Field");

        // Step 3: Enter First Option
        WebElement firstOptionField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@qa-test='input-ddChoice'])[1]")));
        firstOptionField.sendKeys("Dropdown Choice 1");
        System.out.println("✅ Entered First Option: Dropdown Choice 1");
        logger.info("✅ Entered First Option: Dropdown Choice 1");

        // Step 4: Click "Add Choice" Button
        // ✅ Locate and Click the "Add Choice" Button
        try {
            WebElement addChoiceButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@qa-test='addChoice']")));

            // Ensure it's in the viewport
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", addChoiceButton);

            // Try clicking using Selenium's normal click method
            addChoiceButton.click();
            System.out.println("✅ Clicked 'Add Choice' Button using Selenium click.");
            logger.info("✅ Clicked 'Add Choice' Button using Selenium click.");
        } catch (ElementClickInterceptedException e) {
            System.out.println("⚠️ Normal click failed. Trying JavaScript click...");
            logger.warn("⚠️ Normal click failed. Trying JavaScript click...");

            WebElement addChoiceButtonFallback = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@qa-test='addChoice']")));

            // Force click using JavaScript
            jsExecutor.executeScript("arguments[0].click();", addChoiceButtonFallback);
            System.out.println("✅ Clicked 'Add Choice' Button using JavaScript.");
            logger.info("✅ Clicked 'Add Choice' Button using JavaScript.");
        }

        // Step 5: Enter Second Option
        WebElement secondOptionField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@qa-test='input-ddChoice'])[2]")));
        secondOptionField.sendKeys("Dropdown Choice 2");
        System.out.println("✅ Entered Second Option: Dropdown Choice 2");
        logger.info("✅ Entered Second Option: Dropdown Choice 2");

        // Step 6: Click Save Button for Dropdown
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@qa-test='button-saveField'])[11]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", saveButton);
        saveButton.click();
        System.out.println("✅ Clicked 'Save' Button for Dropdown");
        logger.info("✅ Clicked 'Save' Button for Dropdown");
    }

    // ** Add Text Field **
    private void addTextField() {
        System.out.println("🛠 Adding Text Input Field...");
        logger.info("🛠 Adding Text Input Field...");

        // ✅ Locate and Click the "Add" button for Text Input field in the left panel
        WebElement textFieldAddButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//mat-icon[@qa-test='button-addField'])[2]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", textFieldAddButton);
        textFieldAddButton.click();
        System.out.println("✅ Clicked on 'Add' Button for Text Input Field");
        logger.info("✅ Clicked on 'Add' Button for Text Input Field");

        // ✅ Wait for the form to be visible
        WebElement textInputField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@qa-test='input-Fields'])[2]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", textInputField);
        textInputField.clear();
        textInputField.sendKeys("Text Input");
        System.out.println("✅ Entered Text Input Field Name");
        logger.info("✅ Entered Text Input Field Name");

        // ✅ Click the Save Button for Text Input
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@qa-test='button-saveField'])[12]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", saveButton);
        saveButton.click();
        System.out.println("✅ Clicked 'Save' Button for Text Input");
        logger.info("✅ Clicked 'Save' Button for Text Input");
    }

    // ** Add Paragraph Field **
    private void addParagraphField() {
        System.out.println("🛠 Adding Paragraph Field...");
        logger.info("🛠 Adding Paragraph Field...");

        // ✅ Locate and Click the "Add" button for Paragraph field in the left panel
        WebElement paragraphFieldAddButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//mat-icon[@qa-test='button-addField'])[3]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", paragraphFieldAddButton);
        paragraphFieldAddButton.click();
        System.out.println("✅ Clicked on 'Add' Button for Paragraph Field");
        logger.info("✅ Clicked on 'Add' Button for Paragraph Field");

        // ✅ Wait for the input field to be visible
        WebElement paragraphInputField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[@qa-test='input-Fields'])[3]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", paragraphInputField);
        paragraphInputField.clear();
        paragraphInputField.sendKeys("Paragraph Input");
        System.out.println("✅ Entered Paragraph Input Field Name");
        logger.info("✅ Entered Paragraph Input Field Name");

        WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//button[@qa-test='button-saveField'])[13]//span//span[contains(text(), 'Save')]")));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        jsExecutor.executeScript("arguments[0].click();", saveButton);
        System.out.println("✅ Clicked 'Save' Button for Para Input");
        logger.info("✅ Clicked 'Save' Button for Para Input");
//
//        // ✅ Scroll to the top after saving
//        jsExecutor.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
//        System.out.println("⬆️ Scrolled to the top.");
//        logger.info("⬆️ Scrolled to the top.");
    }

//    private void addReadOnlyField() {
//        System.out.println("🛠 Adding Checkbox Field...");
//        logger.info("🛠 Adding Checkbox Field...");
//
//        WebElement readOnlyFieldAddButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("(//mat-icon[@qa-test='button-addField'])[4]")));
//        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", readOnlyFieldAddButton);
//        readOnlyFieldAddButton.click();
//        System.out.println("✅ Clicked on 'Add' Button for Checkbox Field");
//        logger.info("✅ Clicked on 'Add' Button for Checkbox Field");
//
//        WebElement readOnlyFieldInputField = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//input[@qa-test='input-readonlyField']")));
//        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", readOnlyFieldInputField);
//        readOnlyFieldInputField.clear();
//        readOnlyFieldInputField.sendKeys("Read Only Field");
//        System.out.println("✅ Entered Checkbox Field Name");
//        logger.info("✅ Entered Checkbox Field Name");
//
//        WebElement readOnlyDescField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.fr-element[contenteditable='true']")));
//        readOnlyDescField.click();
//        jsExecutor.executeScript("arguments[0].innerHTML = arguments[1];", readOnlyDescField, "ReadOnly Description Field");
//        System.out.println("✅ Entered Description: " + "ReadOnly Description Field");
//        actions.moveByOffset(10, 10).click().perform();
//    }

    // ** Add Checkbox Field **
    private void addCheckBox() {
        System.out.println("🛠 Adding Checkbox Field...");
        logger.info("🛠 Adding Checkbox Field...");

        WebElement checkboxFieldAddButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//mat-icon[@qa-test='button-addField'])[5]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", checkboxFieldAddButton);
        checkboxFieldAddButton.click();
        System.out.println("✅ Clicked on 'Add' Button for Checkbox Field");
        logger.info("✅ Clicked on 'Add' Button for Checkbox Field");

        WebElement checkboxInputField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[@qa-test='input-Fields'])[4]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", checkboxInputField);
        checkboxInputField.clear();
        checkboxInputField.sendKeys("Checkbox Field");
        System.out.println("✅ Entered Checkbox Field Name");
        logger.info("✅ Entered Checkbox Field Name");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[@qa-test='button-saveField'])[14]")));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        jsExecutor.executeScript("arguments[0].click();", saveButton);

//        jsExecutor.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
//        System.out.println("⬆️ Scrolled to the top.");
//        logger.info("⬆️ Scrolled to the top.");
    }

    // ** Add Date Field **
    private void addDateField() {
        System.out.println("🛠 Adding Date Field...");
        logger.info("🛠 Adding Date Field...");

        WebElement dateFieldAddButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//mat-icon[@qa-test='button-addField'])[6]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dateFieldAddButton);
        dateFieldAddButton.click();
        System.out.println("✅ Clicked on 'Add' Button for Date Field");
        logger.info("✅ Clicked on 'Add' Button for Date Field");

        WebElement dateInputField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[@qa-test='input-Fields'])[5]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dateInputField);
        dateInputField.clear();
        dateInputField.sendKeys("Date Field");
        System.out.println("✅ Entered Date Field Name");
        logger.info("✅ Entered Date Field Name");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[@qa-test='button-saveField'])[15]")));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        jsExecutor.executeScript("arguments[0].click();", saveButton);

//        jsExecutor.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
//        System.out.println("⬆️ Scrolled to the top.");
//        logger.info("⬆️ Scrolled to the top.");
    }

    // ** Add Number Field **
    private void addNumberField() {
        System.out.println("🛠 Adding Number Field...");
        logger.info("🛠 Adding Number Field...");

        // ✅ Locate and Click the "Add" button for Number field in the left panel
        WebElement numberFieldAddButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//mat-icon[@qa-test='button-addField'])[7]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", numberFieldAddButton);
        numberFieldAddButton.click();
        System.out.println("✅ Clicked on 'Add' Button for Number Field");
        logger.info("✅ Clicked on 'Add' Button for Number Field");

        // ✅ Wait for the form to be visible
        WebElement numberInputField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[@qa-test='input-Fields'])[6]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", numberInputField);
        numberInputField.clear();
        numberInputField.sendKeys("Number Field");
        System.out.println("✅ Entered Number Field Name");
        logger.info("✅ Entered Number Field Name");

        // ✅ Scroll to the bottom before clicking Save
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[@qa-test='button-saveField'])[16]")));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        jsExecutor.executeScript("arguments[0].click();", saveButton);

//        // ✅ Scroll to the top after saving
//        jsExecutor.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
//        System.out.println("⬆️ Scrolled to the top.");
//        logger.info("⬆️ Scrolled to the top.");
    }

    // ** Add Decimal Field **
    private void addDecimalField() {
        System.out.println("🛠 Adding Decimal Field...");
        logger.info("🛠 Adding Decimal Field...");

        // ✅ Locate and Click the "Add" button for Decimal field in the left panel
        WebElement decimalFieldAddButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//mat-icon[@qa-test='button-addField'])[8]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", decimalFieldAddButton);
        decimalFieldAddButton.click();
        System.out.println("✅ Clicked on 'Add' Button for Decimal Field");
        logger.info("✅ Clicked on 'Add' Button for Decimal Field");

        // ✅ Wait for the form to be visible
        WebElement decimalInputField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[@qa-test='input-Fields'])[7]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", decimalInputField);
        decimalInputField.clear();
        decimalInputField.sendKeys("Decimal Field");
        System.out.println("✅ Entered Decimal Field Name");
        logger.info("✅ Entered Decimal Field Name");

        // ✅ Scroll to the bottom before clicking Save
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[@qa-test='button-saveField'])[17]")));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        jsExecutor.executeScript("arguments[0].click();", saveButton);

//        // ✅ Scroll to the top after saving
//        jsExecutor.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
//        System.out.println("⬆️ Scrolled to the top.");
//        logger.info("⬆️ Scrolled to the top.");
    }

//    public void clickSaveButton() {
//        try {
//            System.out.println("🔍 Looking for the Save button...");
//
//            // **Ensure the expansion panel is fully scrolled before clicking Save**
//            WebElement expansionPanel = driver.findElement(By.xpath("//*[@id='cdk-accordion-child-10']"));
//            jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'end'});", expansionPanel);
//            Thread.sleep(500); // Let UI adjust
//
//            // **Find the Save button inside the expansion panel**
//            WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
//                    By.xpath("//*[@id='cdk-accordion-child-10']/mat-action-row/button[1]")
//            ));
//
//            // **Ensure the Save button is in view before clicking**
//            jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveButton);
//            Thread.sleep(500);
//
//            // **Wait until the button is clickable**
//            wait.until(ExpectedConditions.elementToBeClickable(saveButton));
//
//            // **Try clicking normally**
//            saveButton.click();
//            System.out.println("✅ Successfully clicked the 'Save' button.");
//
//            // ✅ **Wait for the save process to complete**
//            Thread.sleep(1000);  // Adjust as needed for UI updates
//
//            // ✅ **Scroll back to the top of the page**
//            System.out.println("⬆️ Scrolling back to the top...");
//            jsExecutor.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
//            Thread.sleep(500);  // Allow time for scrolling animation
//
//            System.out.println("✅ Successfully scrolled to the top.");
//
//        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
//            System.out.println("⚠️ Save button click intercepted. Trying JavaScript click...");
//
//            // **Find button again in case it changed**
//            WebElement saveButtonFallback = wait.until(ExpectedConditions.presenceOfElementLocated(
//                    By.xpath("//*[@id='cdk-accordion-child-10']/mat-action-row/button[1]")
//            ));
//
//            // **Click using JavaScript as a fallback**
//            jsExecutor.executeScript("arguments[0].click();", saveButtonFallback);
//            System.out.println("✅ Successfully clicked the 'Save' button using JavaScript.");
//
//            // ✅ **Scroll to the top after clicking**
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//            System.out.println("⬆️ Scrolling back to the top...");
//            jsExecutor.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//            System.out.println("✅ Successfully scrolled to the top.");
//
//        } catch (TimeoutException e) {
//            System.out.println("❌ Error: Save button not found within timeout. Possible reasons:");
//            System.out.println("   1️⃣ The button is inside an iframe.");
//            System.out.println("   2️⃣ The button is not in the DOM at the time of search.");
//            System.out.println("   3️⃣ The button is not enabled yet.");
//        } catch (Exception e) {
//            System.out.println("❌ Unexpected error while clicking Save: " + e.getMessage());
//        }
//    }

    //    public void revertChanges() {
//        System.out.println("🔄 Reverting all added fields...");
//
//        for (int i = 16; i >= 10; i--) {  // Deleting fields from last to first
//            try {
//                String deleteButtonXPath = "//*[@id='mat-expansion-panel-header-" + i + "']/span/div/div[2]/div/button";
//
//                // ✅ Wait for and Click the Delete Button
//                WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(deleteButtonXPath)));
//                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", deleteButton);
//                deleteButton.click();
//                System.out.println("🗑 Clicked 'Delete' for Field " + i);
//
//                // ✅ Wait for and Click the Confirmation Delete Button in the Popup
//                try {
//                    WebElement confirmDeleteButton = wait.until(ExpectedConditions.presenceOfElementLocated(
//                            By.xpath("//app-confirm-dialog-with-spinner//button[1][contains(.,'Delete')]")));
//                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", confirmDeleteButton);
//                    confirmDeleteButton.click();
//                    System.out.println("✅ Confirmed Deletion for Field " + i);
//                } catch (TimeoutException e) {
//                    System.out.println("⚠️ No confirmation popup found for Field " + i);
//                }
//
//                // ✅ Allow UI to update before deleting the next field
//                Thread.sleep(1000);
//
//            } catch (TimeoutException e) {
//                System.out.println("⚠️ No more fields to delete at index " + i);
//            } catch (Exception e) {
//                System.out.println("❌ Error deleting field at index " + i + ": " + e.getMessage());
//            }
//        }
//
//        System.out.println("✅ All fields deleted successfully!");
//    }
//


    // ** Add Attachment Field **
    private void addAttachmentsField() {
        System.out.println("🛠 Adding Attachments Field...");
        logger.info("🛠 Adding Attachments Field...");

        // ✅ Locate and Click the "Add" button for Attachments field in the left panel
        WebElement attachmentsFieldAddButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//mat-icon[@qa-test='button-addField'])[9]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", attachmentsFieldAddButton);
        attachmentsFieldAddButton.click();
        System.out.println("✅ Clicked on 'Add' Button for Attachments Field");
        logger.info("✅ Clicked on 'Add' Button for Attachments Field");

        // ✅ Wait for the input field to be visible
        WebElement attachmentsInputField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@qa-test='input-attachmentField']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", attachmentsInputField);
        attachmentsInputField.clear();
        attachmentsInputField.sendKeys("Attachments Field");
        System.out.println("✅ Entered Attachments Field Name");
        logger.info("✅ Entered Attachments Field Name");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[@qa-test='button-saveField'])[18]")));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        jsExecutor.executeScript("arguments[0].click();", saveButton);



//        // ✅ Scroll to the top after saving
//        jsExecutor.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
//        System.out.println("⬆️ Scrolled to the top.");
//        logger.info("⬆️ Scrolled to the top.");
    }

    // ** Add Dropdown with Sections **
    private void addDropdownWithSections() {
        System.out.println("🛠 Adding Dropdown with Sections...");
        logger.info("🛠 Adding Dropdown with Sections...");

        WebElement dropdownWithSectionsButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='cdk-drop-list-0']/div[10]/div/div/div[2]/mat-icon")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", dropdownWithSectionsButton);
        dropdownWithSectionsButton.click();
        System.out.println("✅ Clicked on 'Add' Button for Dropdown with Sections");
        logger.info("✅ Clicked on 'Add' Button for Dropdown with Sections");

        WebElement dropdownInputField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-input-43']")));
        dropdownInputField.clear();
        dropdownInputField.sendKeys("Dropdown with Sections");
        System.out.println("✅ Entered Dropdown Name");
        logger.info("✅ Entered Dropdown Name");

        // ✅ Add Choices
        WebElement firstChoiceField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-input-45']")));
        firstChoiceField.sendKeys("Choice 1");
        System.out.println("✅ Entered First Choice");
        logger.info("✅ Entered First Choice");

        WebElement addChoiceButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='cdk-accordion-child-18']/div/div/div[3]/div[3]/a"))); // Correct Add Section Button
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", addChoiceButton);
        addChoiceButton.click();
        System.out.println("✅ Clicked 'Add Section' Button");
        logger.info("✅ Clicked 'Add Section' Button");

        WebElement secondChoiceField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-input-46']")));
        secondChoiceField.sendKeys("Choice 2");
        System.out.println("✅ Entered Second Choice");
        logger.info("✅ Entered Second Choice");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[@qa-test='button-saveField'])[19]")));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        jsExecutor.executeScript("arguments[0].click();", saveButton);

//        // ✅ Scroll to the top after saving
//        jsExecutor.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
//        System.out.println("⬆️ Scrolled to the top.");
//        logger.info("⬆️ Scrolled to the top.");
    }

    // ** Add Section to Dropdown **
    private void addSectionToDropdown() {
        System.out.println("🛠 Adding Section to Dropdown...");
        logger.info("🛠 Adding Section to Dropdown...");

        WebElement addSectionButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-expansion-panel-header-18']/span/div/div[2]/button")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", addSectionButton);
        addSectionButton.click();
        System.out.println("✅ Clicked 'Add Section' Button");
        logger.info("✅ Clicked 'Add Section' Button");

        // ** Popup Handling **
        WebElement sectionTitleField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-input-47']")));
        sectionTitleField.sendKeys("New Section");
        System.out.println("✅ Entered Section Title");
        logger.info("✅ Entered Section Title");

        WebElement selectOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-mdc-chip-list-input-0']")));
        selectOption.click();

        // Select second option
        WebElement secondOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option[2]")));
        secondOption.click();
        System.out.println("✅ Selected Option");
        logger.info("✅ Selected Option");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // ✅ Click Save in Popup
        clickSaveButton("//app-ticket-fields-section-dialog//div[contains(@class,'main-footer-container')]//button[.//span[text()='Save']]", "Dropdown Section");
    }

    // ** Add Field Inside Section **
    private void addFieldInsideSection() {
        System.out.println("🛠 Adding Field Inside Section...");
        logger.info("🛠 Adding Field Inside Section...");

        WebElement sectionDiv = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='cdk-drop-list-1']/mat-accordion/div[21]/div")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", sectionDiv);

        WebElement addFieldButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-main-nav/mat-sidenav-container/mat-sidenav-content/div/app-settings/div/div[2]/app-ticket-fields/div/div[3]/div[2]/mat-accordion/div[21]/div/div[2]/div/div/div[1]/div[2]/button/span[2]")));
        addFieldButton.click();
        System.out.println("✅ Clicked 'Add Field' Button");
        logger.info("✅ Clicked 'Add Field' Button");

        // ** Popup Handling **
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[4]/div[2]/div/mat-dialog-container/div/div/app-add-section-ticket-fields-dialog/div/div[2]/mat-form-field/div[1]/div/div[2]")));
        dropdown.click();
        System.out.println("✅ Opened Dropdown");
        logger.info("✅ Opened Dropdown");

        WebElement secondOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"mat-option-8\"]/span")));
        secondOption.click();
        System.out.println("✅ Selected Second Option");
        logger.info("✅ Selected Second Option");

        // ✅ Click Add in Popup
        clickSaveButton("/html/body/div[4]/div[2]/div/mat-dialog-container/div/div/app-add-section-ticket-fields-dialog/div/div[3]/button[1]/span[2]", "Field inside Section");

        // ✅ Enter Field Name
        WebElement fieldInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-input-49']")));
        fieldInput.sendKeys("Custom Field");
        System.out.println("✅ Entered Field Name");
        logger.info("✅ Entered Field Name");

        // ✅ Click Save
        clickSaveButton("/html/body/app-root/app-main-nav/mat-sidenav-container/mat-sidenav-content/div/app-settings/div/div[2]/app-ticket-fields/div/div[3]/div[2]/mat-accordion/div[21]/div/div[2]/div/div/div[2]/mat-accordion/div/mat-expansion-panel/div/mat-action-row/button[1]", "Custom Field inside Section");
    }

    private void clickSaveButton(String saveButtonXPath, String fieldType) {
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(saveButtonXPath)));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", saveButton);

        try {
            saveButton.click();
            System.out.println("✅ Clicked 'Save' Button for " + fieldType + ".");
            logger.info("✅ Clicked 'Save' Button for " + fieldType + ".");
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            System.out.println("⚠️ Save button click intercepted. Trying JavaScript click...");
            logger.warn("⚠️ Save button click intercepted. Trying JavaScript click...");

            jsExecutor.executeScript("arguments[0].click();", saveButton);
            System.out.println("✅ Clicked 'Save' Button for " + fieldType + " using JavaScript.");
            logger.info("✅ Clicked 'Save' Button for " + fieldType + " using JavaScript.");
        }

        // ✅ Scroll to the top after saving
        jsExecutor.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
        System.out.println("⬆️ Scrolled to the top.");
        logger.info("⬆️ Scrolled to the top.");
    }

    // ** Add Nested Dropdown with Sections **
    private void addNestedDropdown() throws InterruptedException {
        System.out.println("🛠 Adding Nested Dropdown with Sections...");
        logger.info("🛠 Adding Nested Dropdown with Sections...");

        // ✅ Click the "Add" button for Nested Dropdown in the left panel
        WebElement nestedDropdownAddButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='cdk-drop-list-0']/div[11]/div/div/div[2]/mat-icon")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", nestedDropdownAddButton);
        nestedDropdownAddButton.click();
        System.out.println("✅ Clicked on 'Add' Button for Nested Dropdown with Sections");
        logger.info("✅ Clicked on 'Add' Button for Nested Dropdown with Sections");

        // ✅ Wait for the input field and enter dropdown name
        WebElement dropdownInputField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-input-51']")));
        dropdownInputField.clear();
        dropdownInputField.sendKeys("Nested Dropdown");
        System.out.println("✅ Entered Nested Dropdown Name");
        logger.info("✅ Entered Nested Dropdown Name");
        Thread.sleep(1000);

        // ✅ Click "Add Choices" button
        WebElement addChoicesButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"cdk-accordion-child-20\"]/div/div/div/div[2]/button")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", addChoicesButton);
        addChoicesButton.click();
        System.out.println("✅ Clicked 'Add Choices' Button for Nested Dropdown");
        logger.info("✅ Clicked 'Add Choices' Button for Nested Dropdown");
        Thread.sleep(1000);

        // ✅ Handle Popup - Add First Choice
        WebElement firstChoiceField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-input-53']")));
        firstChoiceField.sendKeys("Choice 1");
        System.out.println("✅ Entered First Choice");
        logger.info("✅ Entered First Choice");
        Thread.sleep(1000);

        // ✅ Click "Add Choice" Button
        WebElement addChoiceButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[4]/div[2]/div/mat-dialog-container/div/div/app-add-another-level-dropdown-dialog/div/div[2]/div[3]/a")));
        addChoiceButton.click();
        System.out.println("✅ Clicked 'Add Choice' Button");
        logger.info("✅ Clicked 'Add Choice' Button");
        Thread.sleep(1000);

        // ✅ Enter Second Choice
        WebElement secondChoiceField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-input-54']")));
        secondChoiceField.sendKeys("Choice 2");
        System.out.println("✅ Entered Second Choice");
        logger.info("✅ Entered Second Choice");
        Thread.sleep(1000);

        // ✅ Click "Save" Button in Popup
        WebElement savePopupButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[4]/div[2]/div/mat-dialog-container/div/div/app-add-another-level-dropdown-dialog/div/div[3]/button[1]")));
        Thread.sleep(2000);
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", savePopupButton);
        savePopupButton.click();
        System.out.println("✅ Clicked 'Save' Button for Nested Dropdown Choices");
        logger.info("✅ Clicked 'Save' Button for Nested Dropdown Choices");
        Thread.sleep(1000);

        // ✅ Scroll and Click "Add Level" Button
        WebElement addSectionButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-main-nav/mat-sidenav-container/mat-sidenav-content/div/app-settings/div/div[2]/app-ticket-fields/div/div[3]/div[2]/mat-accordion/div[22]/mat-expansion-panel/div/div/div/div/div/div[2]/div[2]/button")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", addSectionButton);
        WebElement addLevelButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-main-nav/mat-sidenav-container/mat-sidenav-content/div/app-settings/div/div[2]/app-ticket-fields/div/div[3]/div[2]/mat-accordion/div[22]/mat-expansion-panel/div/div/div/div/div/div[2]/div[2]/button")));
        addLevelButton.click();
        System.out.println("✅ Clicked 'Add Level' Button");
        logger.info("✅ Clicked 'Add Level' Button");
        Thread.sleep(1000);

        // ✅ Handle Popup - Enter Level Inputs
        WebElement levelInput1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-input-55']")));
        levelInput1.sendKeys("Level 1");
        Thread.sleep(1000);

        WebElement levelInput2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-input-56']")));
        levelInput2.sendKeys("Additional Choice1");
        System.out.println("✅ Entered Level 1 & Level 2 Fields");
        logger.info("✅ Entered Level 1 & Level 2 Fields");
        Thread.sleep(1000);

        // ✅ Click "Add Choices" Button
        WebElement addChoicesPopupButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[4]/div[2]/div/mat-dialog-container/div/div/app-add-another-level-dropdown-dialog/div/div[4]/div[3]/a")));
        addChoicesPopupButton.click();
        System.out.println("✅ Clicked 'Add Choice' Button");
        logger.info("✅ Clicked 'Add Choice' Button");
        Thread.sleep(1000);

        // ✅ Add Additional Choice
        WebElement newChoiceField1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='mat-input-57']")));
        newChoiceField1.sendKeys("Additional Choice2");
        System.out.println("✅ Entered Additional Choice1");
        logger.info("✅ Entered Additional Choice1");
        Thread.sleep(1000);

        // ✅ Click "Save" Button in Popup
        WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div[4]/div[2]/div/mat-dialog-container/div/div/app-add-another-level-dropdown-dialog/div/div[5]/button[1]/span[2]/div/span")));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        jsExecutor.executeScript("arguments[0].click();", saveButton);
        System.out.println("✅ Clicked 'Save' Button for Nested Level Choices");
        logger.info("✅ Clicked 'Save' Button for Nested Level Choices");
        // ✅ Scroll to the top after saving
//        jsExecutor.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
//        System.out.println("⬆️ Scrolled to the top.");
//        logger.info("⬆️ Scrolled to the top.");
    }
}
