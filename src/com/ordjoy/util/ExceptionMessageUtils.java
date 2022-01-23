package com.ordjoy.util;

public final class ExceptionMessageUtils {

    public static final String DAO_LAYER_EXCEPTION_MESSAGE = "Dao layer error";
    public static final String DATABASE_LAYER_EXCEPTION_MESSAGE = "Database layer error";
    public static final String SERVICE_LAYER_EXCEPTION_MESSAGE = "Service layer error";
    public static final String VALIDATION_EXCEPTION_MESSAGE = "Results are invalid";
    public static final String CONTROLLER_EXCEPTION_MESSAGE = "Controller error was thrown while command processing";

    private ExceptionMessageUtils() {
        throw new UnsupportedOperationException();
    }
}