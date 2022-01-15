package com.ordjoy.dao.filter;

public record AlbumFilter(int limit,
                          int offset) implements Filter {
}