package com.ordjoy.validation.impl;

import com.ordjoy.entity.Order;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;

import java.math.BigDecimal;

import static com.ordjoy.util.ErrorConstUtils.*;

public class OrderValidator implements Validator<Order> {

    private static final OrderValidator INSTANCE = new OrderValidator();

    private OrderValidator() {

    }

    public static OrderValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(Order order) {
        ValidationResult validationResult = new ValidationResult();
        if (order != null) {
            if (isPriceValid(order.getPrice()) != 1) {
                validationResult.add(Error.of(PRICE_INVALID, PRICE_IS_INVALID_MESSAGE));
            }
        } else {
            validationResult.add(Error.of(ORDER_INVALID, ORDER_IS_INVALID_MESSAGE));
        }
        return validationResult;
    }

    private int isPriceValid(BigDecimal price) {
        return price.compareTo(BigDecimal.valueOf(0));
    }
}