package d365;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterClass;

@CucumberOptions(
        features = "src/main/resources/features/ticketfieldsandforms.feature",
        glue = {
                "d365.settings.steps",
                "d365.ticketCreation.steps"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/ticketfields-report.html",
                "json:target/cucumber-reports/ticketfields-report.json"
        }
)
public class TicketFieldsRunner extends AbstractTestNGCucumberTests {

}
