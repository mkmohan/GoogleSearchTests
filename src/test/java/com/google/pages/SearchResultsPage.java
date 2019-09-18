package com.google.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SearchResultsPage extends BasePage {
    @FindBy(how = How.XPATH, using = "//*[@id='search']//div[@class='rc']/div/a[starts-with(@href,'http')]")
    private List<WebElement> searchResultLinks;

    @FindBy(how = How.ID, using = "resultStats")
    private WebElement numberOfResultsElement;

    @FindBy(how = How.ID, using = "pnnext")
    private WebElement nextLink;

    @FindBy(how = How.ID, using = "pnprev")
    private WebElement previousLink;

    @FindBy(how = How.ID, using = "pnnext")
    private List<WebElement> nextLinkElements;

    @FindBy(how = How.ID, using = "pnprev")
    private List<WebElement> previousLinkElements;

    @FindBy(how = How.ID, using = "topstuff")
    private WebElement resultsNotFoundMessageElement;


    public SearchResultsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public long findNumberOfResults() {
        webDriverWait().until(ExpectedConditions.visibilityOf(numberOfResultsElement));
        String numberOfResultsText = numberOfResultsElement.getText();
        numberOfResultsText = numberOfResultsText.substring(0, numberOfResultsText.indexOf("results"));
        String numberOfResults = numberOfResultsText.replaceAll("\\D+", "");
        return Long.parseLong(numberOfResults);
    }

    public String openLinkFromResults(int linkIndex) throws MalformedURLException {
        WebElement link = searchResultLinks.get(linkIndex);
        String href = link.getAttribute("href");
        link.click();
        URL url = new URL(href);
        String hostNameOfLink = url.getHost();
        webDriverWait().until(ExpectedConditions.urlContains(hostNameOfLink));
        return hostNameOfLink;
    }

    public boolean isNextLinkDisplayed() {
        return nextLinkElements.size() > 0;
    }

    public boolean isPreviousLinkDisplayed() {
        return previousLinkElements.size() > 0;
    }

    /**
     * Click on next link until next link is disappeared
     */
    private void browseThroughResultPages(List<WebElement> links, WebElement link1, WebElement link2) {
        boolean iterate = true;
        while (iterate) {
            link1.click();
            webDriverWait().until(ExpectedConditions.visibilityOf(link2));
            if (links.size() == 0) {
                iterate = false;
            }
        }
    }

    public void browseForwardThroughResultPages() {
        browseThroughResultPages(nextLinkElements, nextLink, previousLink);
    }

    public void browseBackwardThroughResultPages() {
        browseThroughResultPages(previousLinkElements, previousLink, nextLink);
    }

    public String getResultsNotFoundMessage() {
        return resultsNotFoundMessageElement.getText();
    }
}
