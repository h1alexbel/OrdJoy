package com.ordjoy.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {

    @Test
    @DisplayName("password encoding test")
    void encodingTest() {
        String password = "qwerty12345";
        String encodedPassword = PasswordEncoder.encode(password);
        assertNotEquals(password, encodedPassword,
                "Password after encoding should be differ with pre-encoded!");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("password encoding null and empty case test")
    void isPasswordEncoderDoesntThrowException(String passwordEncode) {
        assertDoesNotThrow(() -> PasswordEncoder.encode(passwordEncode), "");
    }

    @Test
    void isEncodedPasswordNotEmpty() {
        String password = "qwerty12345";
        String encodedPassword = PasswordEncoder.encode(password);
        assertFalse(encodedPassword.isEmpty());
    }
}