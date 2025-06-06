package d365.settings.steps;

import d365.DriverFactory;
import d365.Hooks;
import d365.settings.TicketForms;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketFormsSteps {

    private WebDriver driver;
    private TicketForms ticketForms;

    private static final Logger logger = LoggerFactory.getLogger(TicketFormsSteps.class);

    public TicketFormsSteps() {
        this.driver = Hooks.getDriver(); // ✅ Use the public getter

        if (this.driver == null) {
            this.driver = DriverFactory.getDriver();
            // Optionally update Hooks (only if you need to update it globally)
             Hooks.setDriver(this.driver); // You’d need to create this setter method if needed
        }
        this.ticketForms = new TicketForms(this.driver);
    }

    @And("I navigate to the Ticket Forms settings page")
    public void navigateToTicketFormsSettingsPage() {
        logger.info("Navigating to Ticket Forms settings page...");
        ticketForms.navigateToTicketForms1();
        logger.info("Successfully navigated to Ticket Forms settings page.");
    }

    @And("I add ticket fields to Agent Portal")
    public void addTicketFieldsToAgentPortal() {
        logger.info("Adding ticket fields to Agent Portal...");
        ticketForms.navigateToTicketForms();
        ticketForms.hoverAndClickAddButtonsAgentPortal();
        logger.info("Successfully added ticket fields to Agent Portal.");
    }

    @And("I add ticket fields to Support Portal")
    public void addTicketFieldsToSupportPortal() {
        logger.info("Adding ticket fields to Support Portal...");
        ticketForms.navigateToTicketFormsSupportPortal();
        ticketForms.hoverAndClickAddButtonsForSupportPortal();
        logger.info("Successfully added ticket fields to Support Portal.");
    }

    @And("I add ticket fields to Webform")
    public void addTicketFieldsToWebform() {
        logger.info("Adding ticket fields to Webform...");
        ticketForms.navigateToTicketFormsWebform();
        ticketForms.hoverAndClickAddButtonsForWebform();
        logger.info("Successfully added ticket fields to Webform.");
    }
}
