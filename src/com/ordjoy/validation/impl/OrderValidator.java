package com.ordjoy.validation.impl;

import com.ordjoy.entity.Order;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;

import java.math.BigDecimal;

import static com.ordjoy.util.ErrorConstUtils.*;

public class OrderValidator implements Validator<Order> {

    private static final OrderValidator INSTANCE = new OrderValidator();
    private static final String PRICE_REGEX = "^\\d{1,}$";

    private OrderValidator() {

    }

    /**
     * @return {@link OrderValidator} instance
     */
    public static OrderValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(Order order) {
        ValidationResult validationResult = new ValidationResult();
        if (order != null) {
            if (isPriceBiggerThanZero(order.getPrice()) != 1) {
                validationResult.add(Error.of(PRICE_INVALID, PRICE_IS_INVALID_MESSAGE));
            }
        } else {
            validationResult.add(Error.of(ORDER_INVALID, ORDER_IS_INVALID_MESSAGE));
        }
        return validationResult;
    }

    /** Checks valid or not price is
     * @param price price to validate
     * @return boolean result based on not null check and matching to price regex
     */
    public boolean isPriceValid(String price) {
        return price != null && price.matches(PRICE_REGEX);
    }

    private int isPriceBiggerThanZero(BigDecimal price) {
        return price.compareTo(BigDecimal.valueOf(0));
    }
}