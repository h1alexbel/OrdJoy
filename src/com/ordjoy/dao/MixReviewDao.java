package com.ordjoy.dao;

import com.ordjoy.entity.MixReview;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.ReviewFilter;

import java.util.Set;

public interface MixReviewDao extends GenericDao<Long, MixReview, ReviewFilter> {

    Set<MixReview> findMixReviewsByUserLogin(String login, DefaultFilter filter);

    Set<MixReview> findMixReviewsByUserId(Long userId, DefaultFilter filter);
}