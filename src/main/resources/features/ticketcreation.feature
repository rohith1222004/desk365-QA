Feature: Ticket Creation in Agent Portal, Support Portal, and Webform

  Scenario: Create a ticket in the Agent Portal
    Given I navigate to the Agent Portal Ticket page
    When I create a valid ticket with contact "Joe", subject "Agent-Ticket", and description "The internet is down"
#    Then I should see the ticket listed in the Agent Portal


  Scenario: Create a ticket in Support Portal
    Given I open the Support Portal without signing in
    When I create a ticket with email "727721euec178@skcet.ac.in", subject "Support-Ticket", and description "Issue with accessing the portal"
    And I sign in to the Support Portal
    When I create a ticket after sign in with email "727721euec178@skcet.ac.in", subject "Support-Ticket-AfterSignIn", and description "After sign-in, portal issue resolved"
#    Then I should see the ticket listed in the Support Portal

  Scenario: Create a ticket in Webform
    Given I open the Webform
    When I create a valid ticket with email "727721euec178@skcet.ac.in", subject "Webform-Ticket", and description "The network is slow"
    Then I should see the ticket listed in the ticket list

