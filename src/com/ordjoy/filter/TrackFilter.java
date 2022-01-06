package com.ordjoy.filter;

import com.ordjoy.entity.GenreType;

public record TrackFilter(int limit,
                          int offset,
                          GenreType genreType) {
}