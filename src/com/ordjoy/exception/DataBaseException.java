package com.ordjoy.exception;

public class DataBaseException extends RuntimeException {

    /**
     * Creates a new DaoException with no cause and detail message
     */
    public DataBaseException() {
    }

    /**
     * Creates a new DataBaseException with message
     * @param message Exception message
     */
    public DataBaseException(String message) {
        super(message);
    }

    /**
     * Creates a new DataBaseException with message and cause
     * @param message Exception message
     * @param cause the cause of thrown Exception
     */
    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new DataBaseException with cause
     * @param cause the cause of thrown Exception
     */
    public DataBaseException(Throwable cause) {
        super(cause);
    }
}