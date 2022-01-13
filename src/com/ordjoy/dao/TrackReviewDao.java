package com.ordjoy.dao;

import com.ordjoy.entity.TrackReview;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.ReviewFilter;

import java.util.List;

public interface TrackReviewDao extends GenericDao<Long, TrackReview, ReviewFilter> {

    List<TrackReview> findTrackReviewsByUserLogin(String login, DefaultFilter filter);

    List<TrackReview> findTrackReviewsByUserId(Long userId, DefaultFilter filter);

    List<TrackReview> findTrackReviewsByTrackId(Long trackId, DefaultFilter filter);

    List<TrackReview> findTrackReviewsByTrackTitle(String title, DefaultFilter filter);
}