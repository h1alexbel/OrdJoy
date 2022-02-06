package com.ordjoy.dao;

import com.ordjoy.entity.AlbumReview;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.ReviewFilter;
import com.ordjoy.exception.DaoException;

import java.util.List;

public interface AlbumReviewDao extends GenericDao<Long, AlbumReview, ReviewFilter> {

    /**
     * Finds List of {@link AlbumReview} by {@link com.ordjoy.entity.UserAccount} login from database
     *
     * @param login {@link com.ordjoy.entity.UserAccount} login in database
     * @return List of {@link AlbumReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<AlbumReview> findAlbumReviewsByUserLogin(String login, DefaultFilter filter) throws DaoException;

    /**
     * Finds List of {@link AlbumReview} by {@link com.ordjoy.entity.UserAccount} id from database
     *
     * @param userId {@link com.ordjoy.entity.UserAccount} id in database
     * @return List of {@link AlbumReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<AlbumReview> findAlbumReviewsByUserId(Long userId, DefaultFilter filter) throws DaoException;
}