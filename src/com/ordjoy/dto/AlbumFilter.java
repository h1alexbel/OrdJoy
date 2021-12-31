package com.ordjoy.dto;

import com.ordjoy.entity.Genre;

public record AlbumFilter(int limit,
                          int offset,
                          Genre genre) {
}