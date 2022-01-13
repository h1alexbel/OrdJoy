package com.ordjoy.dao;

import com.ordjoy.entity.AlbumReview;
import com.ordjoy.filter.AlbumFilter;
import com.ordjoy.entity.Album;
import com.ordjoy.filter.DefaultFilter;

import java.util.List;
import java.util.Optional;

public interface AlbumDao extends GenericDao<Long, Album, AlbumFilter> {

    Optional<Album> findAlbumByTitle(String albumTitle);

    List<AlbumReview> findAlbumReviewsByAlbumTitle(String albumTitle, DefaultFilter filter);

    List<AlbumReview> findAlbumReviewsByAlbumId(Long albumId, DefaultFilter filter);
}