package com.ordjoy.dbmanager;

import com.ordjoy.exception.DataBaseException;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.ordjoy.util.ExceptionMessageUtils.*;

public final class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    private ConnectionManager() {
        throw new UnsupportedOperationException();
    }

    public static ProxyConnection getProxyConnection() {
        try {
            return new ProxyConnection
                    (DriverManager.getConnection(PropertiesManager.getPropertyByKey(URL_KEY),
                            PropertiesManager.getPropertyByKey(USERNAME_KEY),
                            PropertiesManager.getPropertyByKey(PASSWORD_KEY)));
        } catch (SQLException e) {
            throw new DataBaseException(DATABASE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }
}