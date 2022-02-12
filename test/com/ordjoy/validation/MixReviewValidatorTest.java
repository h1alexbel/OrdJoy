package com.ordjoy.validation;

import com.ordjoy.entity.Mix;
import com.ordjoy.entity.MixReview;
import com.ordjoy.validation.impl.MixReviewValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MixReviewValidatorTest {

    @Test
    public void isMixReviewValidatorDoesntThrowException() {
        MixReviewValidator mixReviewValidator = MixReviewValidator.getInstance();
        assertDoesNotThrow(() -> mixReviewValidator.isValid(null),
                "Mix Review can not be null!");
    }

    @Test
    public void reviewTextIsEmptyCase() {
        String reviewText = "";
        MixReviewValidator mixReviewValidator = MixReviewValidator.getInstance();
        MixReview mixReview = MixReview.builder()
                .mix(Mix.builder()
                        .description("Description")
                        .name("Test")
                        .build())
                .reviewText(reviewText)
                .build();
        ValidationResult validationResult = mixReviewValidator.isValid(mixReview);
        assertFalse(validationResult.isValid(), "Review Text can not be empty!");
    }

    @Test
    public void reviewTextIsNullCase() {
        MixReviewValidator mixReviewValidator = MixReviewValidator.getInstance();
        MixReview mixReview = MixReview.builder()
                .mix(Mix.builder()
                        .description("Description")
                        .name("Test")
                        .build())
                .reviewText(null)
                .build();
        ValidationResult validationResult = mixReviewValidator.isValid(mixReview);
        assertFalse(validationResult.isValid(), "Review Text can not be null!");
    }
}