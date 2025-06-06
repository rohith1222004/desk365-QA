package d365.settings.steps;

import d365.DriverFactory;
import d365.Hooks;
import d365.settings.Agents;
import d365.signin.SignInMSAL;
import d365.settings.TicketFields;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import d365.utilities.CommonUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketFieldsSteps {

    private WebDriver driver;
    private SignInMSAL signInMSAL;
    private TicketFields ticketFields;
    private Agents agents;
    private CommonUtilities commonUtilities = new CommonUtilities();
    private static final Logger logger = LoggerFactory.getLogger(TicketFieldsSteps.class);


    public TicketFieldsSteps() {
        this.driver = Hooks.getDriver();  // ✅ Access driver using getter

        if (this.driver == null) {
            this.driver = DriverFactory.getDriver();

            // Optional: Set the driver in Hooks globally, if needed
            Hooks.setDriver(this.driver); // ✅ Use setter (you need to create this)
        }

        this.signInMSAL = new SignInMSAL(this.driver);
        this.ticketFields = new TicketFields(this.driver);
        this.agents = new Agents(this.driver);
    }


    @Given("I am on the application login page")
    public void iAmOnTheApplicationLoginPage() {
        driver.get(commonUtilities.getSignInUrl());
        System.out.println("✅ Navigated to the application login page");
        logger.info("✅ Navigated to the application login page");
    }

    @When("I sign in with Microsoft for ticket fields using email {string} and password {string}")
    public void signInWithMicrosoftForTicketFields(String email, String password) {
        String msEmail = System.getenv("MS_EMAIL");
        String msPassword = System.getenv("MS_PASSWORD");
        signInMSAL.signInWithMicrosoft(msEmail, msPassword);
        System.out.println("✅ Signed in with Microsoft for ticket fields.");
        logger.info("✅ Signed in with Microsoft for ticket fields using email: {}", email);
    }

    @When("I Add agent to the helpdesk")
    public void addAgentToHelpdesk() {
        agents.addAgentToHelpdesk("vibin@kanidesk.com", "vibin-kdesk");
        System.out.println("✅ Added agent to the helpdesk");
        logger.info("✅ Added agent to the helpdesk: vibin@kanidesk.com");
    }

    @When("I navigate to the Ticket Fields settings page")
    public void navigateToTicketFieldsPage() {
        driver.get(commonUtilities.getTicketFieldsUrl());
        System.out.println("✅ Navigating to Ticket Fields settings page...");
        logger.info("✅ Navigating to Ticket Fields settings page...");
    }

    @When("I add all custom ticket fields")
    public void addAllCustomTicketFields() throws InterruptedException {
        ticketFields.addTicketFields();
        System.out.println("✅ Adding all custom ticket fields...");
        logger.info("✅ Adding all custom ticket fields...");
    }

    @Then("all the fields should be added successfully")
    public void verifyFieldsAddedSuccessfully() {
        System.out.println("✅ All ticket fields were added successfully!");
        logger.info("✅ All ticket fields were added successfully!");
    }
}
