package com.ordjoy.dao;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.TrackFilter;
import com.ordjoy.entity.*;

import java.util.List;
import java.util.Optional;

public interface TrackDao extends GenericDao<Long, Track, TrackFilter> {

    boolean addExistingTrackToMix(Mix mixThatExists, Track trackThatExists);

    Optional<Track> findByTrackTitle(String trackTitle);

    List<Track> findTracksByAlbumId(Long albumId, DefaultFilter filter);

    List<Track> findTracksByAlbumName(String albumName, DefaultFilter filter);
}