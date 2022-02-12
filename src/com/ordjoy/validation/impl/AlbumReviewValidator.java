package com.ordjoy.validation.impl;

import com.ordjoy.entity.AlbumReview;
import com.ordjoy.util.LogginUtils;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.RegexBase;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ordjoy.util.ErrorConstUtils.*;

public class AlbumReviewValidator implements Validator<AlbumReview> {

    private static final Logger LOGGER = LogManager.getLogger(AlbumReviewValidator.class);
    private static final AlbumReviewValidator INSTANCE = new AlbumReviewValidator();

    private AlbumReviewValidator() {

    }

    /**
     * @return {@link AlbumReviewValidator} instance
     */
    public static AlbumReviewValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(AlbumReview albumReview) {
        ValidationResult validationResult = new ValidationResult();
        if (albumReview != null) {
            if (!isReviewTextValid(albumReview.getReviewText())) {
                validationResult.add(Error.of(REVIEW_TEXT_INVALID, REVIEW_TEXT_INVALID_MESSAGE));
                LOGGER.info(LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
            }
        } else {
            validationResult.add(Error.of(ALBUM_REVIEW_INVALID, ALBUM_REVIEW_INVALID_MESSAGE));
            LOGGER.info(LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
        }
        return validationResult;
    }

    private boolean isReviewTextValid(String reviewText) {
        return reviewText != null && reviewText.matches(RegexBase.REVIEW_REGEX);
    }
}