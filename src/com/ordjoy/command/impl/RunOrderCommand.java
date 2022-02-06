package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.entity.OrderStatus;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.OrderService;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static com.ordjoy.util.JspPageConst.REFERER_HEADER;

public class RunOrderCommand implements FrontCommand {

    private static final String ORDER_ID = "orderId";
    private final OrderService orderService = OrderService.getInstance();

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        Long id = Long.valueOf(httpServletRequest.getParameter(ORDER_ID));
        try {
            orderService.updateOrderStatus(OrderStatus.IN_PROGRESS, id);
            page = httpServletRequest.getHeader(REFERER_HEADER);
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
            return frontCommandResult;
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}