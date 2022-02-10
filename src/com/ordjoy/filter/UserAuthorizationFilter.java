package com.ordjoy.filter;

import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.UserRole;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ordjoy.util.JspPageConst.*;

@WebFilter(urlPatterns = {"/jsp/user/*"})
public class UserAuthorizationFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(UserAuthorizationFilter.class);
    private static final String USER_ATTRIBUTE = "user";

    @Override
    public void doFilter
            (ServletRequest servletRequest,
             ServletResponse servletResponse,
             FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (isAuthorized(httpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            moveToLoginPage(httpServletRequest, httpServletResponse);
        }
    }

    private void moveToLoginPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE_FOR_FILTER);
    }

    private boolean isAuthorized(HttpServletRequest httpServletRequest) {
        boolean result = false;
        UserAccountDto userAccount = (UserAccountDto) httpServletRequest.getSession().getAttribute(USER_ATTRIBUTE);
        if (userAccount != null && userAccount.getRole() == UserRole.CLIENT_ROLE) {
            result = true;
            LOGGER.log(Level.INFO, LogginUtils.USER_ROLE_INFO, userAccount.getRole());
        }
        return result;
    }
}