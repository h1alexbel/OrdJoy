package com.ordjoy.util;

public final class JspFormatHelper {

    private static final String SUFFIX = ".jsp";
    private static final String USER_PREFIX = "jsp/user/";
    private static final String ADMIN_PREFIX = "jsp/admin/";
    private static final String DEFAULT_IF_NULL = "";

    private JspFormatHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets public path with no prefix and with .jsp suffix
     *
     * @param jspName name of the jsp page
     * @return
     */
    public static String getPublicPath(String jspName) {
        if (jspName != null) {
            return jspName + SUFFIX;
        }
        return DEFAULT_IF_NULL;
    }

    /**
     * Gets user path with user prefix and with .jsp suffix
     *
     * @param jspName name of the jsp page
     * @return
     */
    public static String getUserPath(String jspName) {
        if (jspName != null) {
            return USER_PREFIX + jspName + SUFFIX;
        }
        return DEFAULT_IF_NULL;
    }

    /**
     * Gets admin path with admin prefix and with .jsp suffix
     *
     * @param jspName name of the jsp page
     * @return
     */
    public static String getAdminPath(String jspName) {
        if (jspName != null) {
            return ADMIN_PREFIX + jspName + SUFFIX;
        }
        return DEFAULT_IF_NULL;
    }
}