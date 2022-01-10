package com.ordjoy.filter;

import com.ordjoy.entity.Album;

public record TrackFilter(int limit,
                          int offset,
                          Album album) implements Filter {
}