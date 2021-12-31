package com.ordjoy.filter;

import com.ordjoy.entity.Genre;

public record TrackFilter(int limit,
                          int offset,
                          Genre genre) {
}