package com.ordjoy.entity;

import java.util.Set;

public class UserReviewData {

    private Set<TrackReview> trackReviews;
    private Set<AlbumReview> albumReviews;
    private Set<MixReview> mixReviews;

    public UserReviewData(Set<TrackReview> trackReviews, Set<AlbumReview> albumReviews, Set<MixReview> mixReviews) {
        this.trackReviews = trackReviews;
        this.albumReviews = albumReviews;
        this.mixReviews = mixReviews;
    }

    public Set<TrackReview> getTrackReviews() {
        return trackReviews;
    }

    public void setTrackReviews(Set<TrackReview> trackReviews) {
        this.trackReviews = trackReviews;
    }

    public Set<AlbumReview> getAlbumReviews() {
        return albumReviews;
    }

    public void setAlbumReviews(Set<AlbumReview> albumReviews) {
        this.albumReviews = albumReviews;
    }

    public Set<MixReview> getMixReviews() {
        return mixReviews;
    }

    public void setMixReviews(Set<MixReview> mixReviews) {
        this.mixReviews = mixReviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserReviewData that = (UserReviewData) o;

        if (trackReviews != null ? !trackReviews.equals(that.trackReviews) : that.trackReviews != null) return false;
        if (albumReviews != null ? !albumReviews.equals(that.albumReviews) : that.albumReviews != null) return false;
        return mixReviews != null ? mixReviews.equals(that.mixReviews) : that.mixReviews == null;
    }

    @Override
    public int hashCode() {
        int result = trackReviews != null ? trackReviews.hashCode() : 0;
        result = 31 * result + (albumReviews != null ? albumReviews.hashCode() : 0);
        result = 31 * result + (mixReviews != null ? mixReviews.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserReviewData{" +
               "trackReviews=" + trackReviews +
               ", albumReviews=" + albumReviews +
               ", mixReviews=" + mixReviews +
               '}';
    }
}