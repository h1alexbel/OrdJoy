package com.ordjoy.validation.impl;

import com.ordjoy.entity.MixReview;
import com.ordjoy.util.LogginUtils;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.RegexBase;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ordjoy.util.ErrorConstUtils.*;
import static com.ordjoy.util.ErrorConstUtils.MIX_REVIEW_INVALID_MESSAGE;

public class MixReviewValidator implements Validator<MixReview> {

    private static final Logger LOGGER = LogManager.getLogger(MixReviewValidator.class);
    private static final MixReviewValidator INSTANCE = new MixReviewValidator();

    private MixReviewValidator() {

    }

    /**
     * @return {@link MixReviewValidator} instance
     */
    public static MixReviewValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(MixReview mixReview) {
        ValidationResult validationResult = new ValidationResult();
        if (mixReview != null) {
            if (!isReviewTextValid(mixReview.getReviewText())) {
                validationResult.add(Error.of(REVIEW_TEXT_INVALID, REVIEW_TEXT_INVALID_MESSAGE));
                LOGGER.info(LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
            }
        } else {
            validationResult.add(Error.of(MIX_REVIEW_INVALID, MIX_REVIEW_INVALID_MESSAGE));
            LOGGER.info(LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
        }
        return validationResult;
    }

    private boolean isReviewTextValid(String reviewText) {
        return reviewText != null && reviewText.matches(RegexBase.REVIEW_REGEX);
    }
}