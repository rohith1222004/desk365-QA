package d365;

import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

public class SendEmailReportSuite {

    @Test
    public void sendEmailReport() throws InterruptedException {
        // Wait to ensure report files are fully generated
        Thread.sleep(5000);

        List<String> recipients = Arrays.asList("vibin@kanidesk.com", "nivin@kanidesk.com");
        String report1 = "target/cucumber-reports/ticketfields-report.html";
        String report2 = "target/cucumber-reports/ticketcreation-report.html";

        EmailReportSender.sendReport(recipients, Arrays.asList(report1, report2));
    }
}
