package com.ordjoy.dao;

import com.ordjoy.entity.AlbumReview;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.ReviewFilter;

import java.util.Set;

public interface AlbumReviewDao extends GenericDao<Long, AlbumReview, ReviewFilter> {

    Set<AlbumReview> findAlbumReviewsByUserLogin(String login, DefaultFilter filter);

    Set<AlbumReview> findAlbumReviewsByUserId(Long userId, DefaultFilter filter);
}