package com.ordjoy.filter;

public record ReviewFilter(int limit,
                           int offset,
                           String reviewText,
                           String userAccountLogin) implements Filter {
}