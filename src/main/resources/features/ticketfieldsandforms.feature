Feature: Add Ticket Fields After Sign-In

  Background:
    Given I am on the application login page

  Scenario: Sign in and add all custom ticket fields
    When I sign in with Microsoft for ticket fields using email "<MS_EMAIL>" and password "<MS_PASSWORD>"
    And I Add agent to the helpdesk
    And I navigate to the Ticket Fields settings page
    And I add all custom ticket fields
    Then all the fields should be added successfully

  Scenario: Add ticket fields to all portals
    And I navigate to the Ticket Forms settings page
    And I add ticket fields to Agent Portal
    And I add ticket fields to Support Portal
    And I add ticket fields to Webform