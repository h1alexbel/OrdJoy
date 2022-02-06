package com.ordjoy.exception;

import java.io.IOException;

public class ServiceException extends IOException {

    /**
     * Creates a new ServiceException with no cause and detail message
     */
    public ServiceException() {
    }

    /**
     * Creates a new ServiceException with message
     * @param message Exception message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Creates a new ServiceException with message and cause
     * @param message Exception message
     * @param cause the cause of thrown Exception
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new ServiceException with cause
     * @param cause the cause of thrown Exception
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}