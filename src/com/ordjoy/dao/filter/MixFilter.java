package com.ordjoy.dao.filter;

public record MixFilter(int limit,
                        int offset) implements Filter {
}