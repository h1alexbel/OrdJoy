package com.ordjoy.validation;

import com.ordjoy.entity.Order;
import com.ordjoy.entity.OrderStatus;
import com.ordjoy.validation.impl.OrderValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderValidatorTest {

    @Test
    public void isOrderValid() {
        OrderValidator orderValidator = OrderValidator.getInstance();
        Order order = Order.builder()
                .orderStatus(OrderStatus.ACCEPTED)
                .price(new BigDecimal(123))
                .build();
        ValidationResult validationResult = orderValidator.isValid(order);
        assertTrue(validationResult.isValid());
    }

    @Test
    public void isOrderValidatorDoesntThrowException() {
        OrderValidator orderValidator = OrderValidator.getInstance();
        assertDoesNotThrow(() -> orderValidator.isValid(null));
    }

    @Test
    public void isOrderPriceValid() {
        OrderValidator orderValidator = OrderValidator.getInstance();
        boolean actual = orderValidator.isPriceValid("142");
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void orderPriceEmptyCase() {
        String price = "";
        OrderValidator orderValidator = OrderValidator.getInstance();
        boolean actual = orderValidator.isPriceValid(price);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void isOrderValidatorPriceMethodDoesntThrowException() {
        OrderValidator orderValidator = OrderValidator.getInstance();
        assertDoesNotThrow(() -> orderValidator.isPriceValid(null), "Price can not be null!");
    }
}