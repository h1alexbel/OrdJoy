package com.ordjoy.util;

public final class JspFormatHelper {

    private static final String SUFFIX = ".jsp";
    private static final String USER_PREFIX = "jsp/user/";
    private static final String ADMIN_PREFIX = "jsp/admin/";

    private JspFormatHelper() {
        throw new UnsupportedOperationException();
    }

    public static String getPublicPath(String jspName) {
        return jspName + SUFFIX;
    }

    public static String getUserPath(String jspName) {
        return USER_PREFIX + jspName + SUFFIX;
    }

    public static String getAdminPath(String jspName) {
        return ADMIN_PREFIX + jspName + SUFFIX;
    }
}