package com.ordjoy.dto;

import java.io.Serializable;

public class MixReviewDto implements Serializable {

    private final Long id;
    private final String reviewText;
    private final MixDto mix;
    private final UserAccountDto userAccount;

    public MixReviewDto(Long id, String reviewText, MixDto mix, UserAccountDto userAccount) {
        this.id = id;
        this.reviewText = reviewText;
        this.mix = mix;
        this.userAccount = userAccount;
    }

    public Long getId() {
        return id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public MixDto getMix() {
        return mix;
    }

    public UserAccountDto getUserAccount() {
        return userAccount;
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
               " reviewText='" + reviewText + '\'' +
               ", mix=" + mix +
               ", userAccount=" + userAccount +
               '}';
    }
}