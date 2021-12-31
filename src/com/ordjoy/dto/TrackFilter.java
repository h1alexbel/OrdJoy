package com.ordjoy.dto;

import com.ordjoy.entity.Genre;

public record TrackFilter(int limit,
                          int offset,
                          Genre genre) {
}