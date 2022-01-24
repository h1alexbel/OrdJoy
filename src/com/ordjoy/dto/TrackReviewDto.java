package com.ordjoy.dto;

public class TrackReviewDto {

    private final Long id;
    private final String reviewText;
    private final TrackDto track;
    private final UserAccountDto userAccount;

    public TrackReviewDto(Long id, String reviewText, TrackDto track, UserAccountDto userAccount) {
        this.id = id;
        this.reviewText = reviewText;
        this.track = track;
        this.userAccount = userAccount;
    }

    public Long getId() {
        return id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public TrackDto getTrack() {
        return track;
    }

    public UserAccountDto getUserAccount() {
        return userAccount;
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
               " reviewText='" + reviewText + '\'' +
               ", track=" + track +
               ", userAccount=" + userAccount +
               '}';
    }
}