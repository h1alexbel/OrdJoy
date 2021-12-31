package com.ordjoy.dto;

import com.ordjoy.entity.Genre;

public record MixFilter(int limit,
                        int offset,
                        Genre genre,
                        String description) {
}