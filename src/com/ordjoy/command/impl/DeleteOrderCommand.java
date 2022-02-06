package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.OrderService;
import com.ordjoy.util.ExceptionMessageUtils;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.JspPageConst;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static com.ordjoy.util.JspPageConst.*;

public class DeleteOrderCommand implements FrontCommand {

    private static final String ORDER_ID = "orderId";
    private final OrderService orderService = OrderService.getInstance();

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        Long id = Long.valueOf(httpServletRequest.getParameter(ORDER_ID));
        try {
            boolean isDeleted = orderService.deleteOrderById(id);
            if (isDeleted) {
                page = httpServletRequest.getHeader(REFERER_HEADER);
            } else {
                page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_MAIN_PAGE);
            }
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
            return frontCommandResult;
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}