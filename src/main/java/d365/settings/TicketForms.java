package d365.settings;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import d365.utilities.CommonUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class TicketForms {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;
    private Actions actions;
    private CommonUtilities commonUtilities;
    private static final Logger logger = LoggerFactory.getLogger(TicketForms.class); // Initialize Logger

    public TicketForms(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.jsExecutor = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        this.commonUtilities = new CommonUtilities(); // ✅ Initialize CommonUtilities
    }

    // ** Navigate to Ticket Forms - Support Portal **
    public void navigateToTicketFormsSupportPortal() {
        System.out.println("📌 Navigating to Ticket Forms - Support Portal...");
        logger.info("📌 Navigating to Ticket Forms - Support Portal...");

        try {
            navigateToTicketForms1();
            // ✅ Scroll and Click on "Support Portal"
            WebElement supportPortalButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='cdk-drop-list-0']/div/div")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", supportPortalButton);
            Thread.sleep(500); // Small delay for UI to stabilize

            try {
                supportPortalButton.click();
                System.out.println("✅ Clicked on 'Support Portal' Form");
                logger.info("✅ Clicked on 'Support Portal' Form");
            } catch (Exception e) {
                jsExecutor.executeScript("arguments[0].click();", supportPortalButton); // JavaScript click fallback
                System.out.println("✅ Clicked on 'Support Portal' Form using JavaScript");
                logger.info("✅ Clicked on 'Support Portal' Form using JavaScript");
            }
        } catch (Exception e) {
            System.out.println("❌ Error navigating to Support Portal Ticket Forms: " + e.getMessage());
            logger.error("❌ Error navigating to Support Portal Ticket Forms: {}", e.getMessage());
        }
    }

    public void navigateToTicketForms1() {
        try {
            System.out.println("🌐 Navigating to Ticket Forms via URL...");
            logger.info("🌐 Navigating to Ticket Forms via URL...");

            driver.get(commonUtilities.getCustomFormsUrl()); // ✅ Navigate via URL

            // ✅ Wait for the Ticket Forms page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/app-main-nav/mat-sidenav-container/mat-sidenav-content/mat-toolbar/div/div[1]/span[3]")));
            System.out.println("✅ Successfully navigated to Ticket Forms!");
            logger.info("✅ Successfully navigated to Ticket Forms!");
        } catch (Exception e) {
            System.err.println("❌ Error navigating to Ticket Forms: " + e.getMessage());
            logger.error("❌ Error navigating to Ticket Forms: {}", e.getMessage());
        }
    }

    public void navigateToTicketForms() {
        System.out.println("\uD83D\uDCCC Navigating to Ticket Forms...");
        logger.info("\uD83D\uDCCC Navigating to Ticket Forms...");

        try {
            // ✅ Click on "Settings"
            navigateToTicketForms1();
            // ✅ Click on "Agent Portal"
            WebElement agentPortalButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/app-root/app-main-nav/mat-sidenav-container/mat-sidenav-content/div/app-settings/div/div[2]/app-custom-forms/div/app-custom-forms-list/div/div[3]/div[1]/div[7]/div")));
            agentPortalButton.click();
            System.out.println("✅ Clicked on 'Agent Portal' Form");
            logger.info("✅ Clicked on 'Agent Portal' Form");
        } catch (Exception e) {
            System.out.println("❌ Error navigating to Ticket Forms: " + e.getMessage());
            logger.error("❌ Error navigating to Ticket Forms: {}", e.getMessage());
        }
    }

    public void navigateToTicketFormsWebform() {
        System.out.println("\uD83D\uDCCC Navigating to Ticket Forms...");
        logger.info("\uD83D\uDCCC Navigating to Ticket Forms...");

        try {
            navigateToTicketForms1();
            // ✅ Click on "Web Form"
            WebElement agentPortalButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/app-root/app-main-nav/mat-sidenav-container/mat-sidenav-content/div/app-settings/div/div[2]/app-custom-forms/div/app-custom-forms-list/div/div[3]/div[1]/div[9]/div/div/div[1]")));
            agentPortalButton.click();
            System.out.println("✅ Clicked on 'Web Form' Form");
            logger.info("✅ Clicked on 'Web Form' Form");
        } catch (Exception e) {
            System.out.println("❌ Error navigating to Ticket Forms: " + e.getMessage());
            logger.error("❌ Error navigating to Ticket Forms: {}", e.getMessage());
        }
    }

    // ** Add Fields to Forms Using Dynamic Function **
    private void hoverAndClickAddButtons(String[] xpaths, String formType) {
        System.out.println("📌 Adding fields to " + formType + "...");
        logger.info("📌 Adding fields to " + formType + "...");

        for (String xpath : xpaths) {
            try {
                // ✅ Locate the element
                WebElement addButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", addButton);
                Thread.sleep(1000); // Allow scrolling animation to complete

                // ✅ Click the button
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.mat-mdc-snack-bar-label.mdc-snackbar__label")));
                jsExecutor.executeScript("arguments[0].click();", addButton);
                System.out.println("✅ Clicked on Add Button (" + formType + "): " + xpath);
                logger.info("✅ Clicked on Add Button (" + formType + "): " + xpath);

                // ✅ Wait briefly before proceeding to the next button
                Thread.sleep(1000);

            } catch (TimeoutException e) {
                System.out.println("⚠️ Add Button Not Found (" + formType + "): " + xpath);
                logger.warn("⚠️ Add Button Not Found (" + formType + "): {}", xpath);
            } catch (Exception e) {
                System.out.println("❌ Error Clicking Add Button (" + formType + "): " + e.getMessage());
                logger.error("❌ Error Clicking Add Button (" + formType + "): {}", e.getMessage());
            }
        }
        System.out.println("✅ All Add Buttons Clicked Successfully for " + formType + "!");
        logger.info("✅ All Add Buttons Clicked Successfully for " + formType + "!");
    }

    // ** Add Fields to Agent Portal **
    public void hoverAndClickAddButtonsAgentPortal() {
        String[] agentPortalXPaths = {
                "//*[contains(@id, 'cdk-drop-list')]/div[10]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[11]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[12]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[13]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[14]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[15]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[16]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[18]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[19]/div/div/div[2]/mat-icon"
        };
        hoverAndClickAddButtons(agentPortalXPaths, "Agent Portal");
    }

    // ** Add Fields to Webform **
    public void hoverAndClickAddButtonsForWebform() {
        String[] webformXPaths = {
                "//*[contains(@id, 'cdk-drop-list')]/div[3]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[4]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[5]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[6]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[7]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[8]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[9]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[10]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[11]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[12]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[13]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[14]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[15]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[16]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[18]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[19]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[20]/div/div/div[2]/mat-icon"
        };
        hoverAndClickAddButtons(webformXPaths, "Webform");
    }

    // ** Add Fields to Support Portal **
    public void hoverAndClickAddButtonsForSupportPortal() {
        String[] supportPortalXPaths = {
                "//*[contains(@id, 'cdk-drop-list')]/div[3]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[4]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[5]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[6]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[7]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[8]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[9]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[10]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[11]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[12]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[13]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[14]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[15]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[16]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[18]/div/div/div[2]/mat-icon",
                "//*[contains(@id, 'cdk-drop-list')]/div[19]/div/div/div[2]/mat-icon"
        };
        hoverAndClickAddButtons(supportPortalXPaths, "Support Portal");
    }
}
