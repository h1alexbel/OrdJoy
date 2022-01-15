package com.ordjoy.dto;

public class AlbumReviewDto {

    private final Long id;
    private final String review;
    private final String albumName;
    private final String userName;

    public AlbumReviewDto(Long id, String review, String albumName, String userName) {
        this.id = id;
        this.review = review;
        this.albumName = albumName;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumReviewDto that = (AlbumReviewDto) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AlbumReviewDto{" +
               "id=" + id +
               ", review='" + review + '\'' +
               ", albumName='" + albumName + '\'' +
               ", userName='" + userName + '\'' +
               '}';
    }
}