package com.ordjoy.filter;

public record TrackFilter(int limit,
                          int offset,
                          String albumTitle) implements Filter {
}