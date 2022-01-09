package com.ordjoy.entity;

public class AlbumReview implements Entity {

    private Long id;
    private String reviewText;
    private UserAccount userAccount;
    private Album album;

    public AlbumReview() {
    }

    public AlbumReview(Long id, String reviewText, UserAccount userAccount, Album album) {
        this.id = id;
        this.reviewText = reviewText;
        this.userAccount = userAccount;
        this.album = album;
    }

    public AlbumReview(String reviewText, UserAccount userAccount, Album album) {
        this.reviewText = reviewText;
        this.userAccount = userAccount;
        this.album = album;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumReview that = (AlbumReview) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AlbumReview{" +
               "id=" + id +
               ", reviewText='" + reviewText + '\'' +
               ", userAccount=" + userAccount +
               '}';
    }
}