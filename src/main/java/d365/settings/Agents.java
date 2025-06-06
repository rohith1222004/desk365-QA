package d365.settings;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import d365.utilities.CommonUtilities;

import java.time.Duration;

public class Agents {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;
    private static final Logger logger = LoggerFactory.getLogger(Agents.class);
    CommonUtilities commonUtilities = new CommonUtilities();

    public Agents(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void addAgentToHelpdesk(String agentEmail, String agentName) {
        try {
            String agentSettingsURL = commonUtilities.getAgentsListUrl();
            driver.get(agentSettingsURL);
            logger.info("🌐 Navigated to Agent Settings Page");

            WebElement addAgentButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@qa-test='button-addAgent']")));
            if (addAgentButton.isDisplayed() && addAgentButton.isEnabled()) {
                addAgentButton.click();
                logger.info("✅ Clicked 'Add Agent' button");
            } else {
                logger.error("❌ 'Add Agent' button not displayed or not enabled");
                return;
            }

            WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-form-field[@qa-test='input-email']//input[@formcontrolname='agentEmail']")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true); arguments[0].value='" + agentEmail + "'; arguments[0].dispatchEvent(new Event('input', { bubbles: true }))", emailInput);
            logger.info("✅ Entered Agent Email: {}", agentEmail);

            WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-form-field[@qa-test='input-name']//input[@formcontrolname='agentName']")));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true); arguments[0].value='" + agentName + "'; arguments[0].dispatchEvent(new Event('input', { bubbles: true }))", nameInput);
            logger.info("✅ Entered Agent Name: {}", agentName);

            WebElement roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@qa-test='dd-role']")));
            jsExecutor.executeScript("arguments[0].click();", roleDropdown);
            Thread.sleep(500);
            roleDropdown.click();

            WebElement roleOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//mat-option[@qa-test='options-role'])[1]")));
            jsExecutor.executeScript("arguments[0].click();", roleOption);
            logger.info("✅ Selected Agent Role");

            WebElement groupsInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//app-text-suggest-multiple-values-editor[@qa-test='dd-groups']//input[@type='text']")));
            groupsInput.click();
            WebElement groupOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//mat-option[@role= \"option\"])[1]")));
            groupOption.click();
            logger.info("✅ Selected Agent Group");

            WebElement inviteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//app-button-spinner[@qa-test = \"button-invite\"]")));
            Thread.sleep(2000);
            inviteButton.click();
            Thread.sleep(2000);
            logger.info("✅ Clicked 'Invite' button to add the agent");

        } catch (Exception e) {
            logger.error("❌ Error while adding Agent: ", e);
        }
    }
}
