package com.ordjoy.validation.impl;

import com.ordjoy.entity.Mix;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;

import static com.ordjoy.util.ErrorConstUtils.*;

public class MixValidator implements Validator<Mix> {

    private static final String NAME_REGEX = "^[A-Za-zА-Яа-я].{1,512}.$";
    private static final String DESCRIPTION_REGEX = "[A-Za-zА-Яа-я].{2,512}";
    private static final MixValidator INSTANCE = new MixValidator();

    private MixValidator() {

    }

    /**
     * @return {@link MixValidator} instance
     */
    public static MixValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(Mix mix) {
        ValidationResult validationResult = new ValidationResult();
        if (mix != null) {
            if (!isNameValid(mix.getName())) {
                validationResult.add(Error.of(MIX_NAME_INVALID, MIX_NAME_INVALID_MESSAGE));
            }
            if (!isDescriptionValid(mix.getDescription())) {
                validationResult.add(Error.of(DESCRIPTION_INVALID, DESCRIPTION_INVALID_MESSAGE));
            }
        } else {
            validationResult.add(Error.of(MIX_INVALID, MIX_INVALID_MESSAGE));
        }
        return validationResult;
    }

    private boolean isNameValid(String title) {
        return title != null && title.matches(NAME_REGEX);
    }

    private boolean isDescriptionValid(String description) {
        return description != null && description.matches(DESCRIPTION_REGEX);
    }
}