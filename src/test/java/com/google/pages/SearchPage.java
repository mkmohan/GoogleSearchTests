package com.google.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchPage extends BasePage {

    @FindBy(how = How.XPATH, using = "//input[@type='text'][@name='q']")
    private WebElement searchField;

    public SearchPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void loadPage(String url) {
        driver.get(url);
    }

    public void search(String searchString) {
        searchField.sendKeys(searchString, Keys.ENTER);
        webDriverWait(20).until(ExpectedConditions.urlContains("/search"));
    }
}
