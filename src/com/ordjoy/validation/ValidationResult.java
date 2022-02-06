package com.ordjoy.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private final List<Error> errors = new ArrayList<>();

    /**
     * Add {@link Error} to the {@link ValidationResult} error list
     * @param error {@link Error}
     */
    public void add(Error error) {
        this.errors.add(error);
    }

    /**
     * Checks valid or not is {@link ValidationResult}
     * @return is valid or not using method errors.isEmpty()
     * @see Error
     */
    public boolean isValid() {
        return errors.isEmpty();
    }

    /**
     * Gets all errors
     * @return List of the errors
     * @see Error
     */
    public List<Error> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
               "errors=" + errors +
               ", valid=" + isValid() +
               '}';
    }
}