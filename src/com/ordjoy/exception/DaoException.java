package com.ordjoy.exception;

import java.io.IOException;

public class DaoException extends IOException {

    /**
     * Creates a new DaoException with no cause and detail message
     */
    public DaoException() {
    }

    /**
     * Creates a new DaoException with message
     * @param message Exception message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Creates a new DaoException with message and cause
     * @param message Exception message
     * @param cause the cause of thrown Exception
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new DaoException with cause
     * @param cause the cause of thrown Exception
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}