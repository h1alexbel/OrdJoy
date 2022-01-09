package com.ordjoy.filter;

public record AlbumFilter(int limit,
                          int offset,
                          GenreType genreType,
                          String artistName) implements Filter {
}