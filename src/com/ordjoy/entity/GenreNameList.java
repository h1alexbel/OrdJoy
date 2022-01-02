package com.ordjoy.entity;

public enum GenreNameList {

    POP("Pop"),
    DANCE_ELECTRONIC("Dance/Electronic"),
    HIP_HOP_AND_RAP("Hip-Hop and Rap"),
    R_AND_B("R&B"),
    LATIN("Latin"),
    ROCK("Rock"),
    METAL("Metal"),
    COUNTRY("Country"),
    FOLK_AND_ACOUSTIC("Folk/Acoustic"),
    JAZZ("Jazz"),
    CLASSICAL("Classical"),
    NEW_AGE("New Age"),
    BLUES("Blues"),
    WORLD_AND_TRADITIONAL("World/Traditional"),
    EASY_LISTENING("Easy Listening");

    private final String genreName;

    GenreNameList(String genreName) {
        this.genreName = genreName;
    }

    public String getGenreName() {
        return genreName;
    }
}