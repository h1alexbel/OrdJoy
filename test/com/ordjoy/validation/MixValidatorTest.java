package com.ordjoy.validation;

import com.ordjoy.entity.Mix;
import com.ordjoy.validation.impl.MixValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class MixValidatorTest {

    @ParameterizedTest
    @CsvSource({"Test, Description",
            "Docker, wa2",
            "name2, русс"})
    void isMixValid(String name, String description) {
        MixValidator mixValidator = MixValidator.getInstance();
        Mix mix = Mix.builder()
                .name(name)
                .description(description)
                .build();
        ValidationResult validationResult = mixValidator.isValid(mix);
        assertTrue(validationResult.isValid());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("mix null case test")
    void isMixValidatorDoesntThrowException(Mix mix) {
        MixValidator mixValidator = MixValidator.getInstance();
        assertDoesNotThrow(() -> mixValidator.isValid(mix));
    }
}