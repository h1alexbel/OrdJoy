package com.ordjoy.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class JspFormatHelperTest {

    @Test
    @DisplayName("public path test")
    void getPublicPath() {
        String actualPath = JspFormatHelper.getPublicPath("test");
        String expectedPath = "test.jsp";
        assertEquals(expectedPath, actualPath, "Example of public path: test.jsp");
    }

    @Test
    @DisplayName("user path test")
    void getUserPath() {
        String actualUserPath = JspFormatHelper.getUserPath("test");
        String expectedUserPath = "jsp/user/test.jsp";
        assertEquals(expectedUserPath, actualUserPath, "Example of user path: jsp/user/test.jsp");
    }

    @Test
    @DisplayName("admin path test")
    void getAdminPath() {
        String actualAdminPath = JspFormatHelper.getAdminPath("test");
        String expectedAdminPath = "jsp/admin/test.jsp";
        assertEquals(expectedAdminPath, actualAdminPath, "Example of admin path: jsp/admin/test.jsp");
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("public path null case test")
    void getPublicPathNullCase(String jspName) {
        assertDoesNotThrow(() -> JspFormatHelper.getPublicPath(jspName));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("user path null case test")
    void getUserPathNullCase(String jspName) {
        assertDoesNotThrow(() -> JspFormatHelper.getUserPath(jspName));
    }

    @Test
    @NullSource
    @DisplayName("admin path null case test")
    void getAdminPathNullCase(String jspName) {
        assertDoesNotThrow(() -> JspFormatHelper.getAdminPath(jspName));
    }
}