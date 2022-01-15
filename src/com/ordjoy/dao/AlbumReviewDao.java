package com.ordjoy.dao;

import com.ordjoy.entity.AlbumReview;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.ReviewFilter;

import java.util.List;

public interface AlbumReviewDao extends GenericDao<Long, AlbumReview, ReviewFilter> {

    List<AlbumReview> findAlbumReviewsByUserLogin(String login, DefaultFilter filter);

    List<AlbumReview> findAlbumReviewsByUserId(Long userId, DefaultFilter filter);
}