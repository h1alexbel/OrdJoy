package com.ordjoy.dao;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.TrackFilter;
import com.ordjoy.entity.*;

import java.util.Optional;
import java.util.Set;

public interface TrackDao extends GenericDao<Long, Track, TrackFilter> {

    boolean addExistingTrackToMix(Mix mixThatExists, Track trackThatExists);

    Optional<Track> findByTrackTitle(String trackTitle);

    Set<Track> findTracksByAlbumId(Long albumId, DefaultFilter filter);

    Set<Track> findTracksByAlbumName(String albumName, DefaultFilter filter);
}