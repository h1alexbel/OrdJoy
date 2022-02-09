package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dao.filter.OrderFilter;
import com.ordjoy.dto.OrderDto;
import com.ordjoy.entity.OrderStatus;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.OrderService;
import com.ordjoy.util.JspFormatHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.ALL_ORDERS;
import static com.ordjoy.util.JspPageConst.SEPARATOR;

public class ShowAllOrdersCommand implements FrontCommand {

    private static final String ORDERS_ATTRIBUTE = "orders";
    private static final int DEFAULT_LIMIT = 20;
    private static final String OFFSET = "offset";
    private static final String NO_OF_PAGES = "noOfPages";
    private final OrderService orderService = OrderService.getInstance();

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            int offset = Integer.parseInt(httpServletRequest.getParameter(OFFSET));
            Long records = orderService.getRecords();
            OrderFilter filter = new OrderFilter(DEFAULT_LIMIT, offset, null, OrderStatus.ACCEPTED);
            List<OrderDto> orders = orderService.findAllOrdersWithLimitAndOffset(filter);
            int noOfPages = (int) (records / DEFAULT_LIMIT);
            httpServletRequest.setAttribute(NO_OF_PAGES, noOfPages);
            httpServletRequest.setAttribute(ORDERS_ATTRIBUTE, orders);
            page = SEPARATOR + JspFormatHelper.getAdminPath(ALL_ORDERS);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}