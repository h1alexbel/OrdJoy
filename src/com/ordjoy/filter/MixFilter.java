package com.ordjoy.filter;

public record MixFilter(int limit,
                        int offset) implements Filter {
}