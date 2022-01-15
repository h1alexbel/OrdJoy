package com.ordjoy.dto;

public class AlbumReviewDto {

    private final Long id;
    private final String review;
    private final AlbumDto album;
    private final UserDto user;

    public AlbumReviewDto(Long id, String review, AlbumDto albumDto, UserDto userDto) {
        this.id = id;
        this.review = review;
        this.album = albumDto;
        this.user = userDto;
    }

    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public AlbumDto getAlbum() {
        return album;
    }

    public UserDto getUser() {
        return user;
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
               ", user=" + user +
               '}';
    }
}