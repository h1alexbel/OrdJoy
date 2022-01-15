package com.ordjoy.dao.filter;

public record UserAccountFilter(int limit,
                                int offset) implements Filter {
}