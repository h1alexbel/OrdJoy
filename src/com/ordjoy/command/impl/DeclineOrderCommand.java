package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.entity.OrderStatus;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.OrderService;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class DeclineOrderCommand implements FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(DeclineOrderCommand.class);
    private final OrderService orderService = OrderService.getInstance();
    private static final String ORDER_ID = "orderId";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        Long id = Long.valueOf(httpServletRequest.getParameter(ORDER_ID));
        try {
            orderService.updateOrderStatus(OrderStatus.CANCELED, id);
            page = httpServletRequest.getHeader(REFERER_HEADER);
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
            return frontCommandResult;
        } catch (ServiceException e) {
            LOGGER.warn(LogginUtils.DECLINE_ORDER_WARN, e);
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}