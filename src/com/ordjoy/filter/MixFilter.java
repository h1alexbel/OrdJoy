package com.ordjoy.filter;

import com.ordjoy.entity.GenreType;

public record MixFilter(int limit,
                        int offset,
                        GenreType genreType) {
}