package com.ordjoy.filter;

public record UserAccountFilter(int limit,
                                int offset) implements Filter {
}