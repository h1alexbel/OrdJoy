package com.ordjoy.validation;

import com.ordjoy.entity.Order;
import com.ordjoy.entity.OrderStatus;
import com.ordjoy.validation.impl.OrderValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {

    @Test
    @DisplayName("order validation test")
    void isOrderValid() {
        OrderValidator orderValidator = OrderValidator.getInstance();
        Order order = Order.builder()
                .orderStatus(OrderStatus.ACCEPTED)
                .price(new BigDecimal(123))
                .build();
        ValidationResult validationResult = orderValidator.isValid(order);
        assertTrue(validationResult.isValid());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("order null case test")
    void isOrderValidatorDoesntThrowException(Order order) {
        OrderValidator orderValidator = OrderValidator.getInstance();
        assertDoesNotThrow(() -> orderValidator.isValid(order));
    }

    @Test
    @DisplayName("order price validation test")
    void isOrderPriceValid() {
        OrderValidator orderValidator = OrderValidator.getInstance();
        boolean actual = orderValidator.isPriceValid("142");
        boolean expected = true;
        assertEquals(expected, actual, "Price can not be empty!");
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("order price empty case")
    void orderPriceEmptyCase(String price) {
        OrderValidator orderValidator = OrderValidator.getInstance();
        boolean actual = orderValidator.isPriceValid(price);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("order price null case test")
    void isOrderValidatorPriceMethodDoesntThrowException(String price) {
        OrderValidator orderValidator = OrderValidator.getInstance();
        assertDoesNotThrow(() -> orderValidator.isPriceValid(price), "Price can not be null!");
    }
}