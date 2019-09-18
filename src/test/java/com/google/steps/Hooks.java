package com.google.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static Logger logger = LoggerFactory.getLogger(Hooks.class);

    /**
     * The static driver is initialised only once before first test
     * It is closed after all tests
     */
    private static WebDriver driver;

    /**
     * Cucumber does not provide before-all, after-all hooks
     * To close driver after all tests, add shut-down-hook to java runtime. ie. The driver is closed before jvm shuts down.
     */
    static {
        Runtime.getRuntime().addShutdownHook(

                new Thread() {
                    @Override
                    public void run() {
                        if (driver != null) {
                            driver.manage().deleteAllCookies();
                            driver.quit();
                            driver = null;
                        }
                    }
                }
        );
    }

    /**
     * Create webdriver object based on "browser" system property
     * The default browser is: ChromeBrowser
     */
    private void createDriver() {
        String browser = System.getProperty("browser");
        logger.debug("browser: " + browser);
        if ("ie".equalsIgnoreCase(browser)) {
            createIEDriver();
        } else {
            createChromeDriver();
        }
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    /**
     * WebDriverManager automatically downloads chromedriver binary file and sets the system-property (webdriver.chrome.driver)
     */
    private void createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();// decide it from env

    }

    private void createIEDriver() {
        WebDriverManager.iedriver().setup();
        driver = new InternetExplorerDriver();
    }

    /**
     * This method is run before each test
     * Open browser before first test.
     */
    @Before
    public void openDriver() {
        if (driver == null) {
            logger.debug("Creating driver");
            createDriver();
            logger.debug("Driver created");
        }

    }

    /**
     * This method is run after each test
     * Delete all cookies from browser after each test
     * If test fails, save the browser screenshot to reports
     */
    @After
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png"); // stick it in the report
            scenario.write("URL at failure: " + driver.getCurrentUrl());
        }

        driver.manage().deleteAllCookies();
    }

    public static WebDriver getDriver() {
        return driver;
    }

}
