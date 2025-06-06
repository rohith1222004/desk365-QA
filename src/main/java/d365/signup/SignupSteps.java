package d365.signup;

import d365.utilities.CommonUtilities;
import d365.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignupSteps {

    private WebDriver driver;
    private MsalSignUp msalSignUp;
    private String generatedSubdomain; // Class-level variable to store subdomain
    private CommonUtilities utilities = new CommonUtilities();

    public SignupSteps() {
        // Initialize WebDriver using DriverFactory
        driver = DriverFactory.getDriver();
        msalSignUp = new MsalSignUp(driver);
    }

    @Given("I am on the MSAL sign-up page")
    public void iAmOnTheMSALSignUpPage() {
        msalSignUp.openSignUpPage(); // Navigate to the MSAL sign-up page
    }

    @When("I fill in the company name")
    public void iFillInTheCompanyName() {
        String companyName = utilities.getCompanyName();
        msalSignUp.fillCompanyName(companyName + new SimpleDateFormat("MMddHHmmss").format(new Date())); // Fill in the company name
    }

    @When("I fill in the subdomain")
    public void iFillInTheSubdomain() {
        String subdomain = utilities.getSubdomain();
        generatedSubdomain = (subdomain + new SimpleDateFormat("MMddHHmmss").format(new Date())).toLowerCase();
        msalSignUp.fillSubdomain(generatedSubdomain); // Fill in the subdomain
    }

    @When("I select the data center region")
    public void iSelectTheDataCenterRegion() {
        String region = utilities.getDataCenterRegion();
        msalSignUp.selectDataCenterRegion(region); // Select the data center region
    }

    @When("I handle the reCAPTCHA")
    public void iHandleTheRecaptcha() {
        msalSignUp.handleRecaptcha(); // Handle the reCAPTCHA
    }

    @When("I sign in with Microsoft using email {string} and password {string}")
    public void iSignInWithMicrosoft(String email, String password) {
        // Retrieve the MS_EMAIL and MS_PASSWORD environment variables
        String msEmail = System.getenv("MS_EMAIL");
        String msPassword = System.getenv("MS_PASSWORD");

        // Verify if the environment variables are retrieved correctly
        if (msEmail == null || msPassword == null || msEmail.isEmpty() || msPassword.isEmpty()) {
            // If any of the values is null or empty, log an error message and throw an exception
            System.out.println("Error: Environment variables MS_EMAIL or MS_PASSWORD are not set or are empty.");
            throw new RuntimeException("Environment variables MS_EMAIL or MS_PASSWORD are not set or are empty.");
        } else {
            // If environment variables are set correctly, log their values (excluding the password for security)
            System.out.println("MS_EMAIL: " + msEmail);  // Logs the email (do not log passwords for security reasons)
            msalSignUp.signInWithMicrosoft(msEmail, msPassword);  // Proceed with the sign-in
        }
    }


    @Then("I should be redirected to the main application page")
    public void iShouldBeRedirectedToTheMainApplicationPage() {
        // Check if the URL contains the expected string
        Assert.assertTrue(driver.getCurrentUrl().contains("msal-signup-res"));
    }

//    @Then("I should see {string}")
//    public void iShouldSee(String expectedMessage) {
//        // Check if the page contains the expected success message
//        Assert.assertTrue(driver.getPageSource().contains(expectedMessage));
//    }

    @Then("I complete the helpdesk sign-up verification")
    public void iCompleteHelpdeskSignUpVerification() {
        // Call the completeSignUpProcess method to handle redirection, button click, and verification
        msalSignUp.completeSignUpProcess();
        msalSignUp.verifyPageHeadersAndClickNext(generatedSubdomain);
        saveSubdomainToFile(generatedSubdomain);
    }

    @Then("I configure helpdesk settings")
    public void iConfigureHelpdeskSettings() {
        try {
            // Use the dynamically generated subdomain from the previous step
            msalSignUp.updateHelpdeskSettings(generatedSubdomain);
            msalSignUp.updateSupportPortalSettings(generatedSubdomain);
        } catch (Exception e) {
            System.err.println("Error during helpdesk setup: " + e.getMessage());
            throw new RuntimeException("Failed to configure helpdesk settings and add agent");
        }
    }

    // Dynamic company name generator
    private String generateUniqueName(String baseName) {
        String timestamp = new SimpleDateFormat("MMddHHmmss").format(new Date());
        return baseName + timestamp;
    }

    // Save subdomain to a file
    private void saveSubdomainToFile(String subdomain) {
        String subdomainURL = subdomain + ".kanidesk.com";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/subdomain.txt", false))) {
            writer.write(subdomainURL);
            System.out.println("Subdomain saved to file: " + subdomainURL);
        } catch (IOException e) {
            System.err.println("Error while saving subdomain to file: " + e.getMessage());
        }
    }


}