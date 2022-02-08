package com.ordjoy.validation.impl;

import com.ordjoy.entity.Album;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.RegexBase;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;

import static com.ordjoy.util.ErrorConstUtils.*;

public class AlbumValidator implements Validator<Album> {

    private static final AlbumValidator INSTANCE = new AlbumValidator();

    private AlbumValidator() {

    }

    /**
     * @return {@link AlbumValidator} instance
     */
    public static AlbumValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(Album album) {
        ValidationResult validationResult = new ValidationResult();
        if (album != null) {
            if (!isTitleValid(album.getTitle())) {
                validationResult.add(Error.of(TITLE_INVALID, ALBUM_TITLE_INVALID_MESSAGE));
            }
        } else {
            validationResult.add(Error.of(ALBUM_INVALID, ALBUM_INVALID_MESSAGE));
        }
        return validationResult;
    }

    /** Checks valid or not title is
     * @param title title to validate
     * @return boolean result based on not null check and matching to title regex
     */
    public boolean isTitleValid(String title) {
        return title != null && title.matches(RegexBase.TITLE_REGEX);
    }
}