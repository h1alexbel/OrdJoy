package com.ordjoy.dto;

public class MixReviewDto {

    private final Long id;
    private final String review;
    private final MixDto mix;
    private final UserDto user;

    public MixReviewDto(Long id, String review, MixDto mixDto, UserDto userDto) {
        this.id = id;
        this.review = review;
        this.mix = mixDto;
        this.user = userDto;
    }

    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public MixDto getMix() {
        return mix;
    }

    public UserDto getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MixReviewDto that = (MixReviewDto) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MixReviewDto{" +
               "id=" + id +
               ", review='" + review + '\'' +
               ", user=" + user +
               '}';
    }
}