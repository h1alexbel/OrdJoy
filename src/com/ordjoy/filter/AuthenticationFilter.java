package com.ordjoy.filter;

import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.util.JspFormatHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ordjoy.util.JspPageConst.*;

@WebFilter(urlPatterns = {"/login.jsp", "/register.jsp"})
public class AuthenticationFilter implements Filter {

    private static final String USER_ATTRIBUTE = "user";

    @Override
    public void doFilter
            (ServletRequest servletRequest,
             ServletResponse servletResponse,
             FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (!isAuthorized(httpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            UserAccountDto userAccount = (UserAccountDto) httpServletRequest.getSession().getAttribute(USER_ATTRIBUTE);
            String pathToPage;
            switch (userAccount.getRole()) {
                case CLIENT_ROLE -> {
                    pathToPage = JspFormatHelper.getUserPath(USER_MAIN_PAGE);
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + pathToPage);
                }
                case ADMIN_ROLE -> {
                    pathToPage = JspFormatHelper.getAdminPath(ADMIN_MAIN_PAGE);
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + pathToPage);
                }
                default -> throw new ServletException();
            }
        }
    }

    private boolean isAuthorized(HttpServletRequest httpServletRequest) {
        UserAccountDto userAccount = (UserAccountDto) httpServletRequest.getSession().getAttribute(USER_ATTRIBUTE);
        return userAccount != null;
    }
}