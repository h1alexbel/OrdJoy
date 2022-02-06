package com.ordjoy.dao;

import com.ordjoy.entity.MixReview;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.ReviewFilter;
import com.ordjoy.exception.DaoException;

import java.util.List;

public interface MixReviewDao extends GenericDao<Long, MixReview, ReviewFilter> {

    /**
     * Finds List of {@link MixReview} by {@link com.ordjoy.entity.UserAccount} login from database
     *
     * @param login {@link com.ordjoy.entity.UserAccount} login in database
     * @return List of {@link MixReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<MixReview> findMixReviewsByUserLogin(String login, DefaultFilter filter) throws DaoException;

    /**
     * Finds List of {@link MixReview} by {@link com.ordjoy.entity.UserAccount} id from database
     *
     * @param userId {@link com.ordjoy.entity.UserAccount} id in database
     * @return List of {@link MixReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<MixReview> findMixReviewsByUserId(Long userId, DefaultFilter filter) throws DaoException;
}