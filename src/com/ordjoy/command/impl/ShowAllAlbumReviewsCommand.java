package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dao.filter.ReviewFilter;
import com.ordjoy.dto.AlbumReviewDto;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.ReviewService;
import com.ordjoy.util.JspFormatHelper;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class ShowAllAlbumReviewsCommand implements FrontCommand {

    private static final String ALBUM_REVIEWS_ATTRIBUTE = "albumReviews";
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
            Long records = reviewService.getAlbumReviewRecords();
            ReviewFilter reviewFilter = new ReviewFilter(
                    DEFAULT_LIMIT,
                    offset,
                    DEFAULT_REVIEW_TEXT,
                    DEFAULT_USER_ACCOUNT_LOGIN);
            List<AlbumReviewDto> albumReviews = reviewService.findAllAlbumReviewsWithLimitAndOffset(reviewFilter);
            int noOfPages = (int) (records / DEFAULT_LIMIT);
            httpServletRequest.setAttribute(NO_OF_PAGES, noOfPages);
            httpServletRequest.setAttribute(ALBUM_REVIEWS_ATTRIBUTE, albumReviews);
            page = SEPARATOR + JspFormatHelper.getUserPath(ALBUM_REVIEWS_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
            return frontCommandResult;
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}