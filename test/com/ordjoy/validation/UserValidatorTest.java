package com.ordjoy.validation;

import com.ordjoy.entity.UserAccount;
import com.ordjoy.entity.UserData;
import com.ordjoy.entity.UserRole;
import com.ordjoy.validation.impl.UserValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    @Test
    @DisplayName("user validation test")
    void isUserValid() {
        UserValidator userValidator = UserValidator.getInstance();
        UserAccount user = UserAccount.builder()
                .login("test")
                .password("pass")
                .email("test@gmail.com")
                .userData(UserData.builder()
                        .firstName("John")
                        .lastName("Dow")
                        .age(18)
                        .cardNumber("1243124355667722")
                        .userRole(UserRole.CLIENT_ROLE)
                        .build())
                .build();
        ValidationResult validationResult = userValidator.isValid(user);
        assertTrue(validationResult.isValid());
    }

    @Test
    void isUsernameNotEmpty() {
        String login = "test";
        UserValidator userValidator = UserValidator.getInstance();
        boolean actual = userValidator.isLoginValid(login);
        boolean expected = true;
        assertEquals(expected, actual);
        assertTrue(actual, "Username can not be empty!");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("login null or empty case test")
    void isLoginEmptyAndNullCase(String login) {
        UserValidator userValidator = UserValidator.getInstance();
        boolean actual = userValidator.isLoginValid(login);
        boolean expected = false;
        assertEquals(expected, actual);
        assertFalse(actual, "Username can not be empty!");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("password null or empty case test")
    void isPasswordEmptyAndNullCase(String password) {
        UserValidator userValidator = UserValidator.getInstance();
        boolean actual = userValidator.isPasswordValid(password);
        boolean expected = false;
        assertEquals(expected, actual);
        assertFalse(actual, "Password can not be empty!");
    }

    @Test
    void isPasswordNotEmpty() {
        String password = "pass";
        UserValidator userValidator = UserValidator.getInstance();
        boolean actual = userValidator.isPasswordValid(password);
        boolean expected = true;
        assertEquals(expected, actual);
        assertTrue(actual, "Password can not be empty!");
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("user null case test")
    void isUserValidatorDoesntThrowException(UserAccount userAccount) {
        UserValidator userValidator = UserValidator.getInstance();
        assertDoesNotThrow(() -> userValidator.isValid(userAccount), "User can not be null!");
    }

    @Test
    @DisplayName("13 age and under test")
    void ageUnder13Test() {
        UserValidator userValidator = UserValidator.getInstance();
        UserAccount user = UserAccount.builder()
                .login("example")
                .password("pass")
                .email("example@gmail.com")
                .userData(UserData.builder()
                        .firstName("John")
                        .lastName("Dow")
                        .age(13)
                        .cardNumber("1243124355667722")
                        .userRole(UserRole.CLIENT_ROLE)
                        .build())
                .build();
        ValidationResult validationResult = userValidator.isValid(user);
        assertFalse(validationResult.isValid());
    }

    @Test
    @DisplayName("grater than 13 age test")
    void ageGrater13Test() {
        UserValidator userValidator = UserValidator.getInstance();
        UserAccount user = UserAccount.builder()
                .login("example")
                .password("pass")
                .email("example@gmail.com")
                .userData(UserData.builder()
                        .firstName("John")
                        .lastName("Dow")
                        .age(14)
                        .cardNumber("1243124355667722")
                        .userRole(UserRole.CLIENT_ROLE)
                        .build())
                .build();
        ValidationResult validationResult = userValidator.isValid(user);
        assertTrue(validationResult.isValid());
    }
}