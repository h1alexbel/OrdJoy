package com.ordjoy.dao;

import com.ordjoy.entity.MixReview;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.ReviewFilter;
import com.ordjoy.exception.DaoException;

import java.util.List;

public interface MixReviewDao extends GenericDao<Long, MixReview, ReviewFilter> {

    List<MixReview> findMixReviewsByUserLogin(String login, DefaultFilter filter) throws DaoException;

    List<MixReview> findMixReviewsByUserId(Long userId, DefaultFilter filter) throws DaoException;
}