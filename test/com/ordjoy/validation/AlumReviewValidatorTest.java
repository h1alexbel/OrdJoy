package com.ordjoy.validation;

import com.ordjoy.entity.Album;
import com.ordjoy.entity.AlbumReview;
import com.ordjoy.validation.impl.AlbumReviewValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlumReviewValidatorTest {

    @Test
    public void isAlbumReviewValidatorDoesntThrowException() {
        AlbumReviewValidator albumReviewValidator = AlbumReviewValidator.getInstance();
        assertDoesNotThrow(() -> albumReviewValidator.isValid(null),
                "Album Review can be not null!");
    }

    @Test
    public void reviewTextIsEmptyCase() {
        String reviewText = "";
        AlbumReviewValidator albumReviewValidator = AlbumReviewValidator.getInstance();
        AlbumReview albumReview = AlbumReview.builder()
                .album(Album.builder()
                        .title("title")
                        .build())
                .reviewText(reviewText)
                .build();
        ValidationResult validationResult = albumReviewValidator.isValid(albumReview);
        assertFalse(validationResult.isValid(), "Review Text can not be empty!");
    }

    @Test
    public void reviewTextIsNullCase() {
        AlbumReviewValidator albumReviewValidator = AlbumReviewValidator.getInstance();
        AlbumReview albumReview = AlbumReview.builder()
                .album(Album.builder()
                        .title("title")
                        .build())
                .reviewText(null)
                .build();
        ValidationResult validationResult = albumReviewValidator.isValid(albumReview);
        assertFalse(validationResult.isValid(), "Review Text can not be null!");
    }
}