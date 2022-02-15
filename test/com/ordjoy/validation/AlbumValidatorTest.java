package com.ordjoy.validation;

import com.ordjoy.entity.Album;
import com.ordjoy.validation.impl.AlbumValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class AlbumValidatorTest {

    @Test
    @DisplayName("album validation test")
    void isAlbumValid() {
        AlbumValidator albumValidator = AlbumValidator.getInstance();
        Album album = Album.builder()
                .title("Title")
                .build();
        ValidationResult validationResult = albumValidator.isValid(album);
        assertTrue(validationResult.isValid());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("album null case test")
    void isAlbumValidatorDoesntThrowException(Album album) {
        AlbumValidator albumValidator = AlbumValidator.getInstance();
        assertDoesNotThrow(() -> albumValidator.isValid(album));
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    @DisplayName("empty and null title case test")
    void albumTitleIsEmptyCase(String title) {
        AlbumValidator albumValidator = AlbumValidator.getInstance();
        Album album = Album.builder()
                .title(title)
                .build();
        ValidationResult validationResult = albumValidator.isValid(album);
        assertFalse(validationResult.isValid(), "Title can not be empty!");
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("title null case test")
    void albumValidatorTitleMethodDoesntThrowException(String title) {
        AlbumValidator albumValidator = AlbumValidator.getInstance();
        assertDoesNotThrow(() -> albumValidator.isTitleValid(title));
    }
}