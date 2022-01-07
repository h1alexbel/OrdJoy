package com.ordjoy.filter;

import java.math.BigDecimal;

public record OrderFilter(int limit,
                          int offset,
                          BigDecimal price,
                          String cardNumber) {
}