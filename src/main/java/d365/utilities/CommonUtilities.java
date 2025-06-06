package d365.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * CommonUtilities provides reusable URLs by dynamically reading the subdomain from a file.
 */
public class CommonUtilities {
    private static final String SUBDOMAIN = loadSubdomain();

    private static final String SUBDOMAIN1 = "TestAutomation";  // Hardcoded subdomain
    private static final String COMPANY_NAME = "TestAutomation";  // Hardcoded company name
    private static final String DATA_CENTER_REGION = "United States";  // Hardcoded region

    /**
     * ✅ Reads subdomain from the text file at runtime.
     */
    private static String loadSubdomain() {
        String filePath = "src/main/resources/subdomain.txt"; // Path to the text file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.readLine().trim(); // Read first line and remove unnecessary spaces
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("❌ ERROR: Failed to load subdomain from file!");
        }
    }

    public String getHomePageUrl() {
        return "https://" + SUBDOMAIN + "/app/gp-home";
    }

    public String getTicketListUrl() {
        return "https://" + SUBDOMAIN + "/app/tickets";
    }

    public String getAgentPotralTktcreationUrl() {
        return "https://" + SUBDOMAIN + "/app/createticket";
    }


    public String getSignInUrl() {
        return "https://" + SUBDOMAIN + "/app/";
    }

    public String getCustomFormsUrl() {
        return "https://" + SUBDOMAIN + "/app/settings/custom-forms/custom-forms-list";
    }

    public String getTicketFieldsUrl() {
        return "https://" + SUBDOMAIN + "/app/settings/ticket-fields";
    }

    public String getSupportPortalUrl() {
        return "https://" + SUBDOMAIN + "/support";
    }

    public String getWebFormUrl() {
        return "https://" + SUBDOMAIN + "/app/wf";
    }

    public String getContactsListUrl() {
        return "https://" + SUBDOMAIN + "/app/accounts/contacts/contact-list?offset=0";
    }

    public String getGroupsUrl() {
        return "https://" + SUBDOMAIN + "/app/settings/groups";
    }

    public String getAgentsListUrl() {
        return "https://" + SUBDOMAIN + "/app/settings/agents/agent-list";
    }

    public String getHelpdeskSettingsUrl() {
        return "https://" + SUBDOMAIN + "/app/settings/helpdesk";
    }

    public String getSupportPortalSettingsUrl() {
        return "https://" + SUBDOMAIN + "/app/settings/portal";
    }

    public String getSurveyListUrl() {
        return "https://" + SUBDOMAIN + "/app/settings/surveys/survey-list";
    }

    public String getSupportEmail() {
        return "support@" + SUBDOMAIN;
    }

    // Getter methods for company name and region
    public String getCompanyName() {
        return COMPANY_NAME;
    }

    public String getDataCenterRegion() {
        return DATA_CENTER_REGION;
    }

    public String getSubdomain() {
        return SUBDOMAIN1;
    }

    public String getSignUpUrl() {
        return "https://apps.kanidesk.com/app/signup"; // Full URL with protocol
    }


}
