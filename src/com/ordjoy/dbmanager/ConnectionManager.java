package com.ordjoy.dbmanager;

import com.ordjoy.exception.DataBaseException;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.ordjoy.util.ExceptionMessageUtils.*;

public final class ConnectionManager {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionManager.class);
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    private ConnectionManager() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return {@link ProxyConnection} is a connection wrapper to connect with database.
     * @throws DataBaseException if database access error occurs while you try to get connection
     */
    public static ProxyConnection getProxyConnection() {
        try {
            return new ProxyConnection
                    (DriverManager.getConnection(PropertiesManager.getPropertyByKey(URL_KEY),
                            PropertiesManager.getPropertyByKey(USERNAME_KEY),
                            PropertiesManager.getPropertyByKey(PASSWORD_KEY)));
        } catch (SQLException e) {
            LOGGER.fatal(LogginUtils.CONNECTION_MANAGER_FATAL, e);
            throw new DataBaseException(DATABASE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }
}