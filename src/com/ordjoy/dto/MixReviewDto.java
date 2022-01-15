package com.ordjoy.dto;

public class MixReviewDto {

    private final Long id;
    private final String review;
    private final String mixName;
    private final String userName ;

    public MixReviewDto(Long id, String review, String mixName, String userName) {
        this.id = id;
        this.review = review;
        this.mixName = mixName;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public String getMixName() {
        return mixName;
    }

    public String getUserName() {
        return userName;
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
               ", mixName='" + mixName + '\'' +
               ", userName='" + userName + '\'' +
               '}';
    }
}