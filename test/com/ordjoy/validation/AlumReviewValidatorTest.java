package com.ordjoy.validation;

import com.ordjoy.entity.Album;
import com.ordjoy.entity.AlbumReview;
import com.ordjoy.validation.impl.AlbumReviewValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class AlumReviewValidatorTest {

    @ParameterizedTest
    @NullSource
    @DisplayName("album review null case")
    void isAlbumReviewValidatorDoesntThrowException(AlbumReview albumReview) {
        AlbumReviewValidator albumReviewValidator = AlbumReviewValidator.getInstance();
        assertDoesNotThrow(() -> albumReviewValidator.isValid(albumReview),
                "Album Review can be not null!");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("review text empty and null case test")
    void reviewTextIsEmptyAndNullCase(String reviewText) {
        AlbumReviewValidator albumReviewValidator = AlbumReviewValidator.getInstance();
        AlbumReview albumReview = AlbumReview.builder()
                .album(Album.builder()
                        .title("title")
                        .build())
                .reviewText(reviewText)
                .build();
        ValidationResult validationResult = albumReviewValidator.isValid(albumReview);
        assertFalse(validationResult.isValid(), "Review Text can not be empty or null!");
    }
}