package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dao.filter.ReviewFilter;
import com.ordjoy.dto.TrackReviewDto;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.ReviewService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static com.ordjoy.util.JspPageConst.*;

public class ShowAllTrackReviewsCommand implements FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowAllTrackReviewsCommand.class);
    private final ReviewService reviewService = ReviewService.getInstance();
    private static final String TRACK_REVIEWS_ATTRIBUTE = "trackReviews";
    private static final String OFFSET = "offset";
    private static final int DEFAULT_LIMIT = 20;
    private static final String DEFAULT_REVIEW_TEXT = "";
    private static final String DEFAULT_USER_ACCOUNT_LOGIN = "";
    private static final String NO_OF_PAGES = "noOfPages";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            int offset = Integer.parseInt(httpServletRequest.getParameter(OFFSET));
            Long records = reviewService.getTrackReviewRecords();
            ReviewFilter reviewFilter = new ReviewFilter(
                    DEFAULT_LIMIT,
                    offset,
                    DEFAULT_REVIEW_TEXT,
                    DEFAULT_USER_ACCOUNT_LOGIN);
            List<TrackReviewDto> trackReviews = reviewService.findAllTrackReviewsWithLimitAndOffset(reviewFilter);
            int noOfPages = (int) (records / DEFAULT_LIMIT);
            httpServletRequest.setAttribute(NO_OF_PAGES, noOfPages);
            httpServletRequest.setAttribute(TRACK_REVIEWS_ATTRIBUTE, trackReviews);
            page = SEPARATOR + JspFormatHelper.getUserPath(TRACK_REVIEWS_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
            return frontCommandResult;
        } catch (ServiceException e) {
            LOGGER.warn(LogginUtils.SHOW_ALL_WARN, e);
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}