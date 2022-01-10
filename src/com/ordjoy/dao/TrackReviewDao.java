package com.ordjoy.dao;

import com.ordjoy.entity.TrackReview;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.ReviewFilter;

import java.util.Set;

public interface TrackReviewDao extends GenericDao<Long, TrackReview, ReviewFilter> {

    Set<TrackReview> findTrackReviewsByUserLogin(String login, DefaultFilter filter);

    Set<TrackReview> findTrackReviewsByUserId(Long userId, DefaultFilter filter);
}