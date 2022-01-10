package com.ordjoy.dao;

import com.ordjoy.entity.UserReviewData;
import com.ordjoy.filter.UserAccountFilter;
import com.ordjoy.entity.UserAccount;

import java.util.Optional;

public interface UserDao extends GenericDao<Long, UserAccount, UserAccountFilter> {

    Optional<UserReviewData> findUserReviewDataByUserId(Long userId);

    Optional<UserReviewData> findUserReviewDataByLogin(String login);

    void addDiscountPercentageLevel(Integer discountPercentageLevel, String userEmail);

    Optional<Integer> findDiscountPercentageLevelByUserId(Long userId);

    Optional<Integer> findDiscountPercentageLevelByEmail(String email);

    Optional<UserAccount> findUserByLogin(String login);

    Optional<UserAccount> findUserByEmail(String email);
}