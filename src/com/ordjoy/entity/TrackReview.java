package com.ordjoy.entity;

public class TrackReview implements Entity {

    private Long id;
    private String reviewText;
    private UserAccount userAccount;
    private Track track;

    public TrackReview() {
    }

    public TrackReview(Long id, String reviewText, UserAccount userAccount, Track track) {
        this.id = id;
        this.reviewText = reviewText;
        this.userAccount = userAccount;
        this.track = track;
    }

    public TrackReview(String reviewText, UserAccount userAccount, Track track) {
        this.reviewText = reviewText;
        this.userAccount = userAccount;
        this.track = track;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackReview that = (TrackReview) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TrackReview{" +
               "id=" + id +
               ", reviewText='" + reviewText + '\'' +
               ", userAccount=" + userAccount +
               '}';
    }
}