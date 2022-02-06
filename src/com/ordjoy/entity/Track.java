package com.ordjoy.entity;

import java.util.List;
import java.util.Set;

public class Track implements Entity {

    private Long id;
    private String songUrl;
    private String title;
    private Album album;
    private List<Mix> mixes;
    private Set<TrackReview> reviews;

    public Track() {
    }

    public Track(Long id, String songUrl, String title, Album album, List<Mix> mixes, Set<TrackReview> reviews) {
        this.id = id;
        this.songUrl = songUrl;
        this.title = title;
        this.album = album;
        this.mixes = mixes;
        this.reviews = reviews;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String songUrl;
        private String title;
        private Album album;
        private List<Mix> mixes;
        private Set<TrackReview> reviews;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder songUrl(String songUrl) {
            this.songUrl = songUrl;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder album(Album album) {
            this.album = album;
            return this;
        }

        public Builder mixes(List<Mix> mixes) {
            this.mixes = mixes;
            return this;
        }

        public Builder reviews(Set<TrackReview> reviews) {
            this.reviews = reviews;
            return this;
        }

        public Track build() {
            return new Track(id, songUrl, title, album, mixes, reviews);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Mix> getMixes() {
        return mixes;
    }

    public void setMixes(List<Mix> mixes) {
        this.mixes = mixes;
    }

    public Set<TrackReview> getReviews() {
        return reviews;
    }

    public void setReviews(Set<TrackReview> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        return id != null ? id.equals(track.id) : track.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Track{" +
               "id=" + id +
               ", songUrl='" + songUrl + '\'' +
               ", title='" + title + '\'' +
               ", album=" + album +
               '}';
    }
}