package com.ordjoy.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JspFormatHelperTest {

    @Test
    void getPublicPath() {
        String actualPath = JspFormatHelper.getPublicPath("test");
        String expectedPath = "test.jsp";
        assertEquals(expectedPath, actualPath, "Example of public path: test.jsp");
    }

    @Test
    void getUserPath() {
        String actualUserPath = JspFormatHelper.getUserPath("test");
        String expectedUserPath = "jsp/user/test.jsp";
        assertEquals(expectedUserPath, actualUserPath, "Example of user path: jsp/user/test.jsp");
    }

    @Test
    void getAdminPath() {
        String actualAdminPath = JspFormatHelper.getAdminPath("test");
        String expectedAdminPath = "jsp/admin/test.jsp";
        assertEquals(expectedAdminPath, actualAdminPath, "Example of admin path: jsp/admin/test.jsp");
    }

    @Test
    void getPublicPathNullCase() {
        assertDoesNotThrow(() -> JspFormatHelper.getPublicPath(null));
    }

    @Test
    void getUserPathNullCase() {
        assertDoesNotThrow(() -> JspFormatHelper.getUserPath(null));
    }

    @Test
    void getAdminPathNullCase() {
        assertDoesNotThrow(() -> JspFormatHelper.getAdminPath(null));
    }
}