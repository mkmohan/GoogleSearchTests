package com.google;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features"}, tags = {"@googleSearch"}, plugin = {
        "pretty", "html:target/reports/html",
        "junit:target/reports/junitReports.xml"})
public class CucumberRunnerTest {
}
