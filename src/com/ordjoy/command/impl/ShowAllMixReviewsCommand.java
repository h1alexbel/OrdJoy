package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dao.filter.ReviewFilter;
import com.ordjoy.dto.MixReviewDto;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.ReviewService;
import com.ordjoy.util.JspFormatHelper;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class ShowAllMixReviewsCommand implements FrontCommand {

    private static final String MIX_REVIEWS_ATTRIBUTE = "mixReviews";
    private final ReviewService reviewService = ReviewService.getInstance();
    private final ReviewFilter reviewFilter = new ReviewFilter(20, 0, "", "");

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            List<MixReviewDto> mixReviews = reviewService.findAllMixReviewsWithLimitAndOffset(reviewFilter);
            httpServletRequest.setAttribute(MIX_REVIEWS_ATTRIBUTE, mixReviews);
            page = SEPARATOR + JspFormatHelper.getUserPath(MIX_REVIEWS_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
            return frontCommandResult;
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}