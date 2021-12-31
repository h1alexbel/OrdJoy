package com.ordjoy.filter;

import com.ordjoy.entity.Genre;

public record MixFilter(int limit,
                        int offset,
                        Genre genre,
                        String description) {
}