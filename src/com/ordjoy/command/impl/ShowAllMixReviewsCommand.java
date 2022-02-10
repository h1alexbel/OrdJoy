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
    private static final String OFFSET = "offset";
    private static final int DEFAULT_LIMIT = 20;
    private static final String NO_OF_PAGES = "noOfPages";
    private static final String DEFAULT_REVIEW_TEXT = "";
    private static final String DEFAULT_USER_ACCOUNT_LOGIN = "";
    private final ReviewService reviewService = ReviewService.getInstance();

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            int offset = Integer.parseInt(httpServletRequest.getParameter(OFFSET));
            Long records = reviewService.getMixReviewRecords();
            ReviewFilter reviewFilter = new ReviewFilter(
                    DEFAULT_LIMIT,
                    offset,
                    DEFAULT_REVIEW_TEXT,
                    DEFAULT_USER_ACCOUNT_LOGIN);
            List<MixReviewDto> mixReviews = reviewService.findAllMixReviewsWithLimitAndOffset(reviewFilter);
            int noOfPages = (int) (records / DEFAULT_LIMIT);
            httpServletRequest.setAttribute(NO_OF_PAGES, noOfPages);
            httpServletRequest.setAttribute(MIX_REVIEWS_ATTRIBUTE, mixReviews);
            page = SEPARATOR + JspFormatHelper.getUserPath(MIX_REVIEWS_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
            return frontCommandResult;
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}