package com.ordjoy.dbmanager;

import com.ordjoy.exception.DataBaseException;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesManager {

    private static final Logger LOGGER = LogManager.getLogger(PropertiesManager.class);
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
            LOGGER.log(Level.FATAL, LogginUtils.PROPERTIES_ERROR, e);
            throw new DataBaseException(e);
        }
    }

    /**
     * @param key key in application.properties file
     * @return Property value
     */
    public static String getPropertyByKey(String key) {
        return PROPERTIES.getProperty(key);
    }
}