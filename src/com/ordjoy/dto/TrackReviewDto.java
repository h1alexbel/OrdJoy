package com.ordjoy.dto;

public class TrackReviewDto {

    private final Long id;
    private final String review;
    private final String trackName;
    private final String userLogin;

    public TrackReviewDto(Long id, String review, String trackName, String userLogin) {
        this.id = id;
        this.review = review;
        this.trackName = trackName;
        this.userLogin = userLogin;
    }

    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackReviewDto that = (TrackReviewDto) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TrackReviewDto{" +
               "id=" + id +
               ", review='" + review + '\'' +
               ", trackName='" + trackName + '\'' +
               ", userLogin='" + userLogin + '\'' +
               '}';
    }
}