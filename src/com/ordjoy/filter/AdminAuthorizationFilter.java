package com.ordjoy.filter;

import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/html/admin/*")
public class AdminAuthorizationFilter implements Filter {

    private static final String LOGIN_PAGE = "login.html";
    private static final String USER_ATTRIBUTE = "user";

    @Override
    public void doFilter
            (ServletRequest servletRequest,
             ServletResponse servletResponse,
             FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (isAuthorized(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            moveToLoginPage(httpServletRequest, httpServletResponse);
        }
    }

    private void moveToLoginPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
    }

    private boolean isAuthorized(HttpServletRequest httpServletRequest) {
        boolean result = false;
        UserAccountDto userAccount = (UserAccountDto) httpServletRequest.getSession().getAttribute(USER_ATTRIBUTE);
        if (userAccount != null && userAccount.getRole() == UserRole.ADMIN_ROLE) {
            result = true;
        }
        return result;
    }
}