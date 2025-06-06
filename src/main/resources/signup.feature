#Feature: MSAL Sign-up Process
#
#  Scenario: User signs up with Microsoft
#    Given I am on the MSAL sign-up page
#    When I fill in the company name
#    And I fill in the subdomain
#    And I select the data center region
#    And I handle the reCAPTCHA
#    And I sign in with Microsoft using email "<MS_EMAIL>" and password "<MS_PASSWORD>"
#    Then I should be redirected to the main application page
#    And I complete the helpdesk sign-up verification
#    And I configure helpdesk settings