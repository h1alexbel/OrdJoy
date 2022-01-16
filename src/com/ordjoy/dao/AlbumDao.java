package com.ordjoy.dao;

import com.ordjoy.entity.AlbumReview;
import com.ordjoy.dao.filter.AlbumFilter;
import com.ordjoy.entity.Album;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface AlbumDao extends GenericDao<Long, Album, AlbumFilter> {

    Optional<Album> findAlbumByTitle(String albumTitle) throws DaoException;

    List<AlbumReview> findAlbumReviewsByAlbumTitle(String albumTitle, DefaultFilter filter) throws DaoException;

    List<AlbumReview> findAlbumReviewsByAlbumId(Long albumId, DefaultFilter filter) throws DaoException;
}