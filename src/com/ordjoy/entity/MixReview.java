package com.ordjoy.entity;

public class MixReview implements Entity {

    private Long id;
    private String reviewText;
    private UserAccount userAccount;
    private Mix mix;

    public MixReview() {
    }

    public MixReview(Long id, String reviewText, UserAccount userAccount, Mix mix) {
        this.id = id;
        this.reviewText = reviewText;
        this.userAccount = userAccount;
        this.mix = mix;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String reviewText;
        private UserAccount userAccount;
        private Mix mix;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder reviewText(String reviewText) {
            this.reviewText = reviewText;
            return this;
        }

        public Builder userAccount(UserAccount userAccount) {
            this.userAccount = userAccount;
            return this;
        }

        public Builder mix(Mix mix) {
            this.mix = mix;
            return this;
        }

        public MixReview build() {
            return new MixReview(id, reviewText, userAccount, mix);
        }
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

    public Mix getMix() {
        return mix;
    }

    public void setMix(Mix mix) {
        this.mix = mix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MixReview mixReview = (MixReview) o;

        return id != null ? id.equals(mixReview.id) : mixReview.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MixReview{" +
               "id=" + id +
               ", reviewText='" + reviewText + '\'' +
               ", userAccount=" + userAccount +
               '}';
    }
}