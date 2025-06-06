package d365;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterClass;

import java.util.Arrays;
import java.util.List;

@CucumberOptions(
        features = "src/main/resources/features/ticketcreation.feature",
        glue = {
                "d365.settings.steps",
                "d365.ticketCreation.steps"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/ticketcreation-report.html",
                "json:target/cucumber-reports/ticketcreation-report.json"
        }
)
public class TicketCreationRunner extends AbstractTestNGCucumberTests {

}
