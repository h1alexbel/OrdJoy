package com.ordjoy.exception;

import java.io.IOException;

public class ControllerException extends IOException {

    /**
     * Creates a new ControllerException with no cause and detail message
     */
    public ControllerException() {
        super();
    }

    /**
     * Creates a new ControllerException with message
     * @param message Exception message
     */
    public ControllerException(String message) {
        super(message);
    }

    /**
     * Creates a new ControllerException with message and cause
     * @param message Exception message
     * @param cause the cause of thrown Exception
     */
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new ControllerException with cause
     * @param cause the cause of thrown Exception
     */
    public ControllerException(Throwable cause) {
        super(cause);
    }
}