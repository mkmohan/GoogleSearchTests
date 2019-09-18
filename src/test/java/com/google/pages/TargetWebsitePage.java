package com.google.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class TargetWebsitePage extends BasePage {

    public TargetWebsitePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
