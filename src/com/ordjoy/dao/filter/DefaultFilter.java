package com.ordjoy.dao.filter;

public record DefaultFilter(int limit,
                            int offset) implements Filter {
}