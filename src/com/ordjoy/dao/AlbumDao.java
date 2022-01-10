package com.ordjoy.dao;

import com.ordjoy.entity.AlbumReview;
import com.ordjoy.filter.AlbumFilter;
import com.ordjoy.entity.Album;
import com.ordjoy.filter.DefaultFilter;

import java.util.Optional;
import java.util.Set;

public interface AlbumDao extends GenericDao<Long, Album, AlbumFilter> {

    Optional<Album> findAlbumByTitle(String albumTitle);

    Set<AlbumReview> findAlbumReviewsByAlbumTitle(String albumTitle, DefaultFilter filter);

    Set<AlbumReview> findAlbumReviewsByAlbumId(Long albumId, DefaultFilter filter);
}