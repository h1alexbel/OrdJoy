package com.ordjoy.dto;

import java.io.Serializable;

public class AlbumReviewDto implements Serializable {

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String reviewText;
        private AlbumDto album;
        private UserAccountDto userAccount;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder reviewText(String reviewText) {
            this.reviewText = reviewText;
            return this;
        }

        public Builder album(AlbumDto album) {
            this.album = album;
            return this;
        }

        public Builder userAccount(UserAccountDto userAccount) {
            this.userAccount = userAccount;
            return this;
        }

        public AlbumReviewDto build() {
            return new AlbumReviewDto(id, reviewText, album, userAccount);
        }
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