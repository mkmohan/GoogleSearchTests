package com.google.util;

import java.io.InputStream;
import java.util.Properties;

public class TestProperties {

    private static final String PROPERTY_FILE = "environment.properties";

    private Properties properties;
    private static TestProperties testProperties = new TestProperties();

    private TestProperties() {
        properties = new Properties();

        try (InputStream inputStream = TestProperties.class.getClassLoader().getResourceAsStream(PROPERTY_FILE)) {
            properties.load(inputStream);
        } catch (Exception exc) {
            throw new RuntimeException(String.format("Property file: %s could not be loaded", PROPERTY_FILE));
        }

    }

    public static TestProperties instance() {
        return testProperties;
    }

    public String getProperty(String propertyName) {
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue == null) {
            throw new RuntimeException(String.format("propertyValue is not found for propertyName: %s", propertyName));
        }
        return propertyValue;
    }


}
