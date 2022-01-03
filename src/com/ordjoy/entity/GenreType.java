package com.ordjoy.entity;

public enum GenreType implements Entity {

    POP("POP"),
    ELECTRONIC("ELECTRONIC"),
    RAP("RAP"),
    RB("RB"),
    LATIN("LATIN"),
    ROCK("ROCK"),
    METAL("METAL"),
    COUNTRY("COUNTRY"),
    FOLK("FOLK"),
    JAZZ("JAZZ"),
    CLASSICAL("CLASSICAL"),
    NEW_AGE("NEW_AGE"),
    BLUES("BLUES"),
    TRADITIONAL("TRADITIONAL"),
    EASY_LISTENING("EASY_LISTENING");

    private final String genreName;

    GenreType(String genreName) {
        this.genreName = genreName;
    }

    public String getGenreName() {
        return genreName;
    }
}