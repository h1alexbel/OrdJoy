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

    public Mix(Long id, String name, String description, Set<Track> tracks, Set<MixReview> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tracks = tracks;
        this.reviews = reviews;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private Set<Track> tracks;
        private Set<MixReview> reviews;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder tracks(Set<Track> tracks) {
            this.tracks = tracks;
            return this;
        }

        public Builder reviews(Set<MixReview> reviews) {
            this.reviews = reviews;
            return this;
        }

        public Mix build() {
            return new Mix(id, name, description, tracks, reviews);
        }
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