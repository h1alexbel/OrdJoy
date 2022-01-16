package com.ordjoy.dao;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.TrackFilter;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TrackDao extends GenericDao<Long, Track, TrackFilter> {

    boolean addExistingTrackToMix(Mix mixThatExists, Track trackThatExists) throws DaoException;

    Optional<Track> findByTrackTitle(String trackTitle) throws DaoException;

    List<Track> findTracksByAlbumId(Long albumId, DefaultFilter filter) throws DaoException;

    List<Track> findTracksByAlbumName(String albumName, DefaultFilter filter) throws DaoException;
}