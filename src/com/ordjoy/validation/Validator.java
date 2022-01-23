package com.ordjoy.validation;

public interface Validator<T> {

    ValidationResult isValid(T object);
}