package com.ordjoy.dao;

import com.ordjoy.entity.MixReview;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.ReviewFilter;

import java.util.List;

public interface MixReviewDao extends GenericDao<Long, MixReview, ReviewFilter> {

    List<MixReview> findMixReviewsByUserLogin(String login, DefaultFilter filter);

    List<MixReview> findMixReviewsByUserId(Long userId, DefaultFilter filter);
}