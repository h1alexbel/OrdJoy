package com.ordjoy.dao;

import com.ordjoy.entity.TrackReview;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.ReviewFilter;
import com.ordjoy.exception.DaoException;

import java.util.List;

public interface TrackReviewDao extends GenericDao<Long, TrackReview, ReviewFilter> {

    List<TrackReview> findTrackReviewsByUserLogin(String login, DefaultFilter filter) throws DaoException;

    List<TrackReview> findTrackReviewsByUserId(Long userId, DefaultFilter filter) throws DaoException;

    List<TrackReview> findTrackReviewsByTrackId(Long trackId, DefaultFilter filter) throws DaoException;

    List<TrackReview> findTrackReviewsByTrackTitle(String title, DefaultFilter filter) throws DaoException;
}