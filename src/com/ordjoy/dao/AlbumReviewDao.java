package com.ordjoy.dao;

import com.ordjoy.entity.AlbumReview;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.ReviewFilter;
import com.ordjoy.exception.DaoException;

import java.util.List;

public interface AlbumReviewDao extends GenericDao<Long, AlbumReview, ReviewFilter> {

    List<AlbumReview> findAlbumReviewsByUserLogin(String login, DefaultFilter filter) throws DaoException;

    List<AlbumReview> findAlbumReviewsByUserId(Long userId, DefaultFilter filter) throws DaoException;
}