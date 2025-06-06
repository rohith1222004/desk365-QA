package d365.ticketCreation.steps;

import d365.DriverFactory;
import d365.Hooks;
import d365.ticketCreation.AgentPortal;
import d365.ticketCreation.SupportPortal;
import d365.ticketCreation.WebForm;
import d365.utilities.CommonUtilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;

public class TicketCreationSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private AgentPortal agentPortal;
    private SupportPortal supportPortal;
    private WebForm webForm;
    private CommonUtilities commonUtilities = new CommonUtilities();
    private static final Logger logger = LoggerFactory.getLogger(TicketCreationSteps.class);

    public TicketCreationSteps() {
        this.driver = Hooks.getDriver();  // ✅ Access driver using getter

        if (this.driver == null) {
            this.driver = DriverFactory.getDriver();
            Hooks.setDriver(this.driver); // ✅ Use setter (you need to create this)
        }
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.agentPortal = new AgentPortal(this.driver);
        this.supportPortal = new SupportPortal(this.driver);
        this.webForm = new WebForm(this.driver);
    }

    public static List<String> createdTickets = new ArrayList<>();

    public static void addCreatedTicket(String subject) {
        createdTickets.add(subject);
    }

    private String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd//HH-mm-ss").format(new Date());
    }

    // Scenario 1: Ticket creation in Agent Portal
    @Given("I navigate to the Agent Portal Ticket page")
    public void iNavigateToAgentPortalTicketPage() {
        agentPortal.navigateToTicketPage();
        System.out.println("✅ Navigated to Agent Portal Ticket page.");
        logger.info("✅ Navigated to Agent Portal Ticket page.");
    }

    @When("I create a valid ticket with contact {string}, subject {string}, and description {string}")
    public void iCreateTicketInAgentPortal(String contact, String subject, String description) {
        String uniqueSubject = subject + " #" + getTimestamp() + "-" + (createdTickets.size() + 1);
        addCreatedTicket(uniqueSubject);
        agentPortal.createValidTicket(contact, uniqueSubject, description);
        System.out.println("✅ Created ticket in Agent Portal.");
        logger.info("✅ Created ticket in Agent Portal.");
    }

//    @Then("I should see the ticket listed in the Agent Portal")
//    public void iShouldSeeTicketInAgentPortal() {
//        agentPortal.verifyTicketCreation();
//        System.out.println("✅ Verified ticket in Agent Portal.");
//        logger.info("✅ Verified ticket in Agent Portal.");
//    }

    // Scenario 2: Ticket creation in Support Portal
    @Given("I open the Support Portal without signing in")
    public void iOpenSupportPortalWithoutSignIn() {
        supportPortal.openSupportPortalInNewTab();
        System.out.println("✅ Opened Support Portal without signing in.");
        logger.info("✅ Opened Support Portal without signing in.");
    }

    @When("I create a ticket with email {string}, subject {string}, and description {string}")
    public void iCreateTicketInSupportPortalWithoutSignIn(String email, String subject, String description) {
        String uniqueSubject = subject + " #" + getTimestamp() + "-" + (createdTickets.size() + 1);
        addCreatedTicket(uniqueSubject);
        supportPortal.createTicketWithoutSignIn(email, uniqueSubject, description);
        System.out.println("✅ Created ticket in Support Portal without sign-in.");
        logger.info("✅ Created ticket in Support Portal without sign-in.");
    }

//    @Then("I should see the ticket listed in the Support Portal")
//    public void iShouldSeeTicketInSupportPortal() {
//        supportPortal.verifyTicketCreation();
//        System.out.println("✅ Verified ticket in Support Portal.");
//        logger.info("✅ Verified ticket in Support Portal.");
//    }

    // Scenario 3: Ticket creation in Support Portal after sign-in
    @Given("I sign in to the Support Portal")
    public void iSignInToSupportPortal() {
        supportPortal.signInToSupportPortal();
        System.out.println("✅ Signed in to Support Portal.");
        logger.info("✅ Signed in to Support Portal.");
    }

    @When("I create a ticket after sign in with email {string}, subject {string}, and description {string}")
    public void iCreateTicketInSupportPortalAfterSignIn(String email, String subject, String description) {
        String uniqueSubject = subject + " #" + getTimestamp() + "-" + (createdTickets.size() + 1);
        addCreatedTicket(uniqueSubject);
        // Assuming your method doesn't require email post sign-in (usually user is known)
        supportPortal.createTicketAfterSignIn(uniqueSubject, description);
        System.out.println("✅ Created ticket in Support Portal after sign-in.");
        logger.info("✅ Created ticket in Support Portal after sign-in.");
    }

//    @Then("I should see the ticket listed in the Support Portal after sign-in")
//    public void iShouldSeeTicketInSupportPortalAfterSignIn() {
//        supportPortal.verifyTicketCreation();
//        System.out.println("✅ Verified ticket in Support Portal after sign-in.");
//        logger.info("✅ Verified ticket in Support Portal after sign-in.");
//    }

    // Scenario 4: Ticket creation in Webform
    @Given("I open the Webform")
    public void iOpenTheWebform() {
        webForm.openWebform();
        System.out.println("✅ Opened Webform.");
        logger.info("✅ Opened Webform.");
    }

    @When("I create a valid ticket with email {string}, subject {string}, and description {string}")
    public void iCreateTicketInWebform(String contactEmail, String subject, String description) {
        String uniqueSubject = subject + " #" + getTimestamp() + "-" + (createdTickets.size() + 1);
        addCreatedTicket(uniqueSubject);
        webForm.createValidTicketFromWebform(contactEmail, uniqueSubject, description);
        System.out.println("✅ Created ticket from Webform.");
        logger.info("✅ Created ticket from Webform.");
    }

    @Then("I should see the ticket listed in the ticket list")
    public void iShouldSeeTicketInTicketList() {
        verifyAllCreatedTicketsExist(createdTickets);
        System.out.println("✅ Verified ticket in Webform.");
        logger.info("✅ Verified ticket in Webform.");
    }

    public void verifyAllCreatedTicketsExist(List<String> createdTickets) {
        System.out.println("📌 Verifying all created tickets in the Ticket List...");
        navigateToTicketListPage();

        driver.navigate().refresh();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table/tbody")));

        List<WebElement> ticketRows = driver.findElements(By.xpath("//table/tbody/tr"));

        List<String> actualSubjects = new ArrayList<>();
        for (int i = 1; i <= ticketRows.size(); i++) {
            try {
                WebElement subjectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//table/tbody/tr[" + i + "]/td[3]/div/a/span")
                ));
                String subjectText = subjectElement.getText().trim();
                actualSubjects.add(subjectText);
            } catch (Exception e) {
                System.err.println("❌ Error retrieving subject from row " + i + ": " + e.getMessage());
            }
        }

        boolean allTicketsFound = true;
        for (String expectedSubject : createdTickets) {
            if (!actualSubjects.contains(expectedSubject)) {
                System.err.println("❌ Ticket not found in list: " + expectedSubject);
                allTicketsFound = false;
            } else {
                System.out.println("✅ Ticket verified: " + expectedSubject);
            }
        }

        Assert.assertTrue(allTicketsFound, "❌ Some tickets were not found in the Ticket List!");
    }
    public void navigateToTicketListPage() {
        System.out.println("📌 Navigating to Ticket List Page...");
        String ticketListUrl = commonUtilities.getTicketListUrl();
        driver.get(ticketListUrl);  // <-- navigate here
        wait.until(ExpectedConditions.urlContains("/tickets"));
        System.out.println("✅ Redirected to Ticket List Page");
    }
}
