package com.google.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base class for all page objects
 */
public abstract class BasePage {

    protected WebDriver driver;
    private static final int TIME_OUT_DEFAULT = 30;//seconds

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected WebDriverWait webDriverWait(int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        return wait;
    }

    protected WebDriverWait webDriverWait() {
        WebDriverWait wait = new WebDriverWait(driver, getTimeOut());
        return wait;
    }

    protected int getTimeOut() {
        String timeOutString = System.getProperty("timeOut");
        int timeOut = TIME_OUT_DEFAULT;
        if (timeOutString != null) {
            try {
                timeOut = Integer.parseInt(timeOutString);
            } catch (NumberFormatException exc) {
                timeOut = TIME_OUT_DEFAULT;
            }
//            System.out.println("timeOut: " + timeOut);
        }
        return timeOut;
    }

}
