package com.google.steps;

import com.google.pages.SearchPage;
import com.google.pages.SearchResultsPage;
import com.google.pages.TargetWebsitePage;
import com.google.service.NotificationService;
import com.google.service.NotificationServiceEmailImpl;
import com.google.service.NotificationServiceNoOpImpl;
import com.google.util.TestProperties;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleSearchSteps {
    private static Logger logger = LoggerFactory.getLogger(NotificationServiceEmailImpl.class);
    private WebDriver driver;
    private SearchPage searchPage;
    private SearchResultsPage searchResultsPage;
    private String searchTerm;
    private String hostNameOfLink;

    @Given("^I am on Google search page$")
    public void openGoogleSearchPage() throws Throwable {
//        String url = "http://www.google.co.uk/";
        String url = TestProperties.instance().getProperty("url");
        logger.debug("url: " + url);
        driver = Hooks.getDriver();
        searchPage = new SearchPage(driver);
        searchPage.loadPage(url);
    }

    @When("^I search for the term \"([^\"]*)\"$")
    public void searchForTerm(String searchString) throws Throwable {
        searchTerm = searchString;
        searchPage.search(searchString);
    }

    @Then("^I should get results for the search term$")
    public void checkSearchResults() throws Throwable {
        searchResultsPage = new SearchResultsPage(driver);
        String searchResultsPageTitle = searchResultsPage.getTitle();
        assertThat(searchResultsPageTitle).contains(searchTerm);

        long numberOfResults = searchResultsPage.findNumberOfResults();
        assertThat(numberOfResults).isGreaterThan(0);
    }

    @When("^I click on one of the link in search results page$")
    public void clickOnLinkOfSearchResults() throws Throwable {
        hostNameOfLink = searchResultsPage.openLinkFromResults(0);
    }

    @Then("^I should be directed to the respective website$")
    public void checkTargetWebsiteDisplayed() throws Throwable {
        TargetWebsitePage targetWebsitePage = new TargetWebsitePage(driver);
        String currentUrl = targetWebsitePage.getCurrentUrl();
        assertThat(currentUrl).contains(hostNameOfLink);

    }

    @Then("^I should be able to browse forward through all result pages$")
    public void browseForward() throws Throwable {
        searchResultsPage.browseForwardThroughResultPages();
        boolean isNextLinkDisplayed = searchResultsPage.isNextLinkDisplayed();
        assertThat(isNextLinkDisplayed).isFalse();
    }

    @Then("^I should be able to browse backward through all result pages$")
    public void browseBackward() throws Throwable {
        searchResultsPage.browseBackwardThroughResultPages();
        boolean isPreviousLinkDisplayed = searchResultsPage.isPreviousLinkDisplayed();
        assertThat(isPreviousLinkDisplayed).isFalse();
    }

    @Then("^I should not get results for the search term$")
    public void checkResultsNotFoundForSearchTerm() throws Throwable {
        searchResultsPage = new SearchResultsPage(driver);
        String message = searchResultsPage.getResultsNotFoundMessage();
        assertThat(message).contains("not match any documents");
    }

    @Then("^I should be notified about the search result$")
    public void notifyAboutSearchResults() throws Throwable {
        String notificationSubject = "Your search did not match any documents";
        String notificationBody = String.format("Your search - %s - did not match any documents", searchTerm);
        NotificationService notificationService = new NotificationServiceNoOpImpl();
        notificationService.sendNotification(notificationSubject, notificationBody);
    }

}
