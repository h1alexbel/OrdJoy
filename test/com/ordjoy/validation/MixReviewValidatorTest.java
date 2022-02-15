package com.ordjoy.validation;

import com.ordjoy.entity.Mix;
import com.ordjoy.entity.MixReview;
import com.ordjoy.validation.impl.MixReviewValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class MixReviewValidatorTest {

    @ParameterizedTest
    @NullSource
    @DisplayName("Mix review null case test")
    void isMixReviewValidatorDoesntThrowException(MixReview mixReview) {
        MixReviewValidator mixReviewValidator = MixReviewValidator.getInstance();
        assertDoesNotThrow(() -> mixReviewValidator.isValid(mixReview),
                "Mix Review can not be null!");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("review text empty and null case test")
    void reviewTextIsEmptyAndNullCase(String reviewText) {
        MixReviewValidator mixReviewValidator = MixReviewValidator.getInstance();
        MixReview mixReview = MixReview.builder()
                .mix(Mix.builder()
                        .description("Description")
                        .name("Test")
                        .build())
                .reviewText(reviewText)
                .build();
        ValidationResult validationResult = mixReviewValidator.isValid(mixReview);
        assertFalse(validationResult.isValid(), "Review Text can not be empty or null!");
    }
}