package com.ordjoy.filter;

import com.ordjoy.entity.GenreType;

public record AlbumFilter(int limit,
                          int offset,
                          GenreType genreType,
                          String artistName) {
}