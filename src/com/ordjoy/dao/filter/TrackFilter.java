package com.ordjoy.dao.filter;

public record TrackFilter(int limit,
                          int offset,
                          String albumTitle) implements Filter {
}