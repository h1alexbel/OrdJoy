package com.ordjoy.validation;

import com.ordjoy.entity.Album;
import com.ordjoy.validation.impl.AlbumValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumValidatorTest {

    @Test
    public void isAlbumValid() {
        AlbumValidator albumValidator = AlbumValidator.getInstance();
        Album album = Album.builder()
                .title("Title")
                .build();
        ValidationResult validationResult = albumValidator.isValid(album);
        assertTrue(validationResult.isValid());
    }

    @Test
    public void isAlbumValidatorDoesntThrowException() {
        AlbumValidator albumValidator = AlbumValidator.getInstance();
        assertDoesNotThrow(() -> albumValidator.isValid(null));
    }

    @Test
    public void albumTitleIsEmptyCase() {
        String title = "";
        AlbumValidator albumValidator = AlbumValidator.getInstance();
        Album album = Album.builder()
                .title(title)
                .build();
        ValidationResult validationResult = albumValidator.isValid(album);
        assertFalse(validationResult.isValid(), "Title can not be empty!");
    }

    @Test
    public void albumTitleIsNullCase() {
        AlbumValidator albumValidator = AlbumValidator.getInstance();
        Album album = Album.builder()
                .title(null)
                .build();
        ValidationResult validationResult = albumValidator.isValid(album);
        assertFalse(validationResult.isValid(), "Title can not be null!");
    }

    @Test
    public void albumValidatorTitleMethodDoesntThrowException() {
        AlbumValidator albumValidator = AlbumValidator.getInstance();
        assertDoesNotThrow(() -> albumValidator.isTitleValid(null));
    }
}