package com.ordjoy.util;

public final class LogginUtils {

    public static final String CONNECTION_MANAGER_ERROR = "Can not get connection!";
    public static final String DRIVER_ERROR = "Driver registration failed!";
    public static final String AUTO_COMMIT_ERROR = "Can not set autocommit mode!";
    public static final String CLOSE_CONNECTION_POOL_ERROR = "Can not close ConnectionPool!";
    public static final String PROPERTIES_ERROR = "Can not load Properties file!";
    public static final String PASSWORD_ENCODING_ERROR = "Can not encode password!";
    public static final String REQUEST_PARAMS = "Request params: {}";
    public static final String FRONT_COMMAND_RESULT_INFO = "FrontCommandResult info: {}";
    public static final String USER_ROLE_INFO = "User role: {}";
    public static final String VALIDATION_FAILED = "Validation failed! Errors: {}";
    public static final String ENTITY_SAVE_ERROR = "Entity save occurs some errors: {}";
    public static final String FETCH_RECORDS_ERROR = "Get records method occurs error!";
    public static final String ENTITY_UPDATE_ERROR = "Entity can not be updated, cause:";
    public static final String ENTITY_DELETE_WARN = "Entity can not be deleted, cause:";
    public static final String CALCULATE_ERROR = "Can not calculate!";
    public static final String LINKED_ENTITY_ERROR = "Entity can not be linked: {}";

    private LogginUtils() {
        throw new UnsupportedOperationException();
    }
}