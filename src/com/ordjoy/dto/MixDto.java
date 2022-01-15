package com.ordjoy.dto;

import com.ordjoy.entity.MixReview;
import com.ordjoy.entity.Track;

import java.util.Set;

public class MixDto {

    private final Long id;
    private final String name;
    private final String description;
    private final Set<Track> tracks;
    private final Set<MixReview> mixReviews;

    public MixDto(Long id, String name, String description, Set<Track> tracks, Set<MixReview> mixReviews) {
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

    public Set<Track> getTracks() {
        return tracks;
    }

    public Set<MixReview> getMixReviews() {
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