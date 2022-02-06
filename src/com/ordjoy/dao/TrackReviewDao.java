package com.ordjoy.dao;

import com.ordjoy.entity.TrackReview;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.ReviewFilter;
import com.ordjoy.exception.DaoException;

import java.util.List;

public interface TrackReviewDao extends GenericDao<Long, TrackReview, ReviewFilter> {

    /**
     * Finds List of {@link TrackReview} by {@link com.ordjoy.entity.UserAccount} login from database
     *
     * @param login {@link com.ordjoy.entity.UserAccount} login in database
     * @return List of {@link TrackReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<TrackReview> findTrackReviewsByUserLogin(String login, DefaultFilter filter) throws DaoException;

    /**
     * Finds List of {@link TrackReview} by {@link com.ordjoy.entity.UserAccount} id from database
     *
     * @param userId {@link com.ordjoy.entity.UserAccount} id in database
     * @return List of {@link TrackReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<TrackReview> findTrackReviewsByUserId(Long userId, DefaultFilter filter) throws DaoException;

    /**
     * Finds List of {@link TrackReview} by {@link com.ordjoy.entity.Track} id from database
     *
     * @param trackId {@link com.ordjoy.entity.Track} id in database
     * @return List of {@link TrackReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<TrackReview> findTrackReviewsByTrackId(Long trackId, DefaultFilter filter) throws DaoException;

    /**
     * Finds List of {@link TrackReview} by {@link com.ordjoy.entity.Track} title from database
     *
     * @param title {@link com.ordjoy.entity.Track} title in database
     * @return List of {@link TrackReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<TrackReview> findTrackReviewsByTrackTitle(String title, DefaultFilter filter) throws DaoException;
}