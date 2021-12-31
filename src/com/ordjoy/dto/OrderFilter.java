package com.ordjoy.dto;

import java.math.BigDecimal;

public record OrderFilter(int limit,
                          int offset,
                          BigDecimal price,
                          Long cardNumber) {
}