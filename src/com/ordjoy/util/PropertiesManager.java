package com.ordjoy.util;

import com.ordjoy.exception.DBException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesManager {

    private static final Properties PROPERTIES = new Properties();
    private static final String ROOT = "database/application.properties";

    static {
        loadProperties();
    }

    private PropertiesManager() {
        throw new UnsupportedOperationException();
    }

    private static void loadProperties() {
        try (InputStream stream = PropertiesManager.class
                .getClassLoader()
                .getResourceAsStream(ROOT)) {
            PROPERTIES.load(stream);
        } catch (IOException e) {
            throw new DBException(e);
        }
    }

    public static String getPropertyByKey(String key) {
        return PROPERTIES.getProperty(key);
    }
}