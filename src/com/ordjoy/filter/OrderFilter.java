package com.ordjoy.filter;

import com.ordjoy.entity.OrderStatus;

import java.math.BigDecimal;

public record OrderFilter(int limit,
                          int offset,
                          BigDecimal price,
                          OrderStatus orderStatus) implements Filter {
}