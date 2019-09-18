@googleSearch
Feature: Google Search
  As a google user I want to be able to:
  - Search for a term in google search engine
  - Retrieve the results and verify I can be directed to the respective websites by clicking on the link displayed
  - Browse all results through pagination.
  - Be notified if no results are found.

  @searchResults
  Scenario Outline: Searches for a term in google and checks results
    Given I am on Google search page
    When I search for the term "<searchTerm>"
    Then I should get results for the search term
    Examples:
      | searchTerm |
      | England    |
      | Italy      |

  @searchAndOpen
  Scenario Outline: Searches for a term in google and opens a link from search results page
    Given I am on Google search page
    When I search for the term "<searchTerm>"
    Then I should get results for the search term
    When I click on one of the link in search results page
    Then  I should be directed to the respective website
    Examples:
      | searchTerm |
      | America    |

  @pagination
  Scenario: Searches for a term in google and browse through the search result pages
    Given I am on Google search page
    When I search for the term "Lascelles Abercrombie (1881–1938), poet and critic"
    Then I should get results for the search term
    And I should be able to browse forward through all result pages
    And I should be able to browse backward through all result pages

  @searchFailure
  Scenario: Searches for a term in google and notifies me if results not found
    Given I am on Google search page
    When I search for the term "dsfdfdgfdgfdgffhghghgh4343dgfgfdgfgfh5454nn4354545nn"
    Then I should not get results for the search term
    And I should be notified about the search result