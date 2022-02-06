package com.ordjoy.util;

public final class JspFormatHelper {

    private static final String SUFFIX = ".jsp";
    private static final String USER_PREFIX = "jsp/user/";
    private static final String ADMIN_PREFIX = "jsp/admin/";

    private JspFormatHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets public path with no prefix and with .jsp suffix
     * @param jspName name of the jsp page
     * @return
     */
    public static String getPublicPath(String jspName) {
        return jspName + SUFFIX;
    }

    /**
     * Gets user path with user prefix and with .jsp suffix
     * @param jspName name of the jsp page
     * @return
     */
    public static String getUserPath(String jspName) {
        return USER_PREFIX + jspName + SUFFIX;
    }

    /**
     * Gets admin path with admin prefix and with .jsp suffix
     * @param jspName name of the jsp page
     * @return
     */
    public static String getAdminPath(String jspName) {
        return ADMIN_PREFIX + jspName + SUFFIX;
    }
}