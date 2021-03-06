package com.ordjoy.entity;

import java.util.Set;

public class Album implements Entity {

    private Long id;
    private String title;
    private Set<Track> tracks;
    private Set<AlbumReview> reviews;

    public Album() {
    }

    public Album(String title) {
        this.title = title;
    }

    public Album(Long id, String title, Set<Track> tracks, Set<AlbumReview> reviews) {
        this.id = id;
        this.title = title;
        this.tracks = tracks;
        this.reviews = reviews;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private Set<Track> tracks;
        private Set<AlbumReview> reviews;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder tracks(Set<Track> tracks) {
            this.tracks = tracks;
            return this;
        }

        public Builder reviews(Set<AlbumReview> reviews) {
            this.reviews = reviews;
            return this;
        }

        public Album build() {
            return new Album(id, title, tracks, reviews);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    public Set<AlbumReview> getReviews() {
        return reviews;
    }

    public void setReviews(Set<AlbumReview> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        return id != null ? id.equals(album.id) : album.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Album{" +
               "id=" + id +
               ", title='" + title + '\'' +
               '}';
    }
}