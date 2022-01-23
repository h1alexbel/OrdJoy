package com.ordjoy.validation.impl;

import com.ordjoy.entity.UserAccount;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;

import static com.ordjoy.util.ErrorConstUtils.*;

public class UserValidator implements Validator<UserAccount> {

    private static final String LOGIN_REGEX = "^\\w{3,20}$";
    private static final String EMAIL_REGEX = "^[A-Za-z.]+\\w+@[A-Za-z]{2,}\\.(com|org)$";
    private static final String PASSWORD_REGEX = "^\\w{2,64}$";
    private static final String FIRST_NAME_REGEX = "^[A-Za-z]{2,20}$";
    private static final String LAST_NAME_REGEX = "^[A-Za-z]{2,20}$";
    private static final String CARD_NUMBER_REGEX = "^\\d{16}$";
    private static final int MIN_AGE_TO_USE_APPLICATION = 13;
    private static final UserValidator INSTANCE = new UserValidator();

    private UserValidator() {

    }

    public static UserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(UserAccount userAccount) {
        ValidationResult validationResult = new ValidationResult();
        if (userAccount != null) {
            if (!isLoginValid(userAccount.getLogin())) {
                validationResult.add(Error.of(LOGIN_INVALID, INVALID_LOGIN_MESSAGE));
            }
            if (!isEmailValid(userAccount.getEmail())) {
                validationResult.add(Error.of(EMAIL_INVALID, INVALID_EMAIL_MESSAGE));
            }
            if (!isFirstNameValid(userAccount.getUserData().getFirstName())) {
                validationResult.add(Error.of(FIRST_NAME_INVALID, INVALID_FIRST_NAME_MESSAGE));
            }
            if (!isLastNameValid(userAccount.getUserData().getLastName())) {
                validationResult.add(Error.of(LAST_NAME_INVALID, INVALID_LAST_NAME_MESSAGE));
            }
            if (!isAgeValid(userAccount.getUserData().getAge())) {
                validationResult.add(Error.of(AGE_INVALID, INVALID_AGE_MESSAGE));
            }
        } else {
            validationResult.add(Error.of(USER_INVALID, USER_CANNOT_BE_NULL_MESSAGE));
        }
        return validationResult;
    }

    public boolean isPasswordValid(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    public boolean isCardDataValid(String cardNumber) {
        boolean result = false;
        if (cardNumber != null) {
            result = cardNumber.matches(CARD_NUMBER_REGEX);
        }
        return result;
    }

    private boolean isLoginValid(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }


    private boolean isEmailValid(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    private boolean isFirstNameValid(String firstName) {
        return firstName != null && firstName.matches(FIRST_NAME_REGEX);
    }

    private boolean isLastNameValid(String lastName) {
        return lastName != null && lastName.matches(LAST_NAME_REGEX);
    }

    private boolean isAgeValid(Integer age) {
        return age != null && age > MIN_AGE_TO_USE_APPLICATION;
    }
}