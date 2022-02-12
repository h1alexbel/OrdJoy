package com.ordjoy.util;

public final class LogginUtils {

    public static final String CONNECTION_MANAGER_FATAL = "Can not get connection!";
    public static final String DRIVER_FATAL = "Driver registration failed!";
    public static final String AUTO_COMMIT_ERROR = "Can not set autocommit mode!";
    public static final String CLOSE_CONNECTION_POOL_FATAL = "Can not close ConnectionPool!";
    public static final String PROPERTIES_FATAL = "Can not load Properties file!";
    public static final String PASSWORD_ENCODING_ERROR = "Can not encode password!";
    public static final String REQUEST_PARAMS = "Request params: {}";
    public static final String FRONT_COMMAND_RESULT_INFO = "FrontCommandResult info: {}";
    public static final String USER_ROLE_INFO = "User role: {}";
    public static final String VALIDATION_FAILED = "Validation failed! Errors: {}";
    public static final String USERNAME_EXISTS = "This username is already exists!";
    public static final String EMAIL_EXISTS = "User with this email is already exists!";
    public static final String TRACK_EXISTS = "Track with this name already exists!";
    public static final String MIX_EXISTS = "Mix with this name already exists!";
    public static final String ALBUM_EXISTS = "Album with this title already exists";
    public static final String FETCH_RECORDS_ERROR = "Get records method occurs error!";
    public static final String SHOW_ALL_WARN = "Command to display lists does not work correctly!";
    public static final String LOGIN_ERROR = "Login operation does not work correctly!";
    public static final String REGISTER_ERROR = "Register operation does not work correctly!";
    public static final String MAKE_ORDER_WARN = "Make order operation does not work correctly!";
    public static final String EDIT_WARN = "Entity update operation does not work correctly!";
    public static final String DELETE_WARN = "Entity delete operation does not work correctly!";
    public static final String ADD_WARN = "Entity save operation does not work correctly!";
    public static final String RUN_ORDER_WARN = "Run order operation does not work correctly!";
    public static final String DECLINE_ORDER_WARN = "Decline order operation does not work correctly!";
    public static final String CONTROLLER_WARN = "Some error occurs when Command been executed!";

    private LogginUtils() {
        throw new UnsupportedOperationException();
    }
}