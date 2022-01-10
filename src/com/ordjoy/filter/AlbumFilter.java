package com.ordjoy.filter;

public record AlbumFilter(int limit,
                          int offset) implements Filter {
}