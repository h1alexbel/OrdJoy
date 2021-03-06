package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dto.OrderDto;
import com.ordjoy.dto.TrackDto;
import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.Order;
import com.ordjoy.entity.Track;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.OrderService;
import com.ordjoy.service.TrackService;
import com.ordjoy.service.UserService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.LogginUtils;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.OrderValidator;
import com.ordjoy.validation.impl.TrackValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class MakeOrderCommand implements FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(MakeOrderCommand.class);
    private final OrderValidator orderValidator = OrderValidator.getInstance();
    private final OrderService orderService = OrderService.getInstance();
    private final TrackService trackService = TrackService.getInstance();
    private final TrackValidator trackValidator = TrackValidator.getInstance();
    private static final String SESSION_USER = "user";
    private static final String SESSION_ORDER = "order";
    private static final String TRACK_TITLE = "trackTitle";
    private static final String PRICE = "price";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        String price = httpServletRequest.getParameter(PRICE);
        UserAccountDto user = (UserAccountDto) httpServletRequest.getSession().getAttribute(SESSION_USER);
        UserService userService = UserService.getInstance();
        String title = httpServletRequest.getParameter(TRACK_TITLE);
        try {
            Optional<TrackDto> maybeTrack = trackService.findByTrackTitle(title);
            if (maybeTrack.isPresent()) {
                TrackDto trackDto = maybeTrack.get();
                String url = trackDto.getUrl();
                String trackTitle = trackDto.getTitle();
                Track track = trackService.buildTrack(url, trackTitle, trackDto.getAlbum().getTitle());
                UserAccount userAccount = userService.buildUserAccountFromSession(user);
                if (orderValidator.isPriceValid(price)) {
                    Order order = orderService.buildOrder(new BigDecimal(price), userAccount, track);
                    ValidationResult trackValidationResult = trackValidator.isValid(track);
                    if (trackValidationResult.isValid()) {
                        ValidationResult orderValidationResult = orderValidator.isValid(order);
                        if (orderValidationResult.isValid()) {
                            OrderDto orderDto = orderService.makeOrder(order);
                            BigDecimal afterDiscountPrice = orderService.calculateOrderPrice(order.getId());
                            orderService.updateOrderPrice(afterDiscountPrice, order.getId());
                            httpServletRequest.getSession().setAttribute(SESSION_ORDER, orderDto);
                            page = httpServletRequest.getContextPath() + JspFormatHelper.getUserPath((THANKS_FOR_ORDER_PAGE));
                        } else {
                            page = httpServletRequest.getHeader(REFERER_HEADER);
                        }
                    } else {
                        page = httpServletRequest.getHeader(REFERER_HEADER);
                    }
                } else {
                    page = httpServletRequest.getHeader(REFERER_HEADER);
                }
            } else {
                page = httpServletRequest.getHeader(REFERER_HEADER);
            }
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
            return frontCommandResult;
        } catch (ServiceException e) {
            LOGGER.warn(LogginUtils.MAKE_ORDER_WARN, e);
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}