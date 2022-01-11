package com.ordjoy.entity;

import java.util.Set;

public class Mix implements Entity {

    private Long id;
    private String name;
    private String description;
    private Set<Track> tracks;
    private Set<MixReview> reviews;

    public Mix() {
    }

    public Mix(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Mix(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    public Set<MixReview> getReviews() {
        return reviews;
    }

    public void setReviews(Set<MixReview> reviews) {
        this.reviews = reviews;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mix mix = (Mix) o;

        return id != null ? id.equals(mix.id) : mix.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Mix{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               '}';
    }
}