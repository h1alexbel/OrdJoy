package com.ordjoy.dao;

import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.TrackFilter;
import com.ordjoy.entity.*;

import java.util.Optional;
import java.util.Set;

public interface TrackDao extends GenericDao<Long, Track, TrackFilter> {

    boolean addExistingTrackToMix(Mix mixThatExists, Track trackThatExists);

    Optional<Track> findByTrackTitle(String trackTitle);

    Set<Track> findTracksByAlbumId(Long albumId, DefaultFilter filter);

    Set<Track> findTracksByAlbumName(String albumName, DefaultFilter filter);

    Set<Track> findTracksByMixId(Long mixId, DefaultFilter filter);

    Set<Track> findTracksByMixName(String mixName, DefaultFilter filter);

    Set<TrackReview> findTrackReviewsByTrackId(Long trackId, DefaultFilter filter);

    Set<TrackReview> findTrackReviewsByTrackTitle(String title, DefaultFilter filter);
}