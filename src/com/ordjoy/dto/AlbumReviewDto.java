package com.ordjoy.dto;

public class AlbumReviewDto {

    private final Long id;
    private final String reviewText;
    private final AlbumDto album;
    private final UserAccountDto userAccount;

    public AlbumReviewDto(Long id, String reviewText, AlbumDto album, UserAccountDto userAccount) {
        this.id = id;
        this.reviewText = reviewText;
        this.album = album;
        this.userAccount = userAccount;
    }

    public Long getId() {
        return id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public AlbumDto getAlbum() {
        return album;
    }

    public UserAccountDto getUserAccount() {
        return userAccount;
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
               " reviewText='" + reviewText + '\'' +
               ", album=" + album +
               ", userAccount=" + userAccount +
               '}';
    }
}