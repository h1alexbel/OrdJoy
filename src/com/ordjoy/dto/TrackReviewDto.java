package com.ordjoy.dto;

public class TrackReviewDto {

    private final Long id;
    private final String review;
    private final TrackDto track;
    private final UserDto user;

    public TrackReviewDto(Long id, String review, TrackDto trackDto, UserDto userDto) {
        this.id = id;
        this.review = review;
        this.track = trackDto;
        this.user = userDto;
    }

    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public TrackDto getTrack() {
        return track;
    }

    public UserDto getUser() {
        return user;
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
               ", user=" + user +
               '}';
    }
}