package com.ordjoy.validation;

public interface Validator<T> {

    /**
     * Validates some object
     * @param object object to validate
     * @return {@link ValidationResult} - the result of validation
     */
    ValidationResult isValid(T object);
}