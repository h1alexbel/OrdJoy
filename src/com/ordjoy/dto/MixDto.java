package com.ordjoy.dto;

import java.util.Set;

public class MixDto {

    private final Long id;
    private final String name;
    private final String description;
    private final Set<TrackDto> tracks;
    private final Set<MixReviewDto> mixReviews;

    public MixDto(Long id, String name, String description, Set<TrackDto> tracks, Set<MixReviewDto> mixReviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tracks = tracks;
        this.mixReviews = mixReviews;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<TrackDto> getTracks() {
        return tracks;
    }

    public Set<MixReviewDto> getMixReviews() {
        return mixReviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MixDto mixDto = (MixDto) o;

        return id != null ? id.equals(mixDto.id) : mixDto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MixDto{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", tracks=" + tracks +
               ", mixReviews=" + mixReviews +
               '}';
    }
}